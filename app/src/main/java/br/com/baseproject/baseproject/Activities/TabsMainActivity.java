package br.com.baseproject.baseproject.Activities;

import android.os.Bundle;
import android.widget.LinearLayout;

import br.com.baseproject.baseproject.Fragments.GroupsFragment;
import br.com.baseproject.baseproject.Fragments.PlaceFragment;
import br.com.baseproject.baseproject.Fragments.ProfileFragment;
import br.com.baseproject.baseproject.Fragments.SearchFragment;
import br.com.baseproject.baseproject.R;

public class TabsMainActivity extends br.com.baseproject.baseproject.TabsMain.TabsMainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs_main);

        new TabsMainBuilder()
                .setContainer(R.id.fragmentContainer)
                //TODO: pegar a cor certa
                .setNotSelectedColor("#444349")
                .setSelectedColor("#0f4f6d")
                .setTabBar((LinearLayout) findViewById(R.id.belowTab))
                .addTab(getDrawable(R.drawable.icon_search), "#eeeeee", "Emissões", new SearchFragment())
                .addTab(getDrawable(R.drawable.icon_chat), "#eeeeee", "Declarações", new GroupsFragment())
                .addTab(getDrawable(R.drawable.icon_home), "#eeeeee", "Financeiro", new PlaceFragment())
                .addTab(getDrawable(R.drawable.icon_profile), "#eeeeee", "DAS", new ProfileFragment())
                .init(this);
    }
}
