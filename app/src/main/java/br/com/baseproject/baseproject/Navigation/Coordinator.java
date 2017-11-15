package br.com.baseproject.baseproject.Navigation;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;
import java.util.List;

import br.com.baseproject.baseproject.Activities.SearchResultsActivity;
import br.com.baseproject.baseproject.Models.PlaceModel;

/**
 * Created by teruya on 14/11/2017.
 */

public class Coordinator {

    public static void goToSearchResults(Activity activity, String address, ArrayList<String> filters, float minPrice, float maxPrice){
        Intent intent = new Intent(activity, SearchResultsActivity.class);
        intent.putExtra("address",address);
        intent.putStringArrayListExtra("filters",filters);
        intent.putExtra("minPrice",minPrice);
        intent.putExtra("maxPrice",maxPrice);
        activity.startActivity(intent);
    }
}
