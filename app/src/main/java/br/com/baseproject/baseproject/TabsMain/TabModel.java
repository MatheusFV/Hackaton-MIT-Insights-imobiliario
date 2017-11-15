package br.com.baseproject.baseproject.TabsMain;

import android.app.Fragment;
import android.graphics.drawable.Drawable;

/**
 * Created by mvenosa on 30/10/17.
 */

public class TabModel {
    private Drawable icon;
    private String backgroundColor;
    private String name;
    private Fragment fragment;

    public TabModel(Drawable icon, String backgroundColor, String name, Fragment fragment) {
        this.icon = icon;
        this.backgroundColor = backgroundColor;
        this.name = name;
        this.fragment = fragment;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }
}
