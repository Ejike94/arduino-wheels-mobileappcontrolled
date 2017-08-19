package com.nnodi.ejike.firstapp;
import android.app.AlertDialog;
import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Parcelable;
import android.support.v7.app.ActionBarActivity;
import android.bluetooth.*;
import android.os.Bundle;
import android.text.style.TabStopSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.UUID.*;

import org.apache.http.client.utils.URIUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;


public class DisplayMessageActivity extends ActionBarActivity  {
    BluetoothAdapter mBA = BluetoothAdapter.getDefaultAdapter();
    boolean check = false;
    int j=0;
   // ConnectThread ct;
   // CommunicationThread ctt;
    InputStream mmInStream;
    OutputStream mmOutStream;
  //  BluetoothSocket mmSocket;
   boolean checkForScan = false;
    List<String> newDevaName = new ArrayList<>();
    List<String> newDevaAddress = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
      //  int REQUEST_ENABLE_BT=1;
       /* Intent enableBt = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        enableBt.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
        startActivity(enableBt);*/
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        ImageView up = (ImageView) findViewById(R.id.up);
        ImageView down = (ImageView) findViewById(R.id.down);
        ImageView left= (ImageView) findViewById(R.id.left);
        ImageView right = (ImageView) findViewById(R.id.right);

        up.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch (View view, MotionEvent event){
                ImageButton btn;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn = (ImageButton) findViewById(R.id.up);
                    btn.setImageResource(R.drawable.upp);
                    sendData("w");
                       }
                    else if (event.getAction() == MotionEvent.ACTION_UP)
                      {
                          btn = (ImageButton) findViewById(R.id.up);
                          btn.setImageResource(R.drawable.up);
                          sendData("e");
                    }
                return false;
            }});
        down.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch (View view, MotionEvent event){
                ImageButton btn;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn = (ImageButton) findViewById(R.id.down);
                    btn.setImageResource(R.drawable.downp);
                    sendData("x");
                }
                else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    btn = (ImageButton) findViewById(R.id.down);
                    btn.setImageResource(R.drawable.down);
                    sendData("e");
                }
                return false;
            }});
        left.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch (View view, MotionEvent event){
                ImageButton btn;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn = (ImageButton) findViewById(R.id.left);
                    btn.setImageResource(R.drawable.leftp);
                    sendData("a");
                }
                else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    btn = (ImageButton) findViewById(R.id.left);
                    btn.setImageResource(R.drawable.back);
                    sendData("e");
                }
                return false;
            }});
        right.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch (View view, MotionEvent event){
                ImageButton btn;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btn = (ImageButton) findViewById(R.id.right);
                    btn.setImageResource(R.drawable.rightp);
                    sendData("d");
                }
                else if (event.getAction() == MotionEvent.ACTION_UP)
                {
                    btn = (ImageButton) findViewById(R.id.right);
                    btn.setImageResource(R.drawable.front);
                    sendData("e");
                }
                return false;
            }});
    }

    public void LookPairedDevice()
    {
        Set<BluetoothDevice> pairedDevices = mBA.getBondedDevices();
        int size= pairedDevices.size();
        // If there are paired devices
        String [] name = new String [size];
        String [] DId =  new String [size];
        if (size > 0) {
            int i=0;
            // Loop through paired devices
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                name[i]= device.getName();
                DId[i] = device.getAddress();
                i++;
            }
        }
        BlueDialog(size, name);
    }

    public void BlueDialog (int size, String [] name) {

      if (size==0)
      { name = new String [1];
        name[0] ="No Device found";
      }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.bluetooth_dialog_title)
                .setItems(name, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // The 'which' argument contains the index position
                        // of the selected item
                    }
                })
                .setPositiveButton(R.string.bluetooth_dialog_final, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        scanDevices();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void scanDevices() {
        int j=0;
        mBA.startDiscovery();
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy
       // IntentFilter ifilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        // Create a BroadcastReceiver for ACTION_FOUND
    }

    // Create a BroadcastReceiver for ACTION_FOUND
    public void alertUser()
    { Toast.makeText(this,"Device found",Toast.LENGTH_SHORT).show();}


    public void alertConnection()
    {
        if(check)
            Toast.makeText(this,"Connection Made",Toast.LENGTH_SHORT).show();
       else
            Toast.makeText(this,"Unable to make connection",Toast.LENGTH_SHORT).show();
    }



    public void showDevices(){

        mBA.cancelDiscovery();
        String [] Nname;
        Nname = new String[newDevaName.size()];


        for(j=0; j< newDevaName.size();j++)
        {
            Nname[j]= newDevaName.get(j);
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.bluetooth_dialog_title_new)
                .setItems(Nname, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                       BluetoothDevice device = mBA.getRemoteDevice(newDevaAddress.get(which));
               // Parcelable[] MY_UUID = mBA.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
                        pairDevice(device);
                          ConnectThread ct= new ConnectThread(device);
                           ct.runThread();
                      //  ct.cancel();
                            alertConnection();

                    }
                })
                .setPositiveButton(R.string.bluetooth_dialog_final, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        scanDevices();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
          alertUser();
            String action = intent.getAction();
            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //   Parcelable[]  = intent.getParcelableArrayExtra(BluetoothDevice.EXTRA_UUID);
                // Add the name and address to an array adapter to show in a ListView
                newDevaName.add(device.getName());
                newDevaAddress.add(device.getAddress());
            }
        }
    };




    public void getInOutStream (BluetoothSocket mmS) {

        CommunicationThread(mmS);
    }

    public void sendData (String arrow) {
               sendStream(arrow.getBytes());
        }

