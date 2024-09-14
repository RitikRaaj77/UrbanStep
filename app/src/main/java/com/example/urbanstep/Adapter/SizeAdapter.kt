package com.example.urbanstep.Adapter

import android.content.Context
import android.content.res.ColorStateList
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputBinding
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.urbanstep.Model.BrandModel
import com.example.urbanstep.R
import com.example.urbanstep.databinding.ViewholderBrandBinding
import com.example.urbanstep.databinding.ViewholderColorBinding
import com.example.urbanstep.databinding.ViewholderSizeBinding

class SizeAdapter(val items:MutableList<String>)
    : RecyclerView.Adapter<SizeAdapter.ViewHolder>(){

    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderSizeBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.sizeText.text = items[position]
        holder.binding.root.setOnClickListener{
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

        if(selectedPosition==position){
            holder.binding.sizelayout.setBackgroundResource(R.drawable.green2_bg)
            holder.binding.sizeText.setTextColor(context.getColor(R.color.shade1))
        }else{
            holder.binding.sizelayout.setBackgroundResource(R.drawable.green_bg)
            holder.binding.sizeText.setTextColor(context.getColor(R.color.shade5))
        }
    }
    class ViewHolder(val binding: ViewholderSizeBinding) :
        RecyclerView.ViewHolder(binding.root){

    }
}