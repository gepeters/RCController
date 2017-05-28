package com.example.ethan.rccontroller;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.ExecutionException;

import android.R.color;

public class MainActivity extends AppCompatActivity {

    int angle=141; //Current Angle of front wheels
    String RC_Car_IP="10.0.0.6"; //IP Address of TCPIP socket on the car
    Socket mySocket=null; //Pointer to Socket server on car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setContentView(R.layout.remotecar_activity); //Use contraint Interface
       // Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        //Declare and open socket to car

      /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        }); */
        //Get pointer to switch
        Switch RC_Connect = (Switch) findViewById(R.id.RC_Connect);

        //Get pointer to webviewer and direct to car streamer
        WebView myWebView = (WebView) findViewById(R.id.RC_Cam);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        myWebView.loadUrl("http://10.0.0.6:8080/stream_simple.html");


       // Example of a call to a native method
       TextView RC_Status = (TextView) findViewById(R.id.RC_Status);
        RC_Status.setText("Initiating connection to car");
       //tv.setText(stringFromJNI());

        //Open the Socket
        SocketClientOpen myOpenClient = new SocketClientOpen (RC_Car_IP, 21567,RC_Status);
        try {
            mySocket=myOpenClient.execute().get();
            //Set the connect switch to on
            RC_Connect.setChecked(true);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //myOpenClient.execute();

        //Speed Control using SeekBar
        SeekBar RC_SpeedControl = (SeekBar) findViewById (R.id.RC_SpeedControl);
        // perform seek bar change listener event used for getting the progress value
        RC_SpeedControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

                //int currentSpeed=0;
                String CurrentSpeedString="speed";
                // Update status
                TextView RC_Status = (TextView) findViewById(R.id.RC_Status);
                RC_Status.setText("Setting Speed");

                TextView RC_SpeedValue = (TextView) findViewById(R.id.RC_SpeedValue);
                //SeekBar RC_SpeedControl = (SeekBar) findViewById (R.id.RC_SpeedControl);

                //currentSpeed=RC_SpeedControl.getProgress()*10;
                CurrentSpeedString+=progressChangedValue*10;

                SocketClientTransmit myClient = new SocketClientTransmit(mySocket, CurrentSpeedString, RC_Status);
                myClient.execute();

                RC_SpeedValue.setText(CurrentSpeedString);

                //Toast.makeText(MainActivity.this, "Seek bar progress is :" + progressChangedValue,
                //        Toast.LENGTH_SHORT).show();
            }
        });
        //End of Set Speed routine

        //Get reference to Right button as define in the layout
        final Button RC_Right = (Button) findViewById(R.id.RC_Right);

        RC_Right.setOnTouchListener(new View.OnTouchListener (){

            private Handler mHandler;

            TextView RC_Status = (TextView) findViewById(R.id.RC_Status);
            //TextView mtv = (TextView) findViewById(R.id.sample_text);
            //int angle=currentAngle;
            String cmd="turn=";

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    cmd = cmd+String.valueOf(angle);
                    RC_Status.setText(cmd);
                    SocketClientTransmit myTransmitClient = new SocketClientTransmit (mySocket, cmd, RC_Status);
                    myTransmitClient.execute();
                    mHandler.postDelayed(this, 5);
                    angle +=2;
                    if (angle > 255) {angle=255;};
                    cmd="turn=";
                }
            }; //End of mAction Method

            @Override public boolean onTouch(View v, MotionEvent M)
            {//Define what should be done when button is pressed

                switch(M.getAction()) {
                    case  MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 5);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return false;

            }
        }); //End of SetOnTouchListener for Right Button





        //Get reference to Left button as define in the layout
        final Button RC_Left = (Button) findViewById(R.id.RC_Left);

        RC_Left.setOnTouchListener(new View.OnTouchListener (){

            private Handler mHandler;

            TextView RC_Status = (TextView) findViewById(R.id.RC_Status);
            String cmd="turn=";

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    cmd = cmd+String.valueOf(angle);
                    RC_Status.setText(cmd);
                    SocketClientTransmit myClient = new SocketClientTransmit (mySocket, cmd, RC_Status);
                    myClient.execute();
                    mHandler.postDelayed(this, 5);
                    angle -=2;
                    if (angle < 0) {angle=0;};
                    cmd="turn=";
                }
            }; //End of mAction Method

            @Override public boolean onTouch(View v, MotionEvent M)
            {//Define what should be done when button is pressed

                switch(M.getAction()) {
                    case  MotionEvent.ACTION_DOWN:
                        if (mHandler != null) return true;
                        mHandler = new Handler();
                        mHandler.postDelayed(mAction, 5);
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mHandler == null) return true;
                        mHandler.removeCallbacks(mAction);
                        mHandler = null;
                        break;
                }
                return false;

            }
        }); //End of SetOnTouchListener for Left Button

        //Get reference to Forward button as define in the layout
        final Button RC_Forward = (Button) findViewById(R.id.RC_Forward);
        RC_Forward.setOnTouchListener(new View.OnTouchListener (){

            TextView RC_Status = (TextView) findViewById(R.id.RC_Status);

            @Override public boolean onTouch(View v, MotionEvent M)
            {//Define what should be done when button is pressed

                switch(M.getAction()) {
                    case  MotionEvent.ACTION_DOWN:
                        RC_Status.setText("Going Forward");
                        SocketClientTransmit myClient = new SocketClientTransmit (mySocket, "forward", RC_Status);
                        //SocketClient myClient = new SocketClient(RC_Car_IP, 21567, "forward", tv);
                        myClient.execute();
                        break;
                    case MotionEvent.ACTION_UP:
                        RC_Status.setText("Stopping");
                        myClient = new SocketClientTransmit (mySocket, "stop", RC_Status);
                        //myClient = new SocketClient(RC_Car_IP, 21567, "stop", tv);
                        myClient.execute();
                        break;
                }
                return false;

            }
        }); //End of SetOnTouchListener for Forward Button

//Get reference to Reverse button as define in the layout
        final Button RC_Backward = (Button) findViewById(R.id.RC_Backward);
        RC_Backward.setOnTouchListener(new View.OnTouchListener (){

            TextView RC_Status = (TextView) findViewById(R.id.RC_Status);

            @Override public boolean onTouch(View v, MotionEvent M)
            {//Define what should be done when button is pressed

                switch(M.getAction()) {
                    case  MotionEvent.ACTION_DOWN:
                        RC_Status.setText("Going Backward");
                        SocketClientTransmit myClient = new SocketClientTransmit (mySocket, "backward", RC_Status);
                        //SocketClient myClient = new SocketClient(RC_Car_IP, 21567, "backward", tv);
                        myClient.execute();
                        break;
                    case MotionEvent.ACTION_UP:
                        RC_Status.setText("Stopping");
                        myClient = new SocketClientTransmit (mySocket, "stop", RC_Status);
                        //myClient = new SocketClient(RC_Car_IP, 21567, "stop", tv);
                        myClient.execute();
                        break;
                }
                return false;

            }
        }); //End of SetOnTouchListener for Reverse Button

    }; //end of onCreate method


    //Method called after OnCreate method complete.
    //protected void onPostCreate(Bundle savedInstanceState) {
    //
    //}; //End of onPostCreate method

/*

    public void RC_Right(View view) {
        // Go Right code
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("Going Right");

        SocketClient myClient = new SocketClient(RC_Car_IP, 21567, "turn=15", tv);
        myClient.execute();
    }

    public void RC_Forward(View view) {
        // Go Right code
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("Going Forward");

        SocketClient myClient = new SocketClient(RC_Car_IP, 21567, "forward", tv);
        myClient.execute();
    }

    public void RC_Reverse(View view) {
        // Go Right code
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("Backing Up");

        SocketClient myClient = new SocketClient(RC_Car_IP, 21567, "backward", tv);
        myClient.execute();
    }
*/

    public void RC_Toggle (View view) {
        // Connect or disconnect to or from car depending on current state of the toggle switch

        //Get pointer to switch
        Switch RC_Connect = (Switch) findViewById(R.id.RC_Connect);
        TextView RC_Status = (TextView) findViewById(R.id.RC_Status);

        if (!RC_Connect.isChecked()) {//Disconnect from the car
            SocketClientClose myClient = new SocketClientClose(mySocket, RC_Status);
            myClient.execute();

            RC_Status.setText("Disconnected!");
            //RC_Connect.setChecked(true);
        } //end of disconnect routine
        else { //Connect to the car
            //Open the Socket
            SocketClientOpen myOpenClient = new SocketClientOpen (RC_Car_IP, 21567,RC_Status);
            try {
                mySocket=myOpenClient.execute().get();
                RC_Status.setText("Connected!");
                //Set the connect switch to on
               // RC_Connect.setChecked(false);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    public void RC_Stop (View view) {
        // Go Right code
        TextView RC_Status = (TextView) findViewById(R.id.RC_Status);
        RC_Status.setText("Stopping!");

  /*      if (mySocket != null) {
            try {
                mySocket.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } */

        SocketClientClose myClient = new SocketClientClose(mySocket, RC_Status);
        myClient.execute();

    }


    public void RC_Speed(View view) {

        int currentSpeed=0;
        String CurrentSpeedString="speed";
        // Go Right code
        TextView RC_Status = (TextView) findViewById(R.id.RC_Status);
        RC_Status.setText("Set Speed to 10");

        TextView RC_SpeedValue = (TextView) findViewById(R.id.RC_SpeedValue);
        SeekBar RC_SpeedControl = (SeekBar) findViewById (R.id.RC_SpeedControl);

        currentSpeed=RC_SpeedControl.getProgress()*10;
        CurrentSpeedString+=currentSpeed;

        //SocketClientTransmit myClient = new SocketClientTransmit(mySocket, CurrentSpeedString, RC_Status);
        //myClient.execute();

        RC_SpeedValue.setText(CurrentSpeedString);

    };// end of RC_Speed method

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }
}
