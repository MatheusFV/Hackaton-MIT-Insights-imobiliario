package br.com.baseproject.baseproject.TabsMain;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import br.com.baseproject.baseproject.Utils.Utils;

/**
 * Created by mvenosa on 30/10/17.
 */

public class TabsManager {

    //Required
    private Activity activity;
    private TabsListener listener;
    private int container;
    private ArrayList<TabModel> tabs;
    private FragmentManager fragmentManager;
    private LinearLayout tabsParentLayout;
    private String selectedTabColor;
    private String notSelectedTabColor;
    private int initialFragment;

    //Generated
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<ImageView> tabsImages = new ArrayList<>();

    public TabsManager(TabsManagerBuilder builder) {
        this.activity = builder.activity;
        this.listener = builder.listener;
        this.container = builder.container;
        this.tabs = builder.tabs;
        this.fragmentManager = builder.fragmentManager;
        this.tabsParentLayout = builder.tabsParentLayout;
        this.selectedTabColor = builder.selectedTabColor;
        this.notSelectedTabColor = builder.notSelectedTabColor;
        this.initialFragment = builder.initialFragment;
    }

    public void generateTabsLayout(){

        tabsParentLayout.setWeightSum(tabs.size());
        ImageView tabImage;
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        for (final TabModel tab: tabs){
            tabImage = new ImageView(activity);
            tabImage.setImageResource(Utils.getDrawableFromString(activity, tab.getIcon()));
            tabImage.setBackgroundColor(Color.parseColor(tab.getBackgroundColor()));
            tabImage.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1f));
            fragmentTransaction.add(this.container, tab.getFragment());
            fragments.add(tab.getFragment());
            tabImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectFragment(tab.getFragment(), (ImageView) view);
                }
            });
            tabImage.setPadding(25,25,25,25);
            tabsImages.add(tabImage);
            tabsParentLayout.addView(tabImage);

        }
        fragmentTransaction.commit();
        selectFragment(fragments.get(initialFragment), tabsImages.get(initialFragment));
    }

    public void selectFragment(Fragment fragmentSelected, ImageView imageSelected){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Fragment fragment: fragments){
            if (fragment.equals(fragmentSelected)){
                listener.onTabSelected(fragmentSelected);
                fragmentTransaction.show(fragment);
            }else{
                fragmentTransaction.hide(fragment);
            }
        }
        fragmentTransaction.commit();
        modifyTabLayoutBySelection(imageSelected);
    }

    private void modifyTabLayoutBySelection(ImageView imageSelected){
        for (ImageView image : tabsImages){
            if (image.equals(imageSelected)){
                image.setColorFilter(Color.parseColor(selectedTabColor));
            }else{
                image.setColorFilter(Color.parseColor(notSelectedTabColor));
            }
        }
    }

    // Builder
    public static class TabsManagerBuilder{
        private Activity activity;
        private TabsListener listener;
        private int container;
        private ArrayList<TabModel> tabs;
        private FragmentManager fragmentManager;
        private LinearLayout tabsParentLayout;
        private String selectedTabColor;
        private String notSelectedTabColor;
        private int initialFragment;

        public TabsManagerBuilder(Activity activity){
            this.activity = activity;
        }

        public TabsManagerBuilder setTabsListener(TabsListener listener){
            this.listener = listener;
            return this;
        }

        public TabsManagerBuilder setContainer(int container){
            this.container = container;
            return this;
        }

        public TabsManagerBuilder setTabs(ArrayList<TabModel> tabs){
            this.tabs = tabs;
            return this;
        }

        public TabsManagerBuilder setFragmentTransaction(FragmentManager fragmentManager){
            this.fragmentManager = fragmentManager;
            return this;
        }

        public TabsManagerBuilder setTabsParentLayout(LinearLayout tabsParentLayout){
            this.tabsParentLayout = tabsParentLayout;
            return this;
        }

        public TabsManagerBuilder setSelectedTabColor(String selectedTabColor){
            this.selectedTabColor = selectedTabColor;
            return this;
        }

        public TabsManagerBuilder setNotSelectedTabColor(String notSelectedTabColor){
            this.notSelectedTabColor = notSelectedTabColor;
            return this;
        }

        public TabsManagerBuilder setInitialFragment(int initialFragment){
            this.initialFragment = initialFragment;
            return this;
        }

        public TabsManager build(){
            return new TabsManager(this);
        }
    }
}

