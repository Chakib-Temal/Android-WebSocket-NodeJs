package com.chakibtemal.fr.android_nodejs;

import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView output;
    private Button startButton;
    private Socket socket;
    private SensorManager sensorManager;
    private Sensor accelero;
    JSONObject sensor = new JSONObject();
    List<SensorsValues> list = new ArrayList<SensorsValues>();

    private float x = 0;
    {
        try {
            socket = IO.socket("http://192.168.1.14:9999");
        } catch (URISyntaxException e) {}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        socket.connect();
        socket.on("hello to you too", onNewMessage);

        this.output = (TextView) findViewById(R.id.output);
        this.startButton = (Button) this.findViewById(R.id.start);
        this.sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        this.accelero = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        try {
            setJson();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        this.startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activeSensor();
                new Timer().scheduleAtFixedRate(new TimerTask(){
                    @Override
                    public void run(){
                        try {
                            attemptSend();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },2000,2000);
            }
        });
    }


    private void attemptSend() throws JSONException {
        JSONArray accelero = new JSONArray();

        for (int i= 0 ; i < list.size() ; i++){
            JSONObject acceleroSoons = new JSONObject();
            acceleroSoons.put("valueX",  list.get(i).getValueX());
            acceleroSoons.put("valueY",  list.get(i).getValueY());
            acceleroSoons.put("valueZ",  list.get(i).getValueZ());
            acceleroSoons.put("time"  ,  list.get(i).getTime());
            accelero.put(acceleroSoons);
        }
        sensor.put("accelero",accelero);

        list.clear();
        socket.emit("sendInformationUser", sensor);
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

    public void sendMessage(View view) throws JSONException {
        attemptSend();
    }

    public void setJson() throws JSONException {


    }

    final SensorEventListener mSensorEventListener = new SensorEventListener() {
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            SensorsValues values = new SensorsValues(sensorEvent.values[0], sensorEvent.values[0], sensorEvent.values[0],sensorEvent.timestamp );
            list.add(values);
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
        activeSensor();
    }

    public void activeSensor(){
        sensorManager.registerListener(mSensorEventListener, accelero, SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        onResume();
    }
}
