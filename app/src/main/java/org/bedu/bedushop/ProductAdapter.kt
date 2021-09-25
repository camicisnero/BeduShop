package org.bedu.bedushop

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class ProductAdapter(private val context: Context,
                     private val products:MutableList<Product>): RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductAdapter.ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductAdapter.ViewHolder, position: Int) {
        val product = products[position]
        val bundle = Bundle()
        bundle.putString("TITLE",product.title)
        bundle.putFloat("PRICE",product.price)
        bundle.putString("DESCRIPTION",product.description)
        bundle.putString("IMAGE",product.image)
        bundle.putFloat("VALUATION",product.valuation)
        bundle.putInt("CALIFICATION", product.calification)
        bundle.putFloat("QUOTA", getPriceQuota(product.price,6))


        holder.itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_navigation_home_to_detailFragment,bundle)
        )
        holder.bind(product,context)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ViewHolder(val view: View):RecyclerView.ViewHolder(view){
        private var title = view.findViewById<TextView>(R.id.tv_title)
        private var price = view.findViewById<TextView>(R.id.tv_price)
        private var valuation = view.findViewById<RatingBar>(R.id.rtg_valuation)
        private var calification = view.findViewById<TextView>(R.id.tv_calification)
        private var image = view.findViewById<ImageView>(R.id.img_product)


        fun bind(product: Product, context: Context){
            title.text = product.title
            price.text = (product.price).toString()
            valuation.rating = product.valuation
            calification.text = product.calification.toString()
            Picasso.get().load(product.image).into(image)
        }
    }

    private fun getPriceQuota(price:Float, quota:Int):Float{
        return price/quota
    }
}