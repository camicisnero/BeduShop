package org.bedu.bedushop.fragments

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import org.bedu.bedushop.activities.HomeActivity
import org.bedu.bedushop.adapters.ProfileAdapter
import org.bedu.bedushop.classes.ItemsProfile
import org.bedu.bedushop.R
import org.bedu.bedushop.databinding.FragmentPaymentBinding

class PaymentFragment: Fragment() {
    private val CHANNEL_OTHERS = "OTROS"

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



    @SuppressLint("NewApi")
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {/*se realiza una comprabación para los dispositivos con SO igual o mayor a oreo*/
            setNotificationChannel()
        }
        binding.buttonPay.setOnClickListener{
            val preferences = requireActivity().getSharedPreferences(CartFragment.PREFS_NAME, Context.MODE_PRIVATE)
            preferences.edit().clear().apply()
            findNavController().navigate(PaymentFragmentDirections.actionPaymentFragmentToSuccessfulPaymentFragment())

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                paymetNotification()
            }
        }

        setUpRecyclerView()

        return view
    }

    private fun setUpRecyclerView(){
        recyclerPayment?.setHasFixedSize(true)
        val mAdapter = ProfileAdapter(itemsRecycler, listener)
        recyclerPayment?.adapter = mAdapter
    }

    /*funcion que crea el canal de notificación*/
    @RequiresApi(Build.VERSION_CODES.O)
    private fun setNotificationChannel(){
        val name = getString(R.string.channelPayment)
        val descriptionText = getString(R.string.descriptionPayment)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_OTHERS, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(channel)
    }

    /*función que lanza las notificaciones*/
    @RequiresApi(Build.VERSION_CODES.S)
    private fun paymetNotification(){
        val pendingIntent: PendingIntent = NavDeepLinkBuilder(requireContext())
            .setComponentName(HomeActivity::class.java)
            .setGraph(R.navigation.bottom_nav_graph)
            .setDestination(R.id.successfulPaymentFragment)
            .createPendingIntent()

        val builder = NotificationCompat.Builder(requireContext(), CHANNEL_OTHERS)
            .setSmallIcon(R.drawable.ic_notification_pay)
            .setContentTitle(getString(R.string.simpleTitle))
            .setContentText(getString(R.string.simpleBody))
            .setColor(ContextCompat.getColor(requireContext(),R.color.secondaryColor))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(requireContext())) {
            notify(1, builder.build())
        }
    }

}