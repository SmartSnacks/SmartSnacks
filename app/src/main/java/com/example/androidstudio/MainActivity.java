package com.example.androidstudio;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.PopupWindow;


import com.example.androidstudio.ui.Item.ItemsFragment;
import com.example.androidstudio.ui.Necessities.NecessitiesFragment;
import com.example.androidstudio.ui.Pantry.PantryFragment;
import com.example.androidstudio.ui.Recipe.RecipesFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MaterialToolbar toolbar = findViewById(R.id.topAppBar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                Log.d("myTag","abc2");
                drawer.openDrawer(GravityCompat.START);
                Log.d("myTag","abc");
            }
        });
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                item.setChecked(true);
                drawer.closeDrawer(GravityCompat.START);

                switch (id)
                {
                    case R.id.nav_pantry:
                        setSupportActionBar(toolbar);
                        toolbar.setTitle("Pantry");
                        replaceFragment(new PantryFragment());
                        break;
                    case R.id.nav_items:
                        setSupportActionBar(toolbar);
                        toolbar.setTitle("Items");
                        replaceFragment(new ItemsFragment());
                        break;
                    case R.id.nav_necessities:
                        setSupportActionBar(toolbar);
                        toolbar.setTitle("Necessities");
                        replaceFragment(new NecessitiesFragment());
                        break;
                    case R.id.nav_recipes:
                        setSupportActionBar(toolbar);
                        toolbar.setTitle("Recipes");
                        replaceFragment(new RecipesFragment());
                        break;
                    default:
                        return true;
                }
                return true;
            }
        });
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();
    }
    //title page changing
   //code goes here

    // the three dots that will do a drop down bar in main.xml
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //navigating
    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


//OUR CODE STARTS HERE.

    //add item button pop up window
    public void buttonPopupwindow(View v){
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View viewPopupwindow = layoutInflater.inflate(R.layout.popup_pantry,null);
        PopupWindow popupwindow = new PopupWindow(viewPopupwindow,700,800, true);

        popupwindow.showAtLocation(v, Gravity.CENTER, 0,0);

        //closing button
        Button close = (Button) viewPopupwindow.findViewById(R.id.button_cancel);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupwindow.dismiss();
            }
        });
        /*
        //adding button
        Button add = (Button) viewPopupwindow.findViewById(R.id.button_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Item(s) successfully added", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

    }

    Connection connect;
    String ConnectionResult = "";

    public void GetTextFromSQL(View v) {
        //TextView tx2 = (TextView) findViewById(R.id.textView2);
        //TextView tx3 = (TextView) findViewById(R.id.textView3);

        try{
            Database connectionHelper = new Database();
            connect = connectionHelper.connectionclass();
            if(connect != null){
                String query = "SELECT * FROM Item";
                Statement st = connect.createStatement();
                ResultSet rs = st.executeQuery(query);

                while(rs.next()){
                    //tx2.setText(rs.getString(0));
                    //tx3.setText(rs.getString(0));
                }
            }else{
                ConnectionResult = "Check connection";
            }
        }catch(Exception ex){

    }

    }

}

