package com.example.project3app2cs478;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class RestaurantsListFragment extends Fragment {
    private RestaurantsViewModel viewModel;
    String[] restaurants = {
            "Gyukaku Streeterville",
            "Portillos",
            "Girl & The Goat",
            "Lou Mitchells",
            "Wildberry Pancakes and Cafe"
    };

    String[] urls = {
            "https://www.gyu-kaku.com/chicago/",
            "https://www.portillos.com/order/",
            "https://www.girlandthegoat.com/",
            "https://www.loumitchells.com/",
            "https://wildberrycafe.com/"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //loads xml file into view object
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        ListView listView = view.findViewById(R.id.listViewFrag);

        //adapter converts each item on the attractions list into a list row
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,//allows highlighting when selected
                restaurants
        );

        listView.setAdapter(adapter);

        //Keeps item highlighted
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Get shared ViewModel tied to Activity
        viewModel = new ViewModelProvider(requireActivity()).get(RestaurantsViewModel.class);

        // Restore previously selected item
        if (viewModel.selectedPosition != -1) {
            listView.setItemChecked(viewModel.selectedPosition, true);
        }

        //Handle clicks
        listView.setOnItemClickListener((parent, view1, position, id) -> {

            String selectedUrl = urls[position];

            // Update ViewModel
            viewModel.selectedPosition = position;
            viewModel.currentUrl = selectedUrl;

            //Call Activity method
            ((RestaurantsActivity) getActivity()).showWebFragment(selectedUrl, position);
        });

        return view;
    }
}

