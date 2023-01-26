package com.nandaiqbalh.newsapp.presentation.ui.onboarding.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nandaiqbalh.newsapp.databinding.FragmentOnboardThreeBinding
import com.nandaiqbalh.newsapp.presentation.ui.home.HomeActivity
import com.nandaiqbalh.newsapp.presentation.ui.onboarding.OnboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnboardThreeFragment : Fragment() {

	private val viewModel: OnboardViewModel by activityViewModels()

	private var _binding: FragmentOnboardThreeBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentOnboardThreeBinding.inflate(inflater, container, false)

		onClickListener()

		return binding.root
	}

	private fun onClickListener(){
		binding.btnNext.setOnClickListener {
			startActivity(Intent(requireContext(), HomeActivity::class.java))
			activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
			activity?.finish()

			// set status on boarding
			viewModel.setStatusOnboarding(true)
		}

	}
}