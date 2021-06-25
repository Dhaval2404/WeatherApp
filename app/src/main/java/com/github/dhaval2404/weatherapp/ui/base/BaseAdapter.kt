package com.github.dhaval2404.weatherapp.ui.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

/**
 * Generic Base Adapter
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 15 May 2021
 */
abstract class BaseAdapter<T, VDB : ViewDataBinding, VH : RecyclerView.ViewHolder>(
    val itemList: MutableList<T> = ArrayList()
) : RecyclerView.Adapter<VH>() {

    constructor() : this(ArrayList())

    @LayoutRes
    protected abstract fun getLayout(): Int

    override fun getItemCount() = itemList.size

    open fun getItem(position: Int) = itemList[position]

    @Suppress("UNCHECKED_CAST")
    protected open fun getViewHolder(binding: VDB): VH {
        return object : RecyclerView.ViewHolder(binding.root) {} as VH
    }

    protected open fun getViewHolder(binding: VDB, viewType: Int): VH {
        return getViewHolder(binding)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val viewDataBinding: VDB = DataBindingUtil.inflate(inflater, getLayout(), parent, false)
        return getViewHolder(viewDataBinding, viewType)
    }

    /**
     * Refresh adapter with new data
     */
    open fun refresh(list: List<T>) {
        itemList.clear()
        itemList.addAll(list)
        notifyDataSetChanged()
    }
}
