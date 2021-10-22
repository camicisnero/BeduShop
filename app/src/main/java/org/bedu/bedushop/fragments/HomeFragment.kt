package org.bedu.bedushop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*
import io.realm.Realm
import okhttp3.*
import org.bedu.bedushop.adapters.ProductRAdapter
import org.bedu.bedushop.classes.ProductR
import org.bedu.bedushop.R

class HomeFragment : Fragment() {

    private lateinit var products: List<ProductR>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun setUpRecyclerView(){
        recyclerProducts?.setHasFixedSize(true)
        recyclerProducts?.layoutManager = LinearLayoutManager(activity)
        recyclerProducts?.adapter = ProductRAdapter(products)
        recyclerProducts?.visibility = View.VISIBLE
        progressBar?.visibility = View.GONE

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val realm = Realm.getDefaultInstance()
        products = realm.where(ProductR::class.java).findAll()
        setUpRecyclerView()
    }

}