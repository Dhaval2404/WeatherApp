package com.github.dhaval2404.weatherapp.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Recycler View Which auto manage ProgressView and EmptyView
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 16 May 2021
 */
class AppRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var emptyView: View? = null
    private var progressView: View? = null
    private var isLoading = false

    init {
        overScrollMode = View.OVER_SCROLL_NEVER
    }

    fun isLoading(): Boolean {
        return isLoading
    }

    fun setLoading(loading: Boolean) {
        isLoading = loading
        checkIfEmpty()
    }

    fun setEmptyView(emptyView: View?) {
        this.emptyView = emptyView
        checkIfEmpty()
    }

    fun setProgressView(progressView: View?) {
        this.progressView = progressView
        checkIfEmpty()
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = getAdapter()
        oldAdapter?.unregisterAdapterDataObserver(observer)

        super.setAdapter(adapter)
        adapter?.registerAdapterDataObserver(observer)

        checkIfEmpty()
    }

    private fun checkIfEmpty() {
        if (adapter != null && adapter?.itemCount?.compareTo(0) != 0) {
            isLoading = false
            emptyView?.visibility = GONE
            progressView?.visibility = GONE
        } else {
            if (isLoading) {
                emptyView?.visibility = GONE
                progressView?.visibility = VISIBLE
            } else {
                emptyView?.visibility = VISIBLE
                progressView?.visibility = GONE
            }
        }
    }

    private val observer = object : AdapterDataObserver() {
        @Override
        override fun onChanged() {
            super.onChanged()
            checkIfEmpty()
        }
    }
}
