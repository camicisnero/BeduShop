package org.bedu.bedushop.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.bedu.bedushop.classes.ItemsProfile
import org.bedu.bedushop.R

class ProfileAdapter(private val listProfile:List<ItemsProfile>,
                     private val clicklistener: (Int) -> Unit): RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {

    class ViewHolder(view: View, clickAtPosition: (Int)->Unit): RecyclerView.ViewHolder(view){
        private var title = view.findViewById<TextView>(R.id.optionTitle)
        private var image = view.findViewById<ImageView>(R.id.image1)

        init {
            itemView.setOnClickListener {
                clickAtPosition(adapterPosition)
            }
        }

        fun bind(item: ItemsProfile){
            title.setText(item.title)
            image.setImageResource(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_option_profile,parent,false),
            ){
            clicklistener(listProfile[it].title)
        }

        return view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listProfile[position]
        return holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listProfile.size
    }

}