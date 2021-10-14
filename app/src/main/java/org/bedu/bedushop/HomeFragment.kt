package org.bedu.bedushop

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.json.JSONObject
import java.lang.Exception

class HomeFragment : Fragment() {

    private val url = "https://fakestoreapi.com/products"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun getProducts(context: Context) {
        val okHttpClient = OkHttpClient()

        val request = Request.Builder()
            .url(url)
            .build()

        val clientBuilder = okHttpClient.newBuilder()
        clientBuilder.build()
            .newCall(request)
            .enqueue(object: Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Toast.makeText(context, "ERROR REQUEST", Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(call: Call, response: Response) {
                    val body = response.body?.string()
                    try {
                        activity?.runOnUiThread {
                            if(response.isSuccessful) {
                                val listProductType = object : TypeToken<List<Product>>() {}.type
                                val mAdapter = ProductAdapter(Gson().fromJson(body, listProductType))
                                recyclerProducts.adapter = mAdapter
                                recyclerProducts.visibility = View.VISIBLE
                                progressBar.visibility = View.INVISIBLE
                            }
                        }

                    } catch (e: Exception){
                        e.printStackTrace()
                    }
                }
            })
    }

    private fun setUpRecyclerView(){
        recyclerProducts.setHasFixedSize(true)
        recyclerProducts.layoutManager = LinearLayoutManager(activity)
        getProducts(requireActivity())

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
    }

}