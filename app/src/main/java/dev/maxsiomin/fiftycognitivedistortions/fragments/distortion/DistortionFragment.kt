package dev.maxsiomin.fiftycognitivedistortions.fragments.distortion

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import dev.maxsiomin.fiftycognitivedistortions.R
import dev.maxsiomin.fiftycognitivedistortions.databinding.FragmentDistortionBinding
import dev.maxsiomin.fiftycognitivedistortions.util.BaseViewModel
import dev.maxsiomin.fiftycognitivedistortions.util.SharedPrefsConfig.DISTORTIONS_SHOWED

@AndroidEntryPoint
class DistortionFragment : Fragment(R.layout.fragment_distorion) {

    private lateinit var binding: FragmentDistortionBinding

    private val viewModel by viewModels<BaseViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDistortionBinding.bind(view)

        setupImageView(getIndex())
    }

    private fun getIndex(): Int {
        val nameInArgs = arguments?.getString(DISTORTION_TO_SHOW)
        if (nameInArgs != null) {
            return allDistortions.indexOf(nameInArgs)
        }
        return viewModel.sharedPrefs.getInt(DISTORTIONS_SHOWED, 1) - 1
    }

    private fun setupImageView(index: Int) {
        binding.distortionImageView.setImageDrawable(ContextCompat.getDrawable(requireContext(), drawablesIds[index]))
    }

    companion object {

        const val DISTORTION_TO_SHOW = "distortion_name"

        val drawablesIds = listOf(
            R.drawable.d1,
            R.drawable.d2,
            R.drawable.d3,
            R.drawable.d4,
            R.drawable.d5,
            R.drawable.d6,
            R.drawable.d7,
            R.drawable.d8,
            R.drawable.d9,
            R.drawable.d10,
            R.drawable.d11,
            R.drawable.d12,
            R.drawable.d13,
            R.drawable.d14,
            R.drawable.d15,
            R.drawable.d16,
            R.drawable.d17,
            R.drawable.d18,
            R.drawable.d19,
            R.drawable.d20,
            R.drawable.d21,
            R.drawable.d22,
            R.drawable.d23,
            R.drawable.d24,
            R.drawable.d25,
            R.drawable.d26,
            R.drawable.d27,
            R.drawable.d28,
            R.drawable.d29,
            R.drawable.d30,
            R.drawable.d31,
            R.drawable.d32,
            R.drawable.d33,
            R.drawable.d34,
            R.drawable.d35,
            R.drawable.d36,
            R.drawable.d37,
            R.drawable.d38,
            R.drawable.d39,
            R.drawable.d40,
            R.drawable.d41,
            R.drawable.d42,
            R.drawable.d43,
            R.drawable.d44,
            R.drawable.d45,
            R.drawable.d46,
            R.drawable.d47,
            R.drawable.d48,
            R.drawable.d49,
            R.drawable.d50,
        )
    }
}
