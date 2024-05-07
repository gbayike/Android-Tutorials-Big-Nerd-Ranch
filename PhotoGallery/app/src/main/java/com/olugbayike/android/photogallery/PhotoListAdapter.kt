package com.olugbayike.android.photogallery

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.olugbayike.android.photogallery.api.GalleryItem
import com.olugbayike.android.photogallery.databinding.ListItemGalleryBinding

class PhotoViewHolder (
    private val binding: ListItemGalleryBinding
) :RecyclerView.ViewHolder(binding.root){
    fun bind(galleryItem: GalleryItem){
        binding.itemImageView.load(galleryItem.url){
            placeholder(R.drawable.image_icon)

        }
    }
}
class PhotoListAdapter (
//    private val galleryItems: List<GalleryItem>
//){
    diffCallback: DiffUtil.ItemCallback<GalleryItem>
): PagingDataAdapter<GalleryItem ,PhotoViewHolder>(diffCallback){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGalleryBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

//    override fun getItemCount() = galleryItems.size

    override fun onBindViewHolder(
        holder: PhotoViewHolder,
        position: Int
    ) {
//        val item = galleryItems[position]
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

}

object GalleryComparator : DiffUtil.ItemCallback<GalleryItem>() {
    override fun areItemsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GalleryItem, newItem: GalleryItem): Boolean {
        return oldItem == newItem
    }
}