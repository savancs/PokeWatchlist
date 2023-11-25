package com.example.pokewatchlist;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class WatchlistFragment extends Fragment {

    ListView listView;
    Button submit;
    EditText search;
    String name;
    APIFragment apiFragment;

    Cursor mCursor;

    //viewModel
    private com.example.pokewatchlist.viewModel viewModel;

    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Pokemon pSelected  = (Pokemon) listView.getItemAtPosition(position);
            viewModel.setcurrentPoke(pSelected);
        }
    };

    public WatchlistFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    View.OnClickListener submitClicked = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            name = search.getText().toString();

            if(name.matches("[a-zA-Z0-9]*") || Integer.valueOf(name) < 1010 || Integer.valueOf(name) >= 0){
                apiFragment.makeRequest(name);
                Toast.makeText(getActivity(), "Valid pokemon name or number", Toast.LENGTH_LONG).show();
            } else{
                Toast.makeText(getActivity(),"Not a valid pokemon name or number", Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_watchlist, container, false);
        listView = v.findViewById(R.id.watchlistLV);
        listView.setOnItemClickListener(listener);
        search = v.findViewById(R.id.searchET);
        submit = v.findViewById(R.id.submitButton);
        submit.setOnClickListener(submitClicked);
        viewModel = new ViewModelProvider(this).get(com.example.pokewatchlist.viewModel.class);
        return v;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel =  new ViewModelProvider(getActivity()).get(viewModel.class);
        viewModel.getPoke().observe(getViewLifecycleOwner(), new Observer<Pokemon>() {
            @Override
            public void onChanged(Pokemon pokemon) {
                ArrayAdapter<Pokemon> adapter = new ArrayAdapter<Pokemon>(getActivity(), android.R.layout.simple_list_item_1, viewModel.getPokes().getValue());
                listView.setAdapter(adapter);
            }

        });
    }
}