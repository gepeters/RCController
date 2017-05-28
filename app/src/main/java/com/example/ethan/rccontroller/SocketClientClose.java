package com.example.ethan.rccontroller;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Gregg on 3/12/2017.
 */

public class SocketClientClose extends AsyncTask<Void, Void, String> {

    String response = "";
    TextView textResponse;
    Socket socket = null;

    SocketClientClose(Socket RCsocket, TextView textResponse) {
        socket = RCsocket;
        this.textResponse = textResponse;
}

    @Override
    protected String doInBackground(Void... arg0) {

            try {
                response = "socket disconnected";
             }
            finally {
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