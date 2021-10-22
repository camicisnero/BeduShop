package org.bedu.bedushop.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.bedu.bedushop.classes.ProductR
import org.bedu.bedushop.fragments.HomeFragmentDirections
import org.bedu.bedushop.R

class ProductRAdapter(private val products:List<ProductR>): RecyclerView.Adapter<ProductRAdapter.ViewHolder>() {
private lateinit var image:ImageView


     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product,parent,false)

        image = view.findViewById(R.id.img_product)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        val action = HomeFragmentDirections.actionNavigationHomeToDetailFragment(
            product.title ?: "title", product.price ?: 0f, product.description ?: "description", product.image?:"",
            product.rate?: 0f, product.count ?: 0, getPriceQuota(product.price?:0f,6),product.id?:position
        )

        val extras = FragmentNavigatorExtras(image to product.id.toString())

        holder.cardView.animation = AnimationUtils.loadAnimation (holder.itemView.context, R.anim.scale_to_up)

        holder.itemView.setOnClickListener{
            Navigation.findNavController(it).navigate(action, extras)
        }

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
        var cardView:CardView = view.findViewById(R.id.cardView)

        fun bind(product: ProductR){
            image.transitionName = product.id.toString()
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