package org.bedu.bedushop.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.bedu.bedushop.Classes.Product
import org.bedu.bedushop.Classes.ProductR
import org.bedu.bedushop.Fragments.HomeFragmentDirections
import org.bedu.bedushop.R

class ProductRAdapter(private val products:List<ProductR>): RecyclerView.Adapter<ProductRAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_product,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        val action = HomeFragmentDirections.actionNavigationHomeToDetailFragment(
            product.title ?: "title", product.price ?: 0f, product.description ?: "description", product.image?:"",
            product.rate?: 0f, product.count ?: 0, getPriceQuota(product.price?:0f,6),product.id?:position
        )

        holder.itemView.setOnClickListener(
            Navigation.createNavigateOnClickListener(action)
        )

        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return products.size
    }

    class ViewHolder(view: View):RecyclerView.ViewHolder(view){
        private var title = view.findViewById<TextView>(R.id.tv_title)
        private var price = view.findViewById<TextView>(R.id.tv_price)
        private var valuation = view.findViewById<RatingBar>(R.id.rtg_valuation)
        private var calification = view.findViewById<TextView>(R.id.tv_calification)
        private var image = view.findViewById<ImageView>(R.id.img_product)


        fun bind(product: ProductR){
            title.text = product.title
            price.text = (product.price).toString()
            valuation.rating = product.rate?: 0f
            calification.text = product.count.toString()
            Picasso.get().load(product.image).into(image)
        }
    }

    private fun getPriceQuota(price:Float, quota:Int):Float{
        return price/quota
    }
}