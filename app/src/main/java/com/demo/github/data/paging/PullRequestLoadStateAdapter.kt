package com.demo.github.data.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.demo.github.R
import com.demo.github.databinding.LayoutLoadStateBinding

class LoadStateViewHolder(
  parent: ViewGroup,
  retry: () -> Unit
) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_load_state, parent, false)
) {
  private val binding = LayoutLoadStateBinding.bind(itemView)
  private val progressBar: ProgressBar = binding.progressBar
  private val errorMsg: TextView = binding.errorMsg
  private val retry: TextView = binding.retryButton
    .also {
      it.setOnClickListener { retry() }
    }
  
  fun bind(loadState: LoadState) {
    if (loadState is LoadState.Error) {
      errorMsg.text = loadState.error.localizedMessage
    }
    
    progressBar.isVisible = loadState is LoadState.Loading
    retry.isVisible = loadState is LoadState.Error
    errorMsg.isVisible = loadState is LoadState.Error
  }
}


class PullRequestLoadStateAdapter(
  private val retry: () -> Unit
) : LoadStateAdapter<LoadStateViewHolder>() {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    loadState: LoadState
  ) = LoadStateViewHolder(parent, retry)
  
  override fun onBindViewHolder(
    holder: LoadStateViewHolder,
    loadState: LoadState
  ) = holder.bind(loadState)
}
