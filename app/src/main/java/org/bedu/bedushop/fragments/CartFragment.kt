package org.bedu.bedushop.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import org.bedu.bedushop.adapters.ProductCartAdapter
import org.bedu.bedushop.classes.ProductR
import org.bedu.bedushop.R
import org.bedu.bedushop.databinding.FragmentCartBinding

class CartFragment : Fragment() {

    private val args: CartFragmentArgs by navArgs()

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    private lateinit var tvEmptyCart: TextView
    private lateinit var btnPay: Button
    private lateinit var recyclerView : RecyclerView
    private val products = mutableListOf<Pair<String, Int>>()

    companion object{
        val PREFS_NAME = "org.bedu.products"
    }
    private lateinit var preferences: SharedPreferences

    private lateinit var listener: (String, Int) -> Unit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        tvEmptyCart = binding.emptyCart
        tvEmptyCart.visibility = View.GONE
        btnPay = binding.btnPay
        recyclerView = binding.recyclerCart

        preferences = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        //Si obtengo un argumento vengo del fragment detail y debo añadirlo o sumarlo en su defecto
        val productId = args.productId
        if (productId != -1){
            val cant = preferences.getInt(productId.toString(), 0)
            preferences.edit()
                .putInt(productId.toString(), cant+1)
                .apply()
        }

        getProducts()
        changedVisibility()
        setUpRecyclerView()

        btnPay.setOnClickListener{
            val subtotal = getSubtotal()
            products.clear()
            val action = CartFragmentDirections.actionNavigationCartToPaymentFragment(subtotal)
            findNavController().navigate(action)
        }

        return view
    }

    private fun getSubtotal(): Float{
        var subtotal = 0f
        val realm = Realm.getDefaultInstance()
        for (p in products) {
            val productR = realm.where(ProductR::class.java).equalTo("id", p.first.toInt()).findFirst()
            Log.d("ProductTile", "${productR?.title}  precio: ${productR?.price}")
            subtotal += productR?.price?.times(p.second) ?: 0f
        }
        return subtotal
    }

    /**
     * Changes visibility if the cart is empty
     */
    private fun changedVisibility(){
        if (products.size>0){
            tvEmptyCart.visibility = View.GONE
            btnPay.visibility = View.VISIBLE
        } else {
            //Si no tengo productos solo muestro que el carrito esta vacío en el textView
            recyclerView.visibility = View.INVISIBLE
            btnPay.visibility = View.INVISIBLE
            tvEmptyCart.visibility = View.VISIBLE
        }
    }

    /**
     * Gets products from DB
     */
    private fun getProducts(){
        val allProducts = preferences.all
        for (data in allProducts){
            products.add(Pair(data.key, data.value) as Pair<String, Int>)
        }

    }

    private fun setListener(){
        listener = { productId, viewId ->
            if (viewId == R.id.cartProductAdd){ //Presione el boton para añadir
                val cant = preferences.getInt(productId, 0)
                preferences.edit()
                    .putInt(productId, cant+1)
                    .apply()
            } else if (viewId == R.id.carProductRemove){ //Presione el boton para eliminar
                val cant = preferences.getInt(productId, 0)
                if (cant == 1){ //Tengo uno entonces debo eliminarlo
                    preferences.edit()
                        .remove(productId)
                        .apply()
                } else if (cant > 1) {
                    preferences.edit()
                        .putInt(productId, cant-1)
                        .apply()
                }
            }
            products.clear()
            //Obtengo los productos luego del cambio
            getProducts()
            //actualizo vistas
            changedVisibility()
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }


    fun setUpRecyclerView(){
        recyclerView.visibility = View.VISIBLE
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        setListener()
        recyclerView.adapter = ProductCartAdapter(products, listener)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}