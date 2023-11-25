package com.example.pokewatchlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

//these imports stink!!!!

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.ParsedRequestListener;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;


public class APIFragment extends Fragment {
    ListView listView;
    TextView name;
    TextView natnum;
    ImageView picture;
    TextView weight;
    TextView height;
    TextView xp;
    TextView move;
    TextView ability;


    //viewmodel
    private com.example.pokewatchlist.viewModel viewModel;


    Observer<Pokemon> observer = new Observer<Pokemon>() {
        @Override
        public void onChanged(Pokemon pokemon) {
            //fill out after making viewmodel
            pokemon = viewModel.getPoke().getValue();
            name.setText(pokemon.getName());
            natnum.setText(pokemon.getNum());
            weight.setText(pokemon.getWeight());
            height.setText(pokemon.getHeight());
            xp.setText(pokemon.getXp());
            move.setText(pokemon.getMove());
            ability.setText(pokemon.getAbility());

            formatNum(pokemon.getNum());
        }
    };

    public APIFragment() {
        // Required empty public constructor
    }

    public void makeRequest(String name){
        ANRequest req =AndroidNetworking.get("https://pokeapi.co/api/v2/pokemon/{poke}").addPathParameter("poke",name).setPriority(Priority.LOW).build();
        req.getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String name = response.getString("name");
                    String natnum = response.getString("id");
                    String height = response.getString("height");
                    String weight = response.getString("weight");
                    String xp = response.getString("base_experience");
                    String move = response.getJSONArray("moves").getJSONObject(0).getJSONObject("move").getString("name");
                    String ability = response.getJSONArray("abilities").getJSONObject(0).getJSONObject("ability").getString("name");

                    Pokemon poke = new Pokemon(natnum, name, height, weight, xp, move, ability);
                    viewModel.addPoke(poke);

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(getActivity(),"Error on getting data ", Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(com.example.pokewatchlist.viewModel.class);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_a_p_i, container, false);
        name = v.findViewById(R.id.pnameTV);
        natnum = v.findViewById(R.id.numberDisplay);
        picture = v.findViewById(R.id.pokeImage);
        weight = v.findViewById(R.id.weightDisplay);
        height = v.findViewById(R.id.heightDisplay);
        xp = v.findViewById(R.id.expDisplay);
        move = v.findViewById(R.id.moveDisplay);
        ability = v.findViewById(R.id.abilityDisplay);
        return v;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        viewModel.getPoke().observe(getViewLifecycleOwner(), observer);
    }

    public void formatNum(String num){
        //pad < 3 digit number to fit url
        if(num.length() < 3){
            num = String.format("%03d", Integer.parseInt(num));
        }
        getImage(num);
    }

    public void getImage(String num){
        //higher quality sprite
        String imageUrl = "https://raw.githubusercontent.com/HybridShivam/Pokemon/master/assets/images/"
                + num + ".png";

        Picasso.get().load(imageUrl).into(picture);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel =  new ViewModelProvider(getActivity()).get(viewModel.class);
        viewModel.getPoke().observe(getViewLifecycleOwner(), new Observer<Pokemon>() {
            @Override
            public void onChanged(Pokemon pokemon) {
                makeRequest(String.valueOf(pokemon));
            }

        });
    }
}