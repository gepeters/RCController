package com.example.ethan.rccontroller;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.R.color;

public class MainActivity extends AppCompatActivity {

    int angle=141;
    String RC_Car_IP="10.0.0.203";


    @Override
    protected void onCreate(Bundle savedInstanceState) {




        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });

       // Example of a call to a native method
       TextView tv = (TextView) findViewById(R.id.sample_text);
       tv.setText(stringFromJNI());

       //Get reference to Right button as define in the layout
       final Button RC_Right = (Button) findViewById(R.id.RC_Right);

       RC_Right.setOnTouchListener(new View.OnTouchListener (){

           private Handler mHandler;

           TextView mtv = (TextView) findViewById(R.id.sample_text);
           //int angle=currentAngle;
           String cmd="turn=";

           Runnable mAction = new Runnable() {
               @Override public void run() {
                   cmd = cmd+String.valueOf(angle);
                   mtv.setText(cmd);
                   SocketClient myClient = new SocketClient(RC_Car_IP, 21567, cmd, mtv);
                   myClient.execute();
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

            TextView mtv = (TextView) findViewById(R.id.sample_text);
            String cmd="turn=";

            Runnable mAction = new Runnable() {
                @Override public void run() {
                    cmd = cmd+String.valueOf(angle);
                    mtv.setText(cmd);
                    SocketClient myClient = new SocketClient(RC_Car_IP, 21567, cmd, mtv);
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

            TextView tv = (TextView) findViewById(R.id.sample_text);

            @Override public boolean onTouch(View v, MotionEvent M)
            {//Define what should be done when button is pressed

                switch(M.getAction()) {
                    case  MotionEvent.ACTION_DOWN:
                        tv.setText("Going Forward");
                        SocketClient myClient = new SocketClient(RC_Car_IP, 21567, "forward", tv);
                        myClient.execute();
                        break;
                    case MotionEvent.ACTION_UP:
                        tv.setText("Stopping");
                        myClient = new SocketClient(RC_Car_IP, 21567, "stop", tv);
                        myClient.execute();
                        break;
                }
                return false;

            }
        }); //End of SetOnTouchListener for Forward Button

//Get reference to Reverse button as define in the layout
        final Button RC_Backward = (Button) findViewById(R.id.RC_Backward);
        RC_Backward.setOnTouchListener(new View.OnTouchListener (){

            TextView tv = (TextView) findViewById(R.id.sample_text);

            @Override public boolean onTouch(View v, MotionEvent M)
            {//Define what should be done when button is pressed

                switch(M.getAction()) {
                    case  MotionEvent.ACTION_DOWN:
                        tv.setText("Going Backward");
                        SocketClient myClient = new SocketClient(RC_Car_IP, 21567, "backward", tv);
                        myClient.execute();
                        break;
                    case MotionEvent.ACTION_UP:
                        tv.setText("Stopping");
                        myClient = new SocketClient(RC_Car_IP, 21567, "stop", tv);
                        myClient.execute();
                        break;
                }
                return false;

            }
        }); //End of SetOnTouchListener for Reverse Button

   }; //end of onCreate method


/*
    final Button RC_Left = (Button) findViewById(R.id.RC_Left);
    RC_Left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                TextView tv = (TextView) findViewById(R.id.sample_text);
                tv.setText("Going Left");
            }
    }); */



    //Call this method if turn right button clicked. RC_Right is defined in the XML for the view
    public void RC_Left(View view) {
        // Go Right code
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("Going Left");

      SocketClient myClient = new SocketClient(RC_Car_IP, 21567, "turn=200", tv);
      myClient.execute();
    }

    public void RC_Speed(View view) {
        // Go Right code
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("Set Speed to 10");

        SocketClient myClient = new SocketClient(RC_Car_IP, 21567, "speed50", tv);
        myClient.execute();
    }

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

    public void RC_Stop (View view) {
        // Go Right code
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("Stopping!");

        SocketClient myClient = new SocketClient(RC_Car_IP, 21567, "stop", tv);
        myClient.execute();

    }

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
