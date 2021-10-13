package org.bedu.bedushop

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import kotlin.random.Random

class ProfileFragment : Fragment() {

    private val baseUrl = "https://reqres.in/api/users/"

    private lateinit var userAvatar : ShapeableImageView
    private lateinit var username : TextView
    private lateinit var userEmail : TextView

    private val listProfile = listOf(
        ItemsProfile(
            R.string.optionDirections,
            R.drawable.ic_location_on
        ),
        ItemsProfile(
            R.string.optionMethodPayment,
            R.drawable.ic_credit_card
        ),
        ItemsProfile(
            R.string.optionDelivery,
            R.drawable.ic_restore
        ),
        ItemsProfile(
            R.string.optionNotifications,
            R.drawable.ic_notifications
        ),
        ItemsProfile(
            R.string.optionChangePassword,
            R.drawable.ic_lock
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_profile, container, false)

        userAvatar = view.findViewById(R.id.userAvatar)
        username = view.findViewById(R.id.username)
        userEmail = view.findViewById(R.id.userEmail)

        dataRequest(view)

        return view
    }

    private fun dataRequest(view: View) {
        val okHttpClient = OkHttpClient()

        val userNumber = Random.nextInt(1,12)
        val url = "$baseUrl$userNumber"

        val request = Request.Builder()
            .url(url)
            .build()

        val clientBuilder = okHttpClient.newBuilder()
        clientBuilder.build()
            .newCall(request)
            .enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Snackbar.make(view, R.string.searchUserDataError, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.snackbarAction) {}
                        .show()
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    try {
                        val json = JSONObject(body)

                        activity?.runOnUiThread {
                            val data = JSONObject(json.getString("data"))
                            username.text = data.getString("first_name")
                            userEmail.text = data.getString("email")
                            Picasso.get().load(data.getString("avatar")).into(userAvatar)
                        }

                    } catch (e: Error) {
                        Log.e("Error", e.toString())
                    }
                }
            })
    }

    private fun setUpRecyclerVie(){
        recyclerProfile.setHasFixedSize(true)
        recyclerProfile.layoutManager = LinearLayoutManager(activity)
        val mAdapter = ProfileAdapter(listProfile)
        recyclerProfile.adapter = mAdapter
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerVie()
    }
}