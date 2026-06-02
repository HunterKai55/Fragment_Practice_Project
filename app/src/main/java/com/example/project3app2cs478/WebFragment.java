package com.example.project3app2cs478;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

//Fragment that displays a webview
public class WebFragment extends Fragment {

    //holds the WebView UI component
    WebView webView;
    //creates fragment layout
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflates the layout
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        webView = view.findViewById(R.id.webView);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //gets the same viewmodel used in the list fragment
        AttractionViewModel viewModel = new ViewModelProvider(requireActivity()).get(AttractionViewModel.class);

        // Restore previously loaded URL if any
        if (viewModel.currentUrl != null) {
            webView.loadUrl(viewModel.currentUrl);
        }
    }

    public void loadUrl(String url) {
        webView.loadUrl(url);
    }
}