package com.nnodi.ejike.firstapp;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;



public class MainActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.nnodi.ejike.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if (id == R.id.action_connect_bluetooth) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void sendMessage (View view)
    {

        EditText user= (EditText)findViewById(R.id.appUsername);
        EditText pass= (EditText)findViewById(R.id.appPassword);
        String username = user.getText().toString();
        String password = pass.getText().toString();
        if(username.equals(password)) {
            Intent intent = new Intent(this, activity_choose_mode.class);
            startActivity(intent);
        }

    }
}
