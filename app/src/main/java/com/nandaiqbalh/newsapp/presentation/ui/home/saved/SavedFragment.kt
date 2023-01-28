package com.nandaiqbalh.newsapp.presentation.ui.home.saved

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nandaiqbalh.newsapp.R
import com.nandaiqbalh.newsapp.presentation.ui.home.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import com.nandaiqbalh.newsapp.databinding.FragmentSavedBinding
import com.nandaiqbalh.newsapp.presentation.ui.home.NewsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment() {

	private var _binding: FragmentSavedBinding? = null
	private val binding get() = _binding!!

	private val viewModel: NewsViewModel by activityViewModels()

	lateinit var newsAdapter: NewsAdapter

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment

		_binding = FragmentSavedBinding.inflate(layoutInflater, container, false)

		setupRecyclerView()

		toDetailArticle()

		dragArticle()

		return binding.root
	}

	private fun toDetailArticle(){
		newsAdapter.setOnItemClickListener {
			val bundle = Bundle().apply {
				putSerializable("article", it)
			}
			findNavController().navigate(
				R.id.action_savedFragment_to_articleFragment,
				bundle
			)
		}
	}

	private fun dragArticle(){
		val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
			ItemTouchHelper.UP or ItemTouchHelper.DOWN,
			ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
		) {
			override fun onMove(
				recyclerView: RecyclerView,
				viewHolder: RecyclerView.ViewHolder,
				target: RecyclerView.ViewHolder
			): Boolean {
				return true
			}

			override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
				val position = viewHolder.adapterPosition
				val article = newsAdapter.differ.currentList[position]
				viewModel.deleteArticle(article)
				Snackbar.make(view!!, "Successfully deleted article", Snackbar.LENGTH_LONG).apply {
					setAction("Undo") {
						viewModel.saveArticle(article)
					}
					show()
				}
			}
		}

		ItemTouchHelper(itemTouchHelperCallback).apply {
			attachToRecyclerView(binding.rvSavedNews)
		}

		viewModel.getSavedNews().observe(viewLifecycleOwner, Observer { articles ->
			if (articles.isEmpty()){
				binding.tvSavedEmpty.visibility = View.VISIBLE
			} else {
				newsAdapter.differ.submitList(articles)
			}
		})

	}

	private fun setupRecyclerView() {
		newsAdapter = NewsAdapter()
		binding.rvSavedNews.apply {
			adapter = newsAdapter
			layoutManager = LinearLayoutManager(activity)
		}
	}
}
