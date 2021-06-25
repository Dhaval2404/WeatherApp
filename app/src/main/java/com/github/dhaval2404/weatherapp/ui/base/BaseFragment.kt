package com.github.dhaval2404.weatherapp.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

/**
 * Parent Activity for all the app fragments
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 15 May 2021
 */
abstract class BaseFragment<VDB : ViewDataBinding> : Fragment() {

    protected lateinit var mViewDataBinding: VDB

    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun getLayout(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayout(), container, false)
        mViewDataBinding.lifecycleOwner = viewLifecycleOwner
        return mViewDataBinding.root
    }
}
