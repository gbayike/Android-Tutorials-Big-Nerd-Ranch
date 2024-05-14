package com.olugbayike.android.photogallery

import android.view.LayoutInflater
import android.view.ViewGroup
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
    private val galleryItems: List<GalleryItem>
): RecyclerView.Adapter<PhotoViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGalleryBinding.inflate(inflater, parent, false)
        return PhotoViewHolder(binding)
    }

    override fun getItemCount() = galleryItems.size

    override fun onBindViewHolder(
        holder: PhotoViewHolder,
        position: Int
    ) {
        val item = galleryItems[position]
        holder.bind(item)
    }

}