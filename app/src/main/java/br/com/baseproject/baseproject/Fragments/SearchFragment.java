package br.com.baseproject.baseproject.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyvet.rangebar.RangeBar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import br.com.baseproject.baseproject.Adapters.CheckboxListAdapter;
import br.com.baseproject.baseproject.Navigation.Coordinator;
import br.com.baseproject.baseproject.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class SearchFragment extends Fragment {
    private TextInputEditText referenceAddressEditText;
    private RangeBar rangeBar;
    private FloatingActionButton floatingActionButton;
    private RecyclerView optionsRecyclerView;
    CheckboxListAdapter adapter;
    public Boolean[] options;
    ArrayList<String> filters;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    private void getLayoutIds() {
        referenceAddressEditText = getActivity().findViewById(R.id.fragment_search_field_address);
        rangeBar = getActivity().findViewById(R.id.search_price_rangebar);
        floatingActionButton = getActivity().findViewById(R.id.search_event_floating_action_button);
        optionsRecyclerView = getActivity().findViewById(R.id.search_filters_list);

    }

    private void setButtonActions() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = referenceAddressEditText.getText().toString();
                int tickStart = (int) rangeBar.getTickStart();
                int minPrice = rangeBar.getLeftIndex() + tickStart;
                int maxPrice = rangeBar.getRightIndex() + tickStart;
                ArrayList<String> selectedFilters = new ArrayList<>();
                for( int i = 0; i < filters.size();i++){
                    if(options[i]){
                        selectedFilters.add(filters.get(i));
                    }
                }
                Coordinator.goToSearchResults(getActivity(),address,selectedFilters,minPrice,maxPrice);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getLayoutIds();
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        referenceAddressEditText = getActivity().findViewById(R.id.fragment_search_field_address);
        rangeBar = getActivity().findViewById(R.id.search_price_rangebar);
        floatingActionButton = getActivity().findViewById(R.id.search_event_floating_action_button);
        optionsRecyclerView = getActivity().findViewById(R.id.search_filters_list);
        filters = new ArrayList<>();

        filters.add("Já existem moradores");
        filters.add("Ainda não há moradores no momento");
        filters.add("Posso morar sozinho");
        filters.add("Os moradores tem idade parecida com a minha");
        filters.add("Os moradores tem perfil similar ao meu");
        filters.add("O proprietário é membro ativo do aplicativo");

        options= new Boolean[filters.size()];
        Arrays.fill(options, Boolean.FALSE);

        adapter = new CheckboxListAdapter(filters,getActivity());
        adapter.searchFragment = this;
        optionsRecyclerView.setAdapter(adapter);
        optionsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setButtonActions();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
