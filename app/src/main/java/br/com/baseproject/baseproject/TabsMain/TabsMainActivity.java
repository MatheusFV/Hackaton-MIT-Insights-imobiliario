package br.com.baseproject.baseproject.TabsMain;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class TabsMainActivity extends AppCompatActivity implements TabsListener {

    TabsManager tabsManager;

    public void initTabsMain(LinearLayout tabBar, int container, ArrayList<TabModel> tabs, String selectedTabColor, String notSelectedTabColor){
        tabsManager = new TabsManager.TabsManagerBuilder(this)
                .setTabsListener(this)
                .setTabsParentLayout(tabBar)
                .setContainer(container)
                .setFragmentTransaction(getFragmentManager())
                .setTabs(tabs)
                .setSelectedTabColor(selectedTabColor)
                .setNotSelectedTabColor(notSelectedTabColor)
                .setInitialFragment(3)
                .build();

        tabsManager.generateTabsLayout();
    }

    @Override
    public void onTabSelected(Fragment fragment) {

    }

    public static class TabsMainBuilder{

        private LinearLayout tabBar;
        private String selectedColor;
        private int container;
        private String notSelectedColor;
        private ArrayList<TabModel> tabs = new ArrayList<>();

        public TabsMainBuilder(){}

        public TabsMainBuilder setTabBar(LinearLayout tabBar){
            this.tabBar = tabBar;
            return this;
        }

        public TabsMainBuilder setSelectedColor(String selectedColor){
            this.selectedColor = selectedColor;
            return this;
        }

        public TabsMainBuilder setContainer(int container){
            this.container = container;
            return this;
        }

        public TabsMainBuilder setNotSelectedColor(String notSelectedColor){
            this.notSelectedColor = notSelectedColor;
            return this;
        }

        public TabsMainBuilder addTab(Drawable icon, String backgroundColor, String name, Fragment fragment){
            this.tabs.add(new TabModel(icon, backgroundColor, name, fragment));
            return this;
        }

        public void init(TabsMainActivity tabsMain){
            tabsMain.initTabsMain(tabBar, container, tabs, selectedColor, notSelectedColor);
        }
    }

}