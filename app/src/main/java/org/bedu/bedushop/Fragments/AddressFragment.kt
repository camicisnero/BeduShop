package org.bedu.bedushop.Fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import org.bedu.bedushop.R
import java.util.Locale

class AddressFragment: BottomSheetDialogFragment(){

    companion object{
        const val PERMISSION_ID = 33
    }

    //Obeto que obtiene la localización
    private lateinit var mFusedLocationClient: FusedLocationProviderClient

    private var latitude = 0.0
    private var longitude = 0.0

    private lateinit var close: ImageView
    private lateinit var listView: ListView
    private lateinit var btnUpdateLocation: MaterialButton
    private lateinit var tvActualLocation: TextView

    private val items_list = arrayOf(
        "Av. Siempreviva 123 \nSprinfield",
        "Av. inexistente 321 \nCiudad de México"
    )


    @SuppressLint("SetTextI18n")
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

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

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
                getLocation()
                val geocoder = Geocoder(requireContext(), Locale.getDefault())

                Thread{
                    Runnable {
                        //Log.d("GPS", "Enter thread")
                        try {
                            if (isLocationEnabled()) {
                                val addresses = geocoder.getFromLocation(latitude, longitude, 1)
                                if (addresses.size > 0) {
                                    val addres = addresses[0]
                                    activity?.runOnUiThread {
                                        tvActualLocation.text =
                                            "${addres.getAddressLine(0)} ${addres.locality}"
                                    }
                                } else {
                                    activity?.runOnUiThread {
                                        showToast(R.string.locationNotFound)
                                    }
                                }
                            }

                        }catch (e: Exception){
                            e.printStackTrace()
                            showToast(R.string.fatalError)
                        }
                    }.run()
                }.start()

        }

        return view
    }

    private fun showToast(id: Int) {
        Toast.makeText(requireContext(), id , Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("MissingPermission")
    private fun getLocation() {
        if (checkPermissions()) { //verificamos si tenemos permisos
            if (isLocationEnabled()) { //localizamos sólo si el GPS está encendido

                mFusedLocationClient.lastLocation.addOnSuccessListener(requireActivity()) { location ->

                    if (location?.latitude != null) {
                        latitude = location.latitude
                        longitude = location.longitude
                    } else {
                        Log.d("Location","No se encontró dirección")
                    }

                }
            } else {
                val alertDialog: AlertDialog? = activity?.let {
                    val builder = AlertDialog.Builder(it)
                    builder.apply {
                        setPositiveButton(
                            R.string.ok,
                            DialogInterface.OnClickListener { _, _ ->
                                goToTurnLocation()
                            })
                        setNegativeButton(
                            R.string.cancel,
                            DialogInterface.OnClickListener { dialog, _ ->
                                dialog.dismiss()
                            })
                    }
                    // Set other dialog properties
                    builder?.setMessage(R.string.dialog_message)
                    //.setTitle(R.string.dialog_title)

                    // Create the AlertDialog
                    builder.create()
                }

                alertDialog?.show()
            }
        } else{
            //si no se tiene permiso, pedirlo
            requestPermissions()
        }
    }

    private fun goToTurnLocation(){
        //Toast.makeText(requireActivity(), "Debes prender el servicio de GPS", Toast.LENGTH_LONG).show()
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }


    private fun checkGranted(permission: String): Boolean{
        return ActivityCompat.checkSelfPermission(requireActivity(), permission) == PackageManager.PERMISSION_GRANTED
    }

    //Pedir los permisos requeridos para que funcione la localización
    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    private fun isLocationEnabled(): Boolean {
        var locationManager: LocationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }


    private fun checkPermissions(): Boolean {
        if ( checkGranted(Manifest.permission.ACCESS_COARSE_LOCATION) &&
            checkGranted(Manifest.permission.ACCESS_COARSE_LOCATION) ){
            return true
        }
        return false
    }



}