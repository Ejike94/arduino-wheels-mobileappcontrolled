package com.nnodi.ejike.firstapp;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;


public class activity_choose_mode extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_mode);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_choose_mode, menu);
        return true;
    }

    public void manual_activity (View view) {
        BluetoothAdapter mBA = BluetoothAdapter.getDefaultAdapter();
          int REQUEST_ENABLE_BT=1;
          if (!mBA.isEnabled()) {
                   Intent enableBt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                   startActivityForResult(enableBt, REQUEST_ENABLE_BT);
                   Toast.makeText(this,"Bluetooth Enabled", Toast.LENGTH_SHORT).show();
           }

            else {  Intent intent = new Intent(this, DisplayMessageActivity.class);
              startActivity(intent);
          }
     }

    public void auto_activity (View view) {
        BluetoothAdapter mBA = BluetoothAdapter.getDefaultAdapter();
        int REQUEST_ENABLE_BT=1;
        if (!mBA.isEnabled()) {
            Intent enableBt = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBt, REQUEST_ENABLE_BT);
            Toast.makeText(this,"Bluetooth Enabled", Toast.LENGTH_SHORT).show();
        }

        else {  Intent intent = new Intent(this, auto_mode_activity.class);
            startActivity(intent);
        }
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

        return super.onOptionsItemSelected(item);
    }
}
