package com.chakibtemal.fr.android_nodejs;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView output;
    private Socket mSocket;
    private SensorManager sensorManager;
    private Sensor accelero;
    private float x = 0;
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
        mSocket.on("hello to you too", onNewMessage);

        this.output = (TextView) findViewById(R.id.output);
        this.sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);

        this.accelero = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);


        new Timer().scheduleAtFixedRate(new TimerTask(){
            @Override
            public void run(){
                attemptSend();
            }
        },2000,2000);
    }


    private void attemptSend() {

        mSocket.emit("sendInformationUser", x);
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

    final SensorEventListener mSensorEventListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            x = sensorEvent.values[0];
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(mSensorEventListener, accelero);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(mSensorEventListener, accelero, SensorManager.SENSOR_DELAY_FASTEST);
    }
}
