package br.com.baseproject.baseproject.Activities;

import android.app.Fragment;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.com.baseproject.baseproject.Fragments.GroupsFragment;
import br.com.baseproject.baseproject.Fragments.PlaceFragment;
import br.com.baseproject.baseproject.Fragments.ProfileFragment;
import br.com.baseproject.baseproject.Fragments.SearchFragment;
import br.com.baseproject.baseproject.R;
import br.com.baseproject.baseproject.Utils.Utils;

public class TabsMainActivity extends br.com.baseproject.baseproject.TabsMain.TabsMainActivity {

    TextView toolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_main);

        toolbarText = findViewById(R.id.toolbarTitle);

        Utils.setStatusBarColor(TabsMainActivity.this, R.color.colorAccent);

        new TabsMainBuilder()
                .setContainer(R.id.fragmentContainer)
                //TODO: pegar a cor certa
                .setNotSelectedColor("#ffffff")
                .setSelectedColor("#ffd11a")
                .setTabBar((LinearLayout) findViewById(R.id.belowTab))
                .addTab(getDrawable(R.drawable.icon_search), "#23538B", "Busca", new SearchFragment())
                .addTab(getDrawable(R.drawable.icon_chat), "#23538B", "Grupos", new GroupsFragment())
                .addTab(getDrawable(R.drawable.icon_home), "#23538B", "Moradia", new PlaceFragment())
                .addTab(getDrawable(R.drawable.icon_profile), "#23538B", "Perfil", new ProfileFragment())
                .init(this);
    }

    @Override
    public void onTabSelected(Fragment fragment) {
        super.onTabSelected(fragment);
    }
}
