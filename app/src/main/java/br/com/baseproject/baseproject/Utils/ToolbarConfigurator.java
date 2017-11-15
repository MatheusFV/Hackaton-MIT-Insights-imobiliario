package br.com.baseproject.baseproject.Utils;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.baseproject.baseproject.R;

/**
 * Created by teruya on 15/11/2017.
 */

public class ToolbarConfigurator {
    /**
     * Set the parameters on a custom toolbar with a title, left and right icons.
     * @param toolbarLayout Custom toolbar layout
     * @param title Screen title
     * @param leftIconResId    Set the left icon if id != 0
     * @param rightIconResId   Set the right icon if id != 0
     */
    public static void configToolbar(View toolbarLayout, String title, @DrawableRes int leftIconResId, @DrawableRes int rightIconResId, final Activity activity){

        IncludedToolbar includedToolbar = new IncludedToolbar(activity);

        String titleCap = title;
        if (!title.isEmpty() || isStringFormattable(title)){
            titleCap = title.substring(0,1).toUpperCase() + title.substring(1);
            includedToolbar.toolbarTitle.setText(titleCap);
        }

        if(leftIconResId != 0) {
            includedToolbar.leftIcon.setVisibility(View.VISIBLE);
            includedToolbar.leftIcon.setImageResource(leftIconResId);
            includedToolbar.leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    activity.finish();
                }
            });
        } else {
            includedToolbar.leftIcon.setVisibility(View.GONE);
        }
        if(rightIconResId != 0) {
            includedToolbar.rightIcon.setVisibility(View.VISIBLE);
            includedToolbar.rightIcon.setImageResource(rightIconResId);
        } else {
            includedToolbar.rightIcon.setVisibility(View.GONE);
        }
    }

    static class IncludedToolbar {
        Activity activity;

        public IncludedToolbar(Activity activity){
            this.activity = activity;
            leftIcon = activity.findViewById(R.id.toolbar_left_icon);
            rightIcon = activity.findViewById(R.id.toolbar_right_icon);
            toolbarTitle = activity.findViewById(R.id.toolbar_title);
        }
        ImageView leftIcon;
        ImageView rightIcon;
        TextView toolbarTitle;
    }

    public static boolean isStringFormattable(String s) {
        // s = MFDSMFPDSM -> return true
        // s = mkdfmsklfmsdklfsm -> return true
        // s = Amdnsdaiojdsaiodas -> return false
        if (!s.isEmpty()) {
            boolean isLowerCase = Character.isLowerCase(s.charAt(0));
            for (int i = 1; i < s.length(); i++) {
                if (isLowerCase != Character.isLowerCase(s.charAt(i)) && !(s.charAt(i) == ' ')) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

}
