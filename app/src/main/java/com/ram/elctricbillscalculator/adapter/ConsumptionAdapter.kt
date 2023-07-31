package com.ram.elctricbillscalculator.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ram.elctricbillscalculator.databinding.ItemSlabBinding
import com.ram.electricbillcalculator.model.ConsumptionModel


class ConsumptionAdapter :
    ListAdapter<ConsumptionModel, ConsumptionAdapter.ItemViewholder>(ConsumptionDiffUtilDiffCallback()) {

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
        fun bind(item: ConsumptionModel) = with(itemView) {
            with(item) {
                binding.tvItemCon.text =
                    item.consumptionUnit.toString()
                binding.tvItemRate.text = item.rate.toString()
            }
        }
    }
}

/*To Update the recycler view as per the value*/
class ConsumptionDiffUtilDiffCallback : DiffUtil.ItemCallback<ConsumptionModel>() {
    override fun areItemsTheSame(oldItem: ConsumptionModel, newItem: ConsumptionModel): Boolean {
        return oldItem.consumptionUnit == newItem.consumptionUnit &&
                oldItem.rate == newItem.rate
    }

    override fun areContentsTheSame(oldItem: ConsumptionModel, newItem: ConsumptionModel): Boolean {
        return oldItem.consumptionUnit == newItem.consumptionUnit &&
                oldItem.rate == newItem.rate
    }
}