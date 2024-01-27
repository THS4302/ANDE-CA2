package com.example.tripsavvy_studio_2b03_2;
//doesnt work now leave as a spare for later


// NavigationUtil.java

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;

public class NavUtil {

    public static void handleNavigationItemSelected(Activity activity, MenuItem item, int userId) {
        switch (item.getItemId()) {
            case R.id.action_home:
                Intent intenth = new Intent(activity, Home.class);
                intenth.putExtra("userId", userId);
                activity.startActivity(intenth);
                break;

            case R.id.action_favourites:
                Intent intentf = new Intent(activity, Favorites.class);
                intentf.putExtra("userId", userId);
                activity.startActivity(intentf);
                break;

            case R.id.action_profile:
                Intent intentp = new Intent(activity, Profile.class);
                intentp.putExtra("userId", userId);
                activity.startActivity(intentp);

                break;

            case R.id.action_store:
                Intent intents = new Intent(activity, Store.class);
                intents.putExtra("userId", userId);
                activity.startActivity(intents);
                break;
        }
    }
}
