package com.nandaiqbalh.newsapp.presentation.ui.home.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.nandaiqbalh.newsapp.data.network.models.news.Article
import com.nandaiqbalh.newsapp.databinding.FragmentArticleBinding
import com.nandaiqbalh.newsapp.presentation.ui.home.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleFragment : Fragment() {

	private var _binding: FragmentArticleBinding? = null
	private val binding get () = _binding!!

	private val viewModel: NewsViewModel by activityViewModels()


	private val args: ArticleFragmentArgs by navArgs()
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentArticleBinding.inflate(layoutInflater, container, false)

		val article = args.article
		binding.webView.apply {
			webViewClient = WebViewClient()
			loadUrl(article.url!!)
		}

		saveArticle(article)

		return binding.root
	}

	private fun saveArticle(article:Article){
		binding.fab.setOnClickListener {
			viewModel.saveArticle(article)
			view?.let {
					it1 -> Snackbar.make(
				it1,
				"Article saved successfully",
				Snackbar.LENGTH_SHORT).show()
			}
		}
	}

	override fun onDestroy() {
		super.onDestroy()

		_binding = null
	}
}