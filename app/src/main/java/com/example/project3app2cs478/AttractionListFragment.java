package com.example.project3app2cs478;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class AttractionListFragment extends Fragment {
    private AttractionViewModel viewModel;
    String[] attractions = {
            "Navy Pier",
            "Art Institute",
            "Shedd Aquarium",
            "Museum of Science",
            "Lincoln Park Zoo"
    };

    String[] urls = {
            "https://navypier.org/",
            "https://www.artic.edu/",
            "https://www.sheddaquarium.org/",
            "https://www.griffinmsi.org/",
            "https://www.lpzoo.org/"
    };

    //creates and returns UI for this fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //loads xml file into view object
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ListView listView = view.findViewById(R.id.listViewFrag);

        //adapter converts each item on the attractions list into a list row
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity(),
                android.R.layout.simple_list_item_activated_1,//allows highlighting when selected
                attractions
        );

        listView.setAdapter(adapter);

        //allows one item to be selected at a time
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        // Get shared ViewModel tied to Activity
        viewModel = new ViewModelProvider(requireActivity()).get(AttractionViewModel.class);

        // makes sure the highlighted item survives configuration changes
        if (viewModel.selectedPosition != -1) {
            listView.setItemChecked(viewModel.selectedPosition, true);
        }

        //Handle clicks
        listView.setOnItemClickListener((parent, view1, position, id) -> {

            String selectedUrl = urls[position];

            // save state in viewmodel
            viewModel.selectedPosition = position;
            viewModel.currentUrl = selectedUrl;

            //Call Activity method to show WebFragment
            ((AttractionActivity) getActivity()).showWebFragment(selectedUrl, position);
        });

        return view;
    }
}