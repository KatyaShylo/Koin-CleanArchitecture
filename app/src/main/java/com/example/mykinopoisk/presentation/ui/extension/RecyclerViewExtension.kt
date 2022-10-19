package com.example.mykinopoisk.presentation.ui.extension

import android.graphics.Rect
import android.view.View
import androidx.annotation.DimenRes
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.addHorizontalSpaceDecoration(
    @DimenRes spaseRes: Int
) {
    val space = context.resources.getDimensionPixelSize(spaseRes)
    addItemDecoration(object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            if (position != 0 && position != parent.adapter?.itemCount) {
                outRect.top = space
            }
        }
    })
}