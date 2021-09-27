package org.bedu.bedushop

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso

class DetailFragment : Fragment() {

    private lateinit var txtNameProduct:TextView
    private lateinit var txtPriceProduct:TextView
    private lateinit var txtDescriptionProducto:TextView
    private lateinit var rtgValuationProduct:RatingBar
    private lateinit var txtCalificationProduct:TextView
    private lateinit var imgProduct:ImageView
    private lateinit var txtPriceCuotaProducto:TextView
    private lateinit var btnAddToCart:Button

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        txtNameProduct = view.findViewById(R.id.tv_title)
        txtPriceProduct = view.findViewById(R.id.tv_price)
        txtDescriptionProducto = view.findViewById(R.id.tv_descripcion)
        rtgValuationProduct = view.findViewById(R.id.rtg_valuation)
        txtCalificationProduct = view.findViewById(R.id.tv_calification)
        imgProduct = view.findViewById(R.id.img_product)
        txtPriceCuotaProducto = view.findViewById(R.id.tv_priceCuotas)

        super.onViewCreated(view, savedInstanceState)
        /*arguments?.let{
            //Without SafeArgs
            val title = arguments?.getString("TITLE");
            val price = arguments?.getFloat("PRICE");
            val description = arguments?.getString("DESCRIPTION");
            val valuation = arguments?.getFloat("VALUATION");
            val calification = arguments?.getInt("CALIFICATION");
            val image = arguments?.getString("IMAGE");
            val quota = arguments?.getFloat("QUOTA")

            txtNameProduct.text = title
            txtPriceProduct.text = price.toString()
            txtDescriptionProducto.text = description
            rtgValuationProduct.rating = valuation!!
            txtCalificationProduct.text = calification.toString()
            Picasso.get().load(image).into(imgProduct)
            txtPriceCuotaProducto.text = quota.toString()
        }*/

        //With SafeArgs
        val title = args.title
        val price = args.price
        val description = args.description
        val valuation = args.valuation
        val calification = args.calification
        val image = args.image
        val quota = args.quota

        txtNameProduct.text = title
        txtPriceProduct.text = price.toString()
        txtDescriptionProducto.text = description
        rtgValuationProduct.rating = valuation!!
        txtCalificationProduct.text = calification.toString()
        Picasso.get().load(image).into(imgProduct)
        txtPriceCuotaProducto.text = "%.2f".format(quota)

    }
}