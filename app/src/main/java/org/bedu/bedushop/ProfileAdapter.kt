package org.bedu.bedushop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProfileAdapter(private val listProfile:List<ItemsProfile>): RecyclerView.Adapter<ProfileAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_option_profile,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listProfile[position]
        return holder.bind(item)
    }

    override fun getItemCount(): Int {
        return listProfile.size
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private var title = view.findViewById<TextView>(R.id.optionTitle)
        private var image = view.findViewById<ImageView>(R.id.image1)

        fun bind(item: ItemsProfile){
            title.setText(item.title)
            image.setImageResource(item.image)
        }
    }

}