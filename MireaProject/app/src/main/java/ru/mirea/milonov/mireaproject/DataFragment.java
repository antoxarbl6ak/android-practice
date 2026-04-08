package ru.mirea.milonov.mireaproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class DataFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ListView themesList;

    public DataFragment() {
        // Required empty public constructor
    }

    public static DataFragment newInstance(String param1, String param2) {
        DataFragment fragment = new DataFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data, container, false);

        themesList = view.findViewById(R.id.themesListView);
        String[] themes = {
                "Activity и Fragment",
                "Navigation Drawer",
                "Web View",
                "Intents"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                themes
        );

        themesList.setAdapter(adapter);
        themesList.setOnItemClickListener((parent, view1, position, id) -> {
            String selectedTheme = themes[position];
            Toast.makeText(requireContext(), "Выбрана тема: " + selectedTheme, Toast.LENGTH_SHORT).show();
        });
        return view;
    }
}