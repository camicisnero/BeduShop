package org.bedu.bedushop

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import java.util.Locale

class AddressFragment: BottomSheetDialogFragment(){

    private var latitude = 19.3758498
    private var longitude = -99.1454907

    private lateinit var close: ImageView
    private lateinit var listView: ListView
    private lateinit var btnUpdateLocation: MaterialButton
    private lateinit var tvActualLocation: TextView

    private val items_list = arrayOf(
        "Av. Siempreviva 123 \nSprinfield",
        "Av. inexistente 321 \nCiudad de México"
    )


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_address, container, false)

        close = view.findViewById(R.id.ivClose)
        listView = view.findViewById(R.id.listViewAddressFragment)
        btnUpdateLocation = view.findViewById(R.id.btnUpdateLocation)
        tvActualLocation = view.findViewById(R.id.tvActualLocation)

        val itemsAdapter =
            activity?.let {
                ArrayAdapter<String>(
                    it,
                    android.R.layout.simple_list_item_1,
                    items_list
                )
            }

        listView.adapter = itemsAdapter


        close.setOnClickListener {
            dismiss()
        }

        btnUpdateLocation.setOnClickListener {
            try {
                val geocoder = Geocoder(requireContext(), Locale.getDefault())
                val addresses = geocoder.getFromLocation(latitude, longitude, 1)

                val addres = addresses[0]
                tvActualLocation.text = addres.getAddressLine(0).toString() + addres.locality

            }catch (e: Exception){
                e.printStackTrace()
                Toast.makeText(requireContext(), "Ups... Algo salió mal", Toast.LENGTH_SHORT).show()
            }

        }

        return view
    }



}