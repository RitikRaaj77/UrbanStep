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

class ColorAdapter(val items:MutableList<String>)
    : RecyclerView.Adapter<ColorAdapter.ViewHolder>(){

    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val binding = ViewholderColorBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(holder.itemView.context)
            .load(items[position])
            .into(holder.binding.pic)
        holder.binding.root.setOnClickListener{
            lastSelectedPosition = selectedPosition
            selectedPosition = position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

        if(selectedPosition==position){
            holder.binding.colorLayout.setBackgroundResource(R.drawable.green2_bg)
        }else{
            holder.binding.colorLayout.setBackgroundResource(R.drawable.green_bg)
        }
    }
    class ViewHolder(val binding: ViewholderColorBinding) :
        RecyclerView.ViewHolder(binding.root){

    }
}