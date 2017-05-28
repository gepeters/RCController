package com.example.ethan.rccontroller;

/**
 * Created by Gregg on 2/19/2017.
 */

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutionException;

import android.app.Activity;
import android.widget.EditText;



public class SocketClientOpen extends AsyncTask<Void, Void, Socket> {

    String dstAddress;
    int dstPort;
        //String RC_Command = "";
    String response = "";
    TextView textResponse;
    Socket RCSocket = null;

    SocketClientOpen(String addr, int port, TextView localtextResponse) {
        dstAddress=addr;
        dstPort=port;
        this.textResponse = localtextResponse;
    }

    @Override
    protected Socket doInBackground(Void... arg0) {

        try {
            //Open socket connection to server
            //Socket RCSocket = null;
            RCSocket = new Socket(dstAddress, dstPort);
            //DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
            //dout.writeUTF(RC_Command);
            //dout.flush();
            response = "command sent to socket:"; //+RC_Command;

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        }

        return RCSocket;
    };
/*
    @Override
    protected void onPostExecute(String result) {
        textResponse.setText(response);
        super.onPostExecute(result);
    }   */

}
