package br.com.baseproject.baseproject.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.text.Normalizer;

/**
 * Created by AppSimples on 19/04/17.
 */

public class Utils {

    // Esconde o teclado
    public static void hideKeyboard(View view, Activity activity) {
        if (view != null) {
            InputMethodManager in = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // Diferentes pinturas da view para diferentes estados
    /**
     Não usar em Buttons, usar em TextViews, por exemplo.
     **/
    public static StateListDrawable makeSelector(int color) {
        StateListDrawable res = new StateListDrawable();
        ColorDrawable pressedColor = new ColorDrawable(color);
        pressedColor.setAlpha(50);
        ColorDrawable c = new ColorDrawable(color);
        res.addState(new int[]{android.R.attr.state_pressed}, pressedColor);
        res.addState(new int[]{}, new ColorDrawable(color));
        return res;
    }

    public static void setStatusBarColor(Activity activity, @ColorRes int colorId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            // clear FLAG_TRANSLUCENT_STATUS flag:
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //get the color from the int then finally change the color
            window.setStatusBarColor(ContextCompat.getColor(activity, colorId));
        }
    }

    // Normaliza as strings de pesquisa para minuscula, sem acentos ou diacriticos
    public static String getSearchString(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "").toLowerCase();
    }

    public static boolean isAppInstalled(String packageName, Context appContext) {
        PackageManager pm = appContext.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }
        return false;
    }

    public static int getDrawableFromString(Context c, String ImageName) {
        return ImageName != null ? c.getResources().getIdentifier(ImageName, "drawable", c.getPackageName()) : 0;
    }
}
