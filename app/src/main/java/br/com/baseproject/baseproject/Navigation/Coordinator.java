package br.com.baseproject.baseproject.Navigation;

import android.app.Activity;
import android.content.Intent;

import java.util.ArrayList;

import br.com.baseproject.baseproject.Activities.ChatActivity;
import br.com.baseproject.baseproject.Activities.SearchResultsActivity;

/**
 * Created by teruya on 14/11/2017.
 */

public class Coordinator {

    public static void goToSearchResults(Activity activity, String address, ArrayList<String> filters, int minPrice, int maxPrice){
        Intent intent = new Intent(activity, SearchResultsActivity.class);
        intent.putExtra("address",address);
        intent.putStringArrayListExtra("filters",filters);
        intent.putExtra("minPrice",minPrice);
        intent.putExtra("maxPrice",maxPrice);
        activity.startActivity(intent);
    }

    public static void goToChat(Activity activity, String placeId, String address, String imageUrl) {
        Intent intent = new Intent(activity, ChatActivity.class);
        intent.putExtra("placeId",placeId);
        intent.putExtra("address",address);
        intent.putExtra("imageUrl",imageUrl);
        activity.startActivity(intent);
    }
}
