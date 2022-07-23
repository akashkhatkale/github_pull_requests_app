package com.demo.github.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.forEach
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.RequestManager
import com.demo.github.R
import com.demo.github.data.model.PullRequestModel
import com.demo.github.databinding.FragmentFeedBinding
import com.demo.github.databinding.LayoutProfileBinding
import com.demo.github.utils.Constants.FEED_FRAGMENT_LOG
import com.demo.github.utils.Constants.NO_PULL_REQUEST_STATUS
import com.demo.github.utils.ErrorConstants.UNKNOWN_ERROR
import com.demo.github.utils.getHeadingTitle
import com.demo.github.view.viewholder.PullRequestAdapter
import com.demo.github.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private lateinit var menuView : LayoutProfileBinding
    private val viewModel : MainViewModel by viewModels()

    @Inject
    lateinit var feedAdapter : PullRequestAdapter
    @Inject
    lateinit var requestManager: RequestManager



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.homeToolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.title = ""

        // menu
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Add menu items here
                menuInflater.inflate(R.menu.home_menu, menu)

                val menuItem = menu.findItem(R.id.profileIcon)
                menuView = LayoutProfileBinding.bind(menuItem.actionView)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return true
            }
        }, viewLifecycleOwner)

        // user
        viewModel.userValue.observe(viewLifecycleOwner){response ->
            response.data?.let{
                setProfileImage(it.avatar_url)
            }
        }

        // recycler view
        setUpRecycler()


        // observe state
        viewModel.stateValue.observe(viewLifecycleOwner){
            binding.pullRequestHeading.text = resources.getString(getHeadingTitle(it))
        }

        // observe pull requests
        viewModel.pullRequestsValue.observe(viewLifecycleOwner){response ->
            refreshRecyclerView(response)
        }
    }


    // recycler view
    private fun setUpRecycler() {
        binding.feedRecyclerView.apply {
            adapter = feedAdapter
            layoutManager = LinearLayoutManager(requireActivity())
        }
        feedAdapter.addLoadStateListener {loadState ->
            if (loadState.source.refresh is LoadState.Loading) {
                // loading
                Log.d(FEED_FRAGMENT_LOG, "loadPullRequestsFeed: res : loading")
                showLoading(View.VISIBLE)
                showStatus(View.INVISIBLE)
            } else {
                showLoading(View.INVISIBLE)

                // getting the error
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    showStatus(View.VISIBLE)
                    setStatus(it.error.message ?: UNKNOWN_ERROR)
                }

                if(feedAdapter.itemCount <= 0) {
                    showStatus(View.VISIBLE)
                    setStatus(NO_PULL_REQUEST_STATUS)
                }
            }
        }
    }
    private fun refreshRecyclerView(feedResponse : PagingData<PullRequestModel>){
        feedAdapter.submitData(viewLifecycleOwner.lifecycle, feedResponse)
    }


    // update ui
    private fun showLoading(visibility : Int){
        binding.feedProgressBar.visibility = visibility
    }

    private fun showStatus(visibility: Int){
        binding.feedStatus.visibility = visibility
    }

    private fun setStatus(status : String){
        binding.feedStatus.text = status

    }

    private fun setProfileImage(imageUrl: String){
        requestManager
            .load(imageUrl)
            .placeholder(R.drawable.icon_person)
            .error(R.drawable.icon_person)
            .into(menuView.pullRequestUserIcon)
    }
}