package com.example.project3app2cs478;

import androidx.lifecycle.ViewModel;

public class AttractionViewModel extends ViewModel {
    // Track which list item is selected (-1 means none)
    public int selectedPosition = -1;

    // Track the current URL loaded in WebView
    public String currentUrl = null;
}