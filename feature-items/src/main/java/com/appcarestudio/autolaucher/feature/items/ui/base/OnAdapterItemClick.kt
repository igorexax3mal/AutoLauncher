package com.appcarestudio.autolaucher.feature.items.ui.base

interface OnAdapterItemClick<T> {
    fun onItemClick(item: T)
}

interface OnCustomBtnClick<T> {
    fun onBtnClick(btnId : Int, item: T?,  position : Int)
}