package com.nandaiqbalh.newsapp.presentation.ui.onboarding.fragments

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.nandaiqbalh.newsapp.R
import com.nandaiqbalh.newsapp.presentation.ui.home.HomeActivity
import com.nandaiqbalh.newsapp.presentation.ui.onboarding.OnboardViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashscreenFragment : Fragment() {

	private val viewModel: OnboardViewModel by activityViewModels()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		val view = inflater.inflate(R.layout.fragment_splashscreen, container, false)

		loadingScreen()

		return view
	}

	private fun loadingScreen() {
		Handler(Looper.getMainLooper()).postDelayed(this::isTheFirstTime, 5000)
	}

	private fun isTheFirstTime() {

		lifecycleScope.launchWhenCreated {
			viewModel.getStatusOnboarding().observe(viewLifecycleOwner) {
				if (it == false) {
					findNavController().navigate(R.id.action_splashscreenFragment_to_onboardOneFragment)
				} else {
					startActivity(Intent(requireContext(), HomeActivity::class.java))
					activity?.overridePendingTransition(
						android.R.anim.fade_in, android.R.anim.fade_out
					)
					activity?.finish()
				}
			}
		}


	}

}