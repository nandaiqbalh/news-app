package com.nandaiqbalh.newsapp.presentation.ui.home.breakingnews

import android.content.ContentValues.TAG
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.nandaiqbalh.newsapp.R
import com.nandaiqbalh.newsapp.databinding.FragmentBreakingNewsBinding
import com.nandaiqbalh.newsapp.other.wrapper.Resource
import com.nandaiqbalh.newsapp.presentation.ui.home.NewsAdapter
import com.nandaiqbalh.newsapp.presentation.ui.home.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {

	private var _binding: FragmentBreakingNewsBinding? = null
	private val binding get() = _binding!!

	private val viewModel: NewsViewModel by activityViewModels()

	lateinit var newsAdapter: NewsAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentBreakingNewsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		observeData()
		setupRecyclerView()
	}

	private fun observeData() {

		viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
			when(response) {
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
		binding.rvBreakingNews.apply {
			adapter = newsAdapter
			layoutManager = LinearLayoutManager(activity)
		}
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}