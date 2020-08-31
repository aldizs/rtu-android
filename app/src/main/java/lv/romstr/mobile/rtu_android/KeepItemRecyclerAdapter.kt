package lv.romstr.mobile.rtu_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_keep.view.*
import kotlinx.android.synthetic.main.item_keep.view.keepClose
import kotlinx.android.synthetic.main.item_keep_image.view.*

class KeepItemRecyclerAdapter(private val items: MutableList<KeepItem>) :
    RecyclerView.Adapter<KeepItemRecyclerAdapter.KeepViewHolder>() {

    abstract class KeepViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(position: Int)
    }

    inner class TextViewHolder(view: View) : KeepViewHolder(view) {
        override fun bind(position: Int) {
            val item = items[position] as KeepItemText
            itemView.keepTitle.text = item.title
            itemView.keepText.text = item.text
        }
    }

    inner class ImageViewHolder(view: View) : KeepViewHolder(view) {
        override fun bind(position: Int) {
            val item = items[position] as KeepItemImage
            Glide.with(itemView)
                .load(item.uri)
                .into(itemView.keepImage)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeepViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == IMAGE_KEEP) {
            ImageViewHolder(inflater.inflate(R.layout.item_keep_image, parent, false))
        } else {
            TextViewHolder(inflater.inflate(R.layout.item_keep, parent, false))
        }
    }

    override fun getItemViewType(position: Int) =
        if (items[position] is KeepItemText) TEXT_KEEP else IMAGE_KEEP

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: KeepViewHolder, position: Int) {
        holder.bind(position)
        val item = items[position]

        holder.itemView.keepClose.setOnClickListener {
            val currentPosition = items.indexOf(item)
            items.removeAt(currentPosition)
            notifyItemRemoved(currentPosition)
        }
    }

    companion object {
        private const val TEXT_KEEP = 0
        private const val IMAGE_KEEP = 1
    }

}