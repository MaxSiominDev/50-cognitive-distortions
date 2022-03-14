package dev.maxsiomin.fiftycognitivedistortions.fragments.info

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.maxsiomin.fiftycognitivedistortions.R
import dev.maxsiomin.fiftycognitivedistortions.databinding.FragmentInfoBinding

@AndroidEntryPoint
class InfoFragment : Fragment(R.layout.fragment_info) {

    private lateinit var binding: FragmentInfoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentInfoBinding.bind(view)

        binding.buttonOk.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}
