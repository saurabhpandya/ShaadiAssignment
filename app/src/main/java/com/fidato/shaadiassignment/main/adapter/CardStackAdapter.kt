package com.fidato.shaadiassignment.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fidato.shaadiassignment.R
import com.fidato.shaadiassignment.ShaadiAssignmentApp
import com.fidato.shaadiassignment.databinding.ItemMatchBinding
import com.fidato.shaadiassignment.model.MatchesModel
import com.fidato.shaadiassignment.utility.MatchActionListner

class CardStackAdapter(
    private var matchesModelList: ArrayList<MatchesModel>,
    private var matchActionListner: MatchActionListner
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val binding: ItemMatchBinding =
            DataBindingUtil.inflate(inflater, R.layout.item_match, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val matchModel = matchesModelList[position]
        holder.bindItems(matchModel)
        holder.binding.imgvwAccept.setOnClickListener { v ->
            holder.binding.rightOverlay.visibility = View.INVISIBLE
            matchActionListner.matchActionListner(true, position)
        }
        holder.binding.imgvwDecline.setOnClickListener { v ->
            holder.binding.leftOverlay.visibility = View.INVISIBLE
            matchActionListner.matchActionListner(false, position)
        }
    }

    override fun getItemCount(): Int {
        return matchesModelList.size
    }

    fun setMatches(lastPos: Int, matchesModelList: ArrayList<MatchesModel>) {
        if (lastPos == 0) {
            this.matchesModelList = ArrayList<MatchesModel>()
            this.matchesModelList = matchesModelList
            notifyDataSetChanged()
        } else {
            this.matchesModelList.addAll(matchesModelList)
            notifyItemRangeChanged(lastPos, matchesModelList.size)
        }

    }

    fun getMatches(): ArrayList<MatchesModel> {
        return matchesModelList
    }

    class ViewHolder(val binding: ItemMatchBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItems(matchModel: MatchesModel) {
            if (matchModel.isAccepted == -1) {
                binding.cnstrntlytActions.visibility = View.VISIBLE
                binding.txtvwStatus.visibility = View.GONE
                binding.leftOverlay.visibility = View.VISIBLE
                binding.rightOverlay.visibility = View.VISIBLE
            } else {
                binding.cnstrntlytActions.visibility = View.GONE
                binding.txtvwStatus.visibility = View.VISIBLE
                binding.leftOverlay.visibility = View.INVISIBLE
                binding.rightOverlay.visibility = View.INVISIBLE
                if (matchModel.isAccepted == 1) {
                    binding.txtvwStatus.text = "You have accepted invitation"
                } else if (matchModel.isAccepted == 0) {
                    binding.txtvwStatus.text = "You have declined invitation"
                }
            }
            binding.txtvwName.text = StringBuilder()
                .append(matchModel.firstName)
                .append(" ")
                .append(matchModel.lastName)
                .toString()

            binding.txtvwAgeGender.text = StringBuilder()
                .append(matchModel.dobAge)
                .append(", ")
                .append(matchModel.gender)
                .toString()

            binding.txtvwLocation.text = StringBuilder()
                .append(matchModel.city)
                .append(", ")
                .append(matchModel.state)
                .append(", ")
                .append(matchModel.country)
                .toString()

            Glide.with(ShaadiAssignmentApp.instance)
                .load(matchModel.pictureLarge)
                .into(binding.imgvwPic)

        }
    }
}