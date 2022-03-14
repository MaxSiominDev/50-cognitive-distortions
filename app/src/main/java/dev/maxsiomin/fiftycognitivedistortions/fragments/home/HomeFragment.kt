package dev.maxsiomin.fiftycognitivedistortions.fragments.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.maxsiomin.fiftycognitivedistortions.R
import dev.maxsiomin.fiftycognitivedistortions.activity.MainActivity
import dev.maxsiomin.fiftycognitivedistortions.databinding.FragmentHomeBinding
import dev.maxsiomin.fiftycognitivedistortions.util.SharedPrefsConfig.DISTORTIONS_SHOWED
import dev.maxsiomin.fiftycognitivedistortions.util.SharedPrefsConfig.OPENED_FIRST_TIME

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding

    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (viewModel.sharedPrefs.getBoolean(OPENED_FIRST_TIME, true)) {
            viewModel.sharedPrefs.edit().putBoolean(OPENED_FIRST_TIME, false).apply()
            findNavController().navigate(R.id.infoFragment)
        }

        binding = FragmentHomeBinding.bind(view)

        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            this.viewModel = this@HomeFragment.viewModel

            distortionsButton.setOnClickListener {
                when (this@HomeFragment.viewModel.sharedPrefs.getInt(DISTORTIONS_SHOWED, 1)) {
                    1 -> findNavController().navigate(R.id.action_homeFragment_to_distortionFragment)
                    else -> {
                        findNavController().navigate(R.id.action_homeFragment_to_distortionsFragment)
                    }
                }
            }

            notifyButton.setOnClickListener {
                this@HomeFragment.viewModel.onNotifyButtonClicked(activity as MainActivity)
            }
        }
    }
}
