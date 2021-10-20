package org.bedu.bedushop.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.bedu.bedushop.databinding.FragmentSuccessfulPaymentBinding

class SuccessfulPaymentFragment: Fragment() {

    //binging
    private var _binding: FragmentSuccessfulPaymentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSuccessfulPaymentBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnGoToHome.setOnClickListener {
            findNavController().navigate(SuccessfulPaymentFragmentDirections.actionSuccessfulPaymentFragmentToNavigationHome())
        }

        return view
    }
}