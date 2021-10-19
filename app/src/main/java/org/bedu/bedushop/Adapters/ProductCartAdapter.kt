package org.bedu.bedushop.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import io.realm.Realm
import kotlinx.android.synthetic.main.item_option_profile.view.*
import org.bedu.bedushop.Classes.ItemsProfile
import org.bedu.bedushop.Classes.ProductR
import org.bedu.bedushop.R

class ProductCartAdapter(private val products: MutableList<Pair<String, Int>>
                         ,private val clickListener: (String, Int) ->Unit
                        ): RecyclerView.Adapter<ProductCartAdapter.ViewHolder>() {

    class ViewHolder(view: View, clickAtPosition: (Int, Int)->Unit): RecyclerView.ViewHolder(view){
        private val image = view.findViewById<ImageView>(R.id.cartProductImage)
        private val title = view.findViewById<TextView>(R.id.cartProductTitle)
        private val price = view.findViewById<TextView>(R.id.cartProductPrice)
        private val cantidad = view.findViewById<TextView>(R.id.cartProductCant)
        private val btnAdd = view.findViewById<ImageView>(R.id.cartProductAdd)
        private val btnRemove = view.findViewById<ImageView>(R.id.carProductRemove)


        init {
            btnAdd.setOnClickListener {
                clickAtPosition(adapterPosition, it.id)
            }
            btnRemove.setOnClickListener {
                clickAtPosition(adapterPosition, it.id)
            }
        }

        fun bind(product: ProductR, cant: Int){
            Picasso.get().load(product.image).into(image)
            title.text = product.title
            price.text = product.price.toString()
            cantidad.text = cant.toString()
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = ViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_cart_product, parent, false),
        ){position, viewId ->
            clickListener(products[position].first, viewId)
            //Para setear el click listener una sola vez, le paso el id del producto
            //y de la vista para saber cual fue presionado
        }

        return view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]

        val realm = Realm.getDefaultInstance()
        val productR = realm.where(ProductR::class.java).equalTo("id", product.first.toInt()).findFirst()

        if (productR != null) {
            holder.bind(productR, product.second)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }
}