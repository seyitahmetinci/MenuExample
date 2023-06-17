package com.example.menuexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    FrameLayout framelaout;
    TabLayout tablayt;
    Fragment fragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        framelaout=findViewById(R.id.framelayout);
        tablayt=findViewById(R.id.simpleTablayout);

        TabLayout.Tab firstTab=tablayt.newTab();
        firstTab.setText("First");
        tablayt.addTab(firstTab);

        TabLayout.Tab secondTab=tablayt.newTab();
        secondTab.setText("Second");
        tablayt.addTab(secondTab);

        fragment=new ContextFragment();
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        ft.replace(R.id.framelayout, fragment);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        ft.commit();

        tablayt.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {


                switch(tab.getPosition()){
                    case 0:
                        fragment=new ContextFragment();
                        break;
                    case 1:
                        fragment=new PopupFragment();
                        break;
                }
                FragmentManager fm=getSupportFragmentManager();
                FragmentTransaction ft=fm.beginTransaction();
                ft.replace(R.id.framelayout,fragment);
                ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                ft.commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        String message = "";
        switch (item.getItemId()) {
            case R.id.menuAbout:
                message = "You selected About";
                break;
            case R.id.menuContact:
                message = "You selected Contact";
                break;
            case R.id.subMenuCommunication:
                message = "You selected Communication";
                break;
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }
}