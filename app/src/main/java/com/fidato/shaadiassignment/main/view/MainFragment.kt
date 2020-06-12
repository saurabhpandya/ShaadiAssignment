package com.fidato.shaadiassignment.main.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import com.fidato.shaadiassignment.base.BaseFragment
import com.fidato.shaadiassignment.databinding.MainFragmentBinding
import com.fidato.shaadiassignment.main.adapter.CardStackAdapter
import com.fidato.shaadiassignment.main.viewmodel.MainViewModel
import com.fidato.shaadiassignment.utility.MatchActionListner
import com.fidato.shaadiassignment.utility.Status
import com.fidato.shaadiassignment.utility.Utility
import com.yuyakaido.android.cardstackview.*

class MainFragment : BaseFragment(), CardStackListener, MatchActionListner {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val TAG = MainFragment::class.java.canonicalName

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    private val manager by lazy { CardStackLayoutManager(this.activity, this) }
    private val stackAdapter by lazy { CardStackAdapter(emptyList(), this) }

    private var lastPosition = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = setupViewModel(this.activity!!, MainViewModel::class.java) as MainViewModel
        binding.vm = viewModel
        initialize()
        getData()
    }

    private fun initialize() {
        manager.apply {
            setStackFrom(StackFrom.None)
            setVisibleCount(3)
            setTranslationInterval(8.0f)
            setScaleInterval(0.95f)
            setSwipeThreshold(0.3f)
            setMaxDegree(20.0f)
            setDirections(Direction.HORIZONTAL)
            setCanScrollHorizontal(true)
            setCanScrollVertical(true)
            setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
            setOverlayInterpolator(LinearInterpolator())
            setStackFrom(StackFrom.Top)
        }

        binding.cardStackView.apply {
            layoutManager = manager
            adapter = stackAdapter
            itemAnimator.apply {
                if (this is DefaultItemAnimator) {
                    supportsChangeAnimations = false
                }
            }
        }

    }

    private fun getData() {
        viewModel.getMatches().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> binding.prgrs.visibility = View.VISIBLE
                Status.ERROR -> binding.prgrs.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    binding.prgrs.visibility = View.GONE
                    Log.d(TAG, "Sucess:${it.data}")
                    if (it.data!!) {
                        setupObserver()
                    }
                }
            }

        })
    }

    private fun setupObserver() {

//        viewModel.getAcceptedMatchesFromDB().observe(viewLifecycleOwner, Observer {
//            when (it.status) {
//                Status.LOADING -> Log.d(TAG, "Loading")
//                Status.ERROR -> Log.d(TAG, "Error:${it.message}")
//                Status.SUCCESS -> {
//                    Log.d(TAG, "Sucess:${it.data?.size}")
//                    Log.d(TAG, "Sucess:Data${it.data}")
//                    if (lastPosition == -1) {
//                        stackAdapter.setMatches(0, it.data!!)
//                    } else {
//                        stackAdapter.setMatches(lastPosition, it.data!!)
//                    }
//
//                }
//            }
//        })

        viewModel.getMatchesFromDB().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> binding.prgrs.visibility = View.VISIBLE
                Status.ERROR -> binding.prgrs.visibility = View.VISIBLE
                Status.SUCCESS -> {
                    binding.prgrs.visibility = View.GONE
                    Log.d(TAG, "Sucess:${it.data?.size}")
                    Log.d(TAG, "Sucess:Data${it.data}")
                    if (lastPosition == -1) {
                        stackAdapter.setMatches(0, it.data!!)
                    } else {
                        stackAdapter.setMatches(lastPosition, it.data!!)
                    }
                }
            }
        })
    }

    override fun onCardDisappeared(view: View?, position: Int) {
        lastPosition = position
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) {

    }

    override fun onCardSwiped(direction: Direction?) {
        var isAccepted = -1
        if (direction != null) {
            if (direction.equals(Direction.Left)) {
                isAccepted = 0
            } else if (direction.equals(Direction.Right)) {
                isAccepted = 1
            }
        }

        // Update adapter and update it to database
        val lastMatchModel = viewModel.matchesList.get(lastPosition)
        if (lastMatchModel.isAccepted != isAccepted) {
            lastMatchModel.isAccepted = isAccepted
            lastMatchModel.dateModified = Utility.getCurrentTimeStamp()
            viewModel.matchesList.get(lastPosition).isAccepted = isAccepted
            stackAdapter.notifyItemRangeChanged(lastPosition, 1)
            viewModel.updateMatch(lastMatchModel)
        }
    }

    override fun onCardCanceled() {

    }

    override fun onCardAppeared(view: View?, position: Int) {

    }

    override fun onCardRewound() {

    }

    override fun matchActionListner(isAccepted: Boolean, position: Int) {
        lastPosition = position
        swipe(isAccepted)
    }

    private fun swipe(accepted: Boolean) {
        val setting = SwipeAnimationSetting.Builder()
        setting.setDuration(Duration.Normal.duration)
        setting.setInterpolator(AccelerateInterpolator())
        if (accepted) {
            onCardSwiped(Direction.Right)
            setting.setDirection(Direction.Right)
        } else {
            setting.setDirection(Direction.Left)
            onCardSwiped(Direction.Left)
        }
        manager.setSwipeAnimationSetting(setting.build())
        binding.cardStackView.swipe()
    }

}