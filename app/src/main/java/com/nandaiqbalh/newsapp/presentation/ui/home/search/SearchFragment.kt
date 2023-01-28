package com.nandaiqbalh.newsapp.presentation.ui.home.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandaiqbalh.newsapp.R
import com.nandaiqbalh.newsapp.databinding.FragmentSearchBinding
import com.nandaiqbalh.newsapp.other.util.Constants.Companion.SEARCH_NEWS_TIME_DELAY
import com.nandaiqbalh.newsapp.other.wrapper.Resource
import com.nandaiqbalh.newsapp.presentation.ui.home.NewsAdapter
import com.nandaiqbalh.newsapp.presentation.ui.home.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment() {

	private var _binding: FragmentSearchBinding? = null
	private val binding get() = _binding!!

	private val viewModel: NewsViewModel by activityViewModels()

	lateinit var newsAdapter: NewsAdapter
	private val TAG = "SearchNewsFragment"

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentSearchBinding.inflate(layoutInflater, container, false)

		searchNews()
		setupRecyclerView()
		toDetailArticle()


		return binding.root
	}

	private fun toDetailArticle(){
		newsAdapter.setOnItemClickListener {
			val bundle = Bundle().apply {
				putSerializable("article", it)
			}
			findNavController().navigate(
				R.id.action_searchFragment_to_articleFragment,
				bundle
			)
		}
	}

	private fun searchNews() {

		var job: Job? = null
		binding.etSearch.addTextChangedListener { editable ->
			job?.cancel()
			job = MainScope().launch {
				delay(SEARCH_NEWS_TIME_DELAY)
				editable?.let {
					if (editable.toString().isNotEmpty()) {
						viewModel.searchNews(editable.toString())
					}
				}
			}
		}

		viewModel.searchNews.observe(viewLifecycleOwner, Observer { response ->
			when (response) {
				is Resource.Success -> {
					hideProgressBar()
					response.data?.let { newsResponse ->
						newsAdapter.differ.submitList(newsResponse.articles)
					}
				}
				is Resource.Error -> {
					hideProgressBar()
					response.message?.let { message ->
						Log.e(TAG, "An error occured: $message")
					}
				}
				is Resource.Loading -> {
					showProgressBar()
				}
			}
		})
	}

	private fun hideProgressBar() {
		binding.paginationProgressBar.visibility = View.INVISIBLE
	}

	private fun showProgressBar() {
		binding.paginationProgressBar.visibility = View.VISIBLE
	}

	private fun setupRecyclerView() {
		newsAdapter = NewsAdapter()
		binding.rvSearchNews.apply {
			adapter = newsAdapter
			layoutManager = LinearLayoutManager(activity)
		}
	}

	override fun onDestroy() {
		super.onDestroy()

		_binding = null
	}
}

