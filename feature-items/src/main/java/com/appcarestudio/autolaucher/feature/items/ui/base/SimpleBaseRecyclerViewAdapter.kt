package com.appcarestudio.autolaucher.feature.items.ui.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

abstract class SimpleBaseRecyclerViewAdapter<T, H : RecyclerView.ViewHolder?>(context: Context?) :
    BaseRecyclerViewAdapter<T, H>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): H & Any {
        return getViewHolder(parent, layoutRes, viewType)
    }

    override fun onBindViewHolder(holder: H & Any, position: Int) {
        onBind(holder, position)
    }

    fun getInflatedView(@LayoutRes layoutRes: Int): View {
        return inflater!!.inflate(layoutRes, null, false)
    }

    fun getInflatedView(@LayoutRes layoutRes: Int, parent: ViewGroup?, attach: Boolean): View {
        return inflater!!.inflate(layoutRes, parent, attach)
    }

    @get:LayoutRes
    abstract val layoutRes: Int

    abstract fun getViewHolder(parent: ViewGroup?, @LayoutRes layoutRes: Int, viewType: Int): H & Any
    abstract fun onBind(holder: H, position: Int)
    var parentFragment: BaseFragment<*, *>? = null
}
