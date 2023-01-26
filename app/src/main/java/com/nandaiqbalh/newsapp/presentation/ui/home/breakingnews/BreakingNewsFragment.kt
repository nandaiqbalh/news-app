package com.nandaiqbalh.newsapp.presentation.ui.home.breakingnews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nandaiqbalh.newsapp.R
import com.nandaiqbalh.newsapp.databinding.FragmentBreakingNewsBinding

class BreakingNewsFragment : Fragment() {

	private var _binding: FragmentBreakingNewsBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentBreakingNewsBinding.inflate(layoutInflater, container, false)

		return binding.root
	}

	override fun onDestroy() {
		super.onDestroy()
		_binding = null
	}
}