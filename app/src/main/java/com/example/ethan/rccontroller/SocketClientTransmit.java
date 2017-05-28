package com.example.ethan.rccontroller;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Gregg on 3/12/2017.
 * This Class takes a command and send to already open TCPIP socket.
 * Socket should be passed as a parameter to the method
 */

 public class SocketClientTransmit extends AsyncTask<Void, Void, String> {

        String RC_Command = "";
        String response = "";
        TextView textResponse;
        Socket socket = null;

        SocketClientTransmit(Socket RCsocket, String cmd, TextView textResponse) {
            socket = RCsocket;
            RC_Command = cmd;
            this.textResponse = textResponse;
        }

        @Override
        protected String doInBackground(Void... arg0) {

            try {
                //Transmit data to server
                DataOutputStream dout=new DataOutputStream(socket.getOutputStream());
                dout.writeUTF(RC_Command);
                dout.flush();
            } catch (UnknownHostException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "UnknownHostException: " + e.toString();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                response = "IOException: " + e.toString();
            } /* finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            } */
            return response;
        }

        @Override
        protected void onPostExecute(String result) {
            textResponse.setText(response);
            super.onPostExecute(result);
        }

    }
