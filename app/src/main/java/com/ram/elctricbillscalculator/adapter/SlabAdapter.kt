package com.ram.electricbillcalculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ram.elctricbillscalculator.databinding.ItemSlabBinding
import com.ram.electricbillcalculator.model.SlabModel


class SlabAdapter :
    ListAdapter<SlabModel, SlabAdapter.ItemViewholder>(SlabDiffUtilDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            ItemSlabBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewholder(val binding: ItemSlabBinding) : RecyclerView.ViewHolder(binding.root) {
        /*setting required data in views to show SlabModel details*/
        fun bind(item: SlabModel) = with(itemView) {
            with(item) {
                binding.tvItemCon.text =
                    item.startUnit.toString() + " to " + item.endUnit.toString()
                binding.tvItemRate.text = item.rate.toString()
            }
        }
    }
}

/*To Update the recycler view as per the value*/
class SlabDiffUtilDiffCallback : DiffUtil.ItemCallback<SlabModel>() {
    override fun areItemsTheSame(oldItem: SlabModel, newItem: SlabModel): Boolean {
        return oldItem.startUnit == newItem.startUnit &&
                oldItem.endUnit == newItem.endUnit &&
                oldItem.rate == newItem.rate
    }

    override fun areContentsTheSame(oldItem: SlabModel, newItem: SlabModel): Boolean {
        return oldItem.startUnit == newItem.startUnit &&
                oldItem.endUnit == newItem.endUnit &&
                oldItem.rate == newItem.rate
    }
}