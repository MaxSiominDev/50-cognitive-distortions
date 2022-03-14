package dev.maxsiomin.fiftycognitivedistortions.fragments.distortions

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.maxsiomin.fiftycognitivedistortions.databinding.ItemDistortionsBinding

class DistortionsRecyclerViewAdapter(
    private val values: List<String>,
    private val onItemClicked: (String) -> Unit,
) : RecyclerView.Adapter<DistortionsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemDistortionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.distortionName.text = values[position]
    }

    override fun getItemCount() = values.size

    inner class ViewHolder(binding: ItemDistortionsBinding) : RecyclerView.ViewHolder(binding.root) {

        val distortionName = binding.distortionName

        init {
            // If item clicked show qr code
            itemView.setOnClickListener {
                onItemClicked(distortionName.text.toString())
            }
        }
    }
}
