package dev.maxsiomin.fiftycognitivedistortions.fragments.distortions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.maxsiomin.fiftycognitivedistortions.R
import dev.maxsiomin.fiftycognitivedistortions.databinding.FragmentDistortionsListBinding
import dev.maxsiomin.fiftycognitivedistortions.fragments.distortion.allDistortions
import dev.maxsiomin.fiftycognitivedistortions.util.BaseViewModel
import dev.maxsiomin.fiftycognitivedistortions.util.SharedPrefsConfig.DISTORTIONS_SHOWED

/**
 * A fragment representing a list of Items.
 */
@AndroidEntryPoint
class DistortionsFragment : Fragment(R.layout.fragment_distortions_list) {

    private val viewModel by viewModels<BaseViewModel>()

    private val distortions: List<String> by lazy {
        allDistortions.subList(0, viewModel.sharedPrefs.getInt(DISTORTIONS_SHOWED, 1)).reversed()
    }

    private lateinit var binding: FragmentDistortionsListBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDistortionsListBinding.bind(view)

        // Set the adapter
        binding.root.adapter = DistortionsRecyclerViewAdapter(distortions) {
            val direction = DistortionsFragmentDirections.actionDistortionsFragmentToDistortionFragment(it)
            findNavController().navigate(direction)
        }
    }
}
