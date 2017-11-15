package br.com.baseproject.baseproject.Fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appyvet.rangebar.RangeBar;

import br.com.baseproject.baseproject.Navigation.Coordinator;
import br.com.baseproject.baseproject.R;

/**
 * A simple {@link Fragment} subclass.
 */

public class SearchFragment extends Fragment {
    private TextInputEditText referenceAddressEditText;
    private RangeBar rangeBar;
    private FloatingActionButton floatingActionButton;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutIds();

    }

    private void getLayoutIds() {
        referenceAddressEditText = getActivity().findViewById(R.id.fragment_search_field_address);
        rangeBar = getActivity().findViewById(R.id.search_price_rangebar);
        floatingActionButton = getActivity().findViewById(R.id.search_event_floating_action_button);
    }

    private void setButtonActions() {

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = referenceAddressEditText.getText().toString();
                int minPrice = rangeBar.getLeftIndex();
                int maxPrice = rangeBar.getRightIndex();
                Coordinator.goToSearchResults(getActivity(),address,null,minPrice,maxPrice);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
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
