package org.bedu.bedushop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    private fun getProducts(): MutableList<Product>{
        val products:MutableList<Product> = ArrayList()
        products.add(Product(1,
            "Fjallraven - Foldsack No. 1 Backpack",
            3012.34f,
            "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everydayYour perfect pack for everyday use and walks in the forest.",
            4.5f,
            321,
            "men's clothing",
            "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg"))

        products.add(Product(2,
            "Fjallraven - Goorsatk No. 12 ",
            7612.12f,
            "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
            3.2f,
            221,
            "men's clothing",
            "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg"))

        products.add(Product(3,
            "Mens Cotton Jacket",
            55.99f,
            "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
            3.8f,
            121,
            "men's clothing",
            "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg"
        ))

        products.add(
            Product(4,
                "Mens Casual Slim Fit",
                15.99f,
                "The color could be slightly different between on the screen and in practice. / Please note that body builds vary by person, therefore, detailed size information should be reviewed below on the product description.",
                2.3f,
                10,
                "men's clothing",
                "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg"
            ))
        products.add(
            Product(5,
                "John Hardy Women's Legends Naga Gold & Silver Dragon Station Chain Bracelet",
                695f,
                "From our Legends Collection, the Naga was inspired by the mythical water dragon that protects the ocean's pearl. Wear facing inward to be bestowed with love and abundance, or outward for protection.",
                4.9f,
                561,
                "jewelery",
                "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg"
            ))

        products.add(
            Product(
                6,
                "Solid Gold Petite Micropave ",
                168f,
                "Satisfaction Guaranteed. Return or exchange any order within 30 days.Designed and sold by Hafeez Center in the United States. Satisfaction Guaranteed. Return or exchange any order within 30 days.",
                3.7f,
                139,
                "jewelery",
                "https://fakestoreapi.com/img/61sbMiUnoGL._AC_UL640_QL65_ML3_.jpg"
            ))

        products.add(
            Product(
                7,
                "White Gold Plated Princess",
                9.99f,
                "Classic Created Wedding Engagement Solitaire Diamond Promise Ring for Her. Gifts to spoil your love more for Engagement, Wedding, Anniversary, Valentine's Day...",
                2.8f,
                1990,
                "jewelery",
                "https://fakestoreapi.com/img/71YAIFU48IL._AC_UL640_QL65_ML3_.jpg"
            ))

        return products
    }

    private fun setUpRecyclerView(){
        recyclerProducts.setHasFixedSize(true)
        recyclerProducts.layoutManager = LinearLayoutManager(activity)
        //seteando el Adapter
        val mAdapter = ProductAdapter(requireActivity(),getProducts())
        //asignando el Adapter al RecyclerView
        recyclerProducts.adapter = mAdapter
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpRecyclerView()
    }

}