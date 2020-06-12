package com.fidato.shaadiassignment.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fidato.shaadiassignment.R
import com.fidato.shaadiassignment.ShaadiAssignmentApp
import com.fidato.shaadiassignment.databinding.ItemSpotBinding
import com.fidato.shaadiassignment.model.MatchesModel
import com.fidato.shaadiassignment.utility.MatchActionListner

class CardStackAdapter(
    private var matchesModelList: List<MatchesModel> = emptyList(),
    private var matchActionListner: MatchActionListner
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val binding: ItemSpotBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_spot, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val matchModel = matchesModelList[position]
        holder.bindItems(matchModel)
        holder.binding.imgvwAccept.setOnClickListener { v ->
            matchActionListner.matchActionListner(true, position)
        }
        holder.binding.imgvwDecline.setOnClickListener { v ->
            matchActionListner.matchActionListner(false, position)
        }
    }

    override fun getItemCount(): Int {
        return matchesModelList.size
    }

    fun setMatches(lastPos: Int, matchesModelList: List<MatchesModel>) {
        this.matchesModelList = matchesModelList
        notifyItemRangeChanged(lastPos, matchesModelList.size)
    }

    fun getMatches(): List<MatchesModel> {
        return matchesModelList
    }

    class ViewHolder(val binding: ItemSpotBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItems(matchModel: MatchesModel) {
            if (matchModel.isAccepted == -1) {
                binding.cnstrntlytActions.visibility = View.VISIBLE
                binding.txtvwStatus.visibility = View.GONE
            } else {
                binding.cnstrntlytActions.visibility = View.GONE
                binding.txtvwStatus.visibility = View.VISIBLE
                if (matchModel.isAccepted == 1) {
                    binding.txtvwStatus.text = "You have accepted invitation"
                } else if (matchModel.isAccepted == 0) {
                    binding.txtvwStatus.text = "You have declined invitation"
                }
            }
            binding.itemName.text = "${matchModel.firstName}. ${matchModel.lastName}"
            binding.itemCity.text = matchModel.city
            Glide.with(ShaadiAssignmentApp.instance)
                .load(matchModel.pictureLarge)
                .into(binding.itemImage)

        }
    }
}