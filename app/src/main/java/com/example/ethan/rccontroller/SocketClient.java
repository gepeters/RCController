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

import android.app.Activity;
import android.widget.EditText;



public class SocketClient extends AsyncTask<Void, Void, String> {

    String dstAddress;
    int dstPort;
    String RC_Command = "";
    String response = "";
    TextView textResponse;

    SocketClient(String addr, int port, String cmd, TextView textResponse) {
        dstAddress = addr;
        dstPort = port;
        RC_Command = cmd;
        this.textResponse = textResponse;
    }

    @Override
    protected String doInBackground(Void... arg0) {

        Socket socket = null;

        try {
            //Open socket connection to server
            socket = new Socket(dstAddress, dstPort);
            DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
            dout.writeUTF(RC_Command);
            dout.flush();
            //response = "command sent to socket:"+RC_Command;

            //Define buffer to transmit data to server
          /*  PrintWriter out = new PrintWriter(new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream())),
             true);
            out.println("left"); */
/*
            //Declare output stream for capturing return data from Server
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(
                    1024);
            byte[] buffer = new byte[1024];

            int bytesRead;

            //Get the input stream from the socket
            InputStream inputStream = socket.getInputStream(); */

			/*
             * notice: inputStream.read() will block if no data return
			 */
      /*      while ((bytesRead = inputStream.read(buffer)) != -1) {
                byteArrayOutputStream.write(buffer, 0, bytesRead);
                response += byteArrayOutputStream.toString("UTF-8");
            } */

        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "UnknownHostException: " + e.toString();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = "IOException: " + e.toString();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return response;
    }

    @Override
    protected void onPostExecute(String result) {
        textResponse.setText(response);
        super.onPostExecute(result);
    }

}
