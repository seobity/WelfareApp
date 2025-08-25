package com.example.finalproject.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.finalproject.R

class OneFragment : Fragment() {

    private lateinit var webView: WebView
    private lateinit var searchEditText: EditText
    private lateinit var searchButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_one, container, false)

        webView = view.findViewById(R.id.youtubeWebView)
        searchEditText = view.findViewById(R.id.searchEditText)
        searchButton = view.findViewById(R.id.searchButton)

        webView.settings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()

        searchButton.setOnClickListener {
            val keyword = searchEditText.text.toString()
            if (keyword.isNotBlank()) {
                val query = "https://www.youtube.com/results?search_query=${keyword}"
                webView.loadUrl(query)
            }
        }

        return view
    }
}