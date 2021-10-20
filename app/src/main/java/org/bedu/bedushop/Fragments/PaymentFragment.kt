package org.bedu.bedushop.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import org.bedu.bedushop.Adapters.ProfileAdapter
import org.bedu.bedushop.Classes.ItemsProfile
import org.bedu.bedushop.R
import org.bedu.bedushop.databinding.FragmentPaymentBinding

class PaymentFragment: Fragment() {

    private val listener : (Int) -> Unit = {}

    private val args: PaymentFragmentArgs by navArgs()

    private val itemsRecycler = listOf(
        ItemsProfile(
            R.string.itemActLocation,
            R.drawable.ic_location_on
        ),
        ItemsProfile(
            R.string.optionMethodPayment,
            R.drawable.ic_credit_card
        )
    )

    //binging
    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerPayment: RecyclerView
    private val shippingPrice = 30f

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        val view = binding.root

        recyclerPayment = binding.recyclerPayment
        binding.priceSubtotal.text = args.subtotal.toString()
        binding.priceTotal.text = (args.subtotal+ shippingPrice).toString()

        binding.buttonPay.setOnClickListener{
            val preferences = requireActivity().getSharedPreferences(CartFragment.PREFS_NAME, Context.MODE_PRIVATE)
            preferences.edit().clear().apply()
            findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToSuccessfulPaymentFragment())
        }

        setUpRecyclerView()

        return view
    }

    private fun setUpRecyclerView(){
        recyclerPayment?.setHasFixedSize(true)
        val mAdapter = ProfileAdapter(itemsRecycler, listener)
        recyclerPayment?.adapter = mAdapter
    }

}