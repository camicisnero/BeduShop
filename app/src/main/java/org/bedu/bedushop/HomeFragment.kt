package org.bedu.bedushop

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.IOException
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HomeFragment : Fragment() {

    private val url = "https://fakestoreapi.com/products"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun getJsonDataFromAsset(context: Context, fileName: String = "products.json"): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }

    private fun getProducts(context: Context): List<Product> {
        val jsonString = getJsonDataFromAsset(context)
        val listProductType = object : TypeToken<List<Product>>() {}.type
        return Gson().fromJson(jsonString, listProductType)
    }

    private fun setUpRecyclerView(){
        recyclerProducts.setHasFixedSize(true)
        recyclerProducts.layoutManager = LinearLayoutManager(activity)
        val mAdapter = ProductAdapter(getProducts(requireActivity()))
        recyclerProducts.adapter = mAdapter
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
    }

}