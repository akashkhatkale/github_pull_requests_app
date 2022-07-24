package com.demo.github.view.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.demo.github.data.model.PullRequestModel
import com.demo.github.databinding.LayoutSmallProfileBinding
import com.demo.github.databinding.ViewholderFeedRowBinding
import com.demo.github.utils.Constants.PLACEHOLDER_DRAWABLE
import com.demo.github.utils.PullRequestState
import com.demo.github.utils.getDateAsDay
import com.demo.github.utils.getInfoText


class PullRequestAdapter(
    private val context : Context,
    private val glide : RequestManager
) : PagingDataAdapter<PullRequestModel, PullRequestAdapter.PullRequestViewHolder>(differ) {


    inner class PullRequestViewHolder(val binding : ViewholderFeedRowBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        private val differ = object : DiffUtil.ItemCallback<PullRequestModel>(){
            override fun areItemsTheSame(oldItem: PullRequestModel, newItem: PullRequestModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: PullRequestModel, newItem: PullRequestModel): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        val binding = ViewholderFeedRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PullRequestViewHolder(binding)
    }



    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        val pullRequestItem = getItem(position)
        pullRequestItem?.let{pullRequest->
            holder.apply {
                binding.pullRequestTitle.text = pullRequest.title
                binding.pullRequestInfo.text = ""
                binding.pullRequestUserName.text = pullRequest.user.login
                binding.pullRequestInfo.text = getInfoText(pullRequest)
                binding.pullRequestStartDate.text = getDateAsDay(pullRequest.created_at)

                val state = PullRequestState.state[pullRequest.state]
                binding.pullRequestStatus.text = state?.get("state") as String
                binding.pullRequestStatus.setTextColor(ContextCompat.getColor(context, state["color"] as Int))
                binding.pullRequestIcon.setImageDrawable(ContextCompat.getDrawable(context, state["icon"] as Int))

                val user = LayoutSmallProfileBinding.bind(binding.root)
                glide.load(pullRequest.user.avatar_url)
                    .placeholder(context.getDrawable(PLACEHOLDER_DRAWABLE))
                    .error(context.getDrawable(PLACEHOLDER_DRAWABLE))
                    .into(user.pullRequestUserIcon)
            }
        }
    }

}