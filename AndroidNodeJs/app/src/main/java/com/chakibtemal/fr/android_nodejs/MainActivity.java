package com.chakibtemal.fr.android_nodejs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
public class MainActivity extends AppCompatActivity {

    private TextView output;
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://192.168.1.14:9999");
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSocket.connect();
        this.output = (TextView) findViewById(R.id.output);

        mSocket.on("hello to you too", onNewMessage);
    }


    private void attemptSend() {
        String message = "hello";
        if (TextUtils.isEmpty(message)) {
            return;
        }
        mSocket.emit("sendInformationUser", message);
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String data = (String) args[0];
                    try {
                        data = data.toString();
                    } catch (Exception e) {
                        return;
                    }
                    output.setText(data);
                }

            });
        }
    };

    public void sendMessage(View view) {
        attemptSend();
    }
}
