package com.example.ethan.rccontroller;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

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
/*
    final Button RC_Left = (Button) findViewById(R.id.RC_Left);
    RC_Left.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                TextView tv = (TextView) findViewById(R.id.sample_text);
                tv.setText("Going Left");
            }
    }); */

    }
    //Call this method if turn right button clicked. RC_Right is defined in the XML for the view
    public void RC_Left(View view) {
        // Go Right code
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("Going Left");

        SocketClient myClient = new SocketClient("192.168.1.8", 8080, tv);
        myClient.execute();
    }

    public void RC_Right(View view) {
        // Go Right code
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("Going Right");
    }

    public void RC_Forward(View view) {
        // Go Right code
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("Going Forward");
    }

    public void RC_Reverse(View view) {
        // Go Right code
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("Backing Up");
    }

    public void RC_Stop (View view) {
        // Go Right code
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText("Stopping!");
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