public void toast(){
    Toast.makeText(this,"Unable to connect",Toast.LENGTH_SHORT).show();}

    private void pairDevice(BluetoothDevice device) {
        try {
            Method method = device.getClass().getMethod("createBond", (Class[]) null);
            method.invoke(device, (Object[]) null);
        } catch (Exception e) {

        }
    }
    //CONNECT THREAD
    private class ConnectThread extends Thread {

    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    Intent intent;

    public ConnectThread(BluetoothDevice device) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;
        UUID uuid = mmDevice.getUuids()[0].getUuid();
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {

            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            toast();

        }
        mmSocket = tmp;
    }

    public void runThread() {
        // Cancel discovery because it will slow down the connection
       // mBA.cancelDiscovery();
        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
            getInOutStream(mmSocket);
            check = true;

        } catch (IOException connectException) {
            check = false;
            // Unable to connect; close the socket and get out
            try {
                 mmSocket.close();

            } catch (IOException closeException) {
            }
            return;
        }
    }
}

    /*public void ConnectThread(BluetoothDevice device) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;
        UUID uuid = mmDevice.getUuids()[0].getUuid();
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {

            // MY_UUID is the app's UUID string, also used by the server code
            tmp = mmDevice.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) { }
        mmSocket = tmp;
    }

*/

    //COMMUNICATION THREAD
    public void CommunicationThread(BluetoothSocket socket) {
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }


    public void sendStream(byte[] bytes) {
       try {
            mmOutStream.write(bytes);
        } catch (IOException e) {
            Toast.makeText(this, "Unable to connect", Toast.LENGTH_SHORT).show();

        }
    }

    public void send(){

        Toast.makeText(this,"pressed",Toast.LENGTH_SHORT).show();
    }

    public void sendMessage(View view) {
        // Do something in response to button
    }







   /* private class CommunicationThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public CommunicationThread(BluetoothSocket socket) {
            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            // Get the input and output streams, using temp objects because
            // member streams are final
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) { }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void send(byte[] bytes) {
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) { }
        }
        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream
            int bytes; // bytes returned from read()

            // Keep listening to the InputStream until an exception occurs

          /*  while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    // Send the obtained bytes to the UI activity
                    mHandler.obtainMessage(MESSAGE_READ, bytes, -1, buffer)
                            .sendToTarget();
                } catch (IOException e) {
                    break;
                }*/


   /* private class ConnectThread extends Thread {

        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        Intent intent;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;
            UUID uuid = mmDevice.getUuids()[0].getUuid();
            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {

                // MY_UUID is the app's UUID string, also used by the server code
                tmp = device.createRfcommSocketToServiceRecord(uuid);
            } catch (IOException e) { }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            mBA.cancelDiscovery();
            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
                check = true;

            } catch (IOException connectException) {
                check = false;
                // Unable to connect; close the socket and get out
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            //  manageConnectedSocket(mmSocket);
        }

        /** Will cancel an in-progress connection, and close the socket */

   // BluetoothDevice ;


    // Register the BroadcastReceiver
       @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_message, menu);
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
        else if (id == R.id.action_connect_bluetooth)
        { checkForScan = true;
            LookPairedDevice();}
        else if (id == R.id.action_showDeva_found)
        {  if(checkForScan)
             {
               showDevices();
             }
            else
               Toast.makeText(this,"You need to scan for devices first",Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
