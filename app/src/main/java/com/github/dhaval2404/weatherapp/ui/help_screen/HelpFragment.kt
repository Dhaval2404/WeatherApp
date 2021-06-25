package com.github.dhaval2404.weatherapp.ui.help_screen

import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import com.github.dhaval2404.weatherapp.R
import com.github.dhaval2404.weatherapp.constant.AppConstant
import com.github.dhaval2404.weatherapp.databinding.FragmentHelpBinding
import com.github.dhaval2404.weatherapp.ui.base.BaseFragment

/**
 * Show app info web page
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 15 May 2021
 */
class HelpFragment : BaseFragment<FragmentHelpBinding>() {

    override fun getLayout() = R.layout.fragment_help

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Init WebView and load web page
        setWebView()
    }

    private fun setWebView() {
        val webView = mViewDataBinding.webview

        // Disable Java Script
        webView.settings.javaScriptEnabled = false

        // disable page forwarding
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return true
            }
        }

        // Force links and redirects to open in the WebView instead of in a browser
        webView.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                if (isAdded && newProgress >= 100) {
                    mViewDataBinding.progressBar.isVisible = false
                }
            }
        }

        // load url
        webView.loadUrl(AppConstant.HELP_PAGE_URL)
    }
}
