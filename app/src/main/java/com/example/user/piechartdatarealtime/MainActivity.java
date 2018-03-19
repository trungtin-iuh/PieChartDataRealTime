package com.example.user.piechartdatarealtime;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.user.piechartdatarealtime.model.AddressParameterRealTimeData;
import com.example.user.piechartdatarealtime.model.ParameterObjectRealTimeData;
import com.example.user.piechartdatarealtime.model.RealTimeStreamingData;
import com.example.user.piechartdatarealtime.utils.GsonUtil;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.tavendo.autobahn.WebSocket;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketOptions;

public class MainActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    private Button btnConnect, btnDisConnect;

    private int startId = 0;

    private Map<String, Integer> mIdMapping = new HashMap<String, Integer>();
    private WebSocketConnection mWebSocketConnection = new WebSocketConnection();
    private static final String TAG =
            MainActivity.class.getSimpleName();
    static public final float PIE_CHART_NULLABLE_VALUE = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        init_view();
        
        init_onclick();
    }

    private void init_view() {
        btnConnect = findViewById(R.id.btnConnect);
        btnDisConnect = findViewById(R.id.btnDisConnect);
        linearLayout = findViewById(R.id.line1);
    }


    private void init_onclick() {
        btnDisConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                disConnectToWebsocket();
            }
        });
        
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToWebsocket("");
            }
        });
    }


    private void disConnectToWebsocket() {
        if(mWebSocketConnection.isConnected()){
            mWebSocketConnection.disconnect();
        }
        mWebSocketConnection = null;
    }

    private PieDataSet createPieDataSet(){

        PieDataSet pieDataSet = new PieDataSet(new ArrayList<PieEntry>(),null);
        pieDataSet.setDrawValues(true);
        pieDataSet.setSelectionShift(5);
        pieDataSet.setSliceSpace(1);
        pieDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);

        return pieDataSet;
    }

    private void setupDataBeforeRealtimeData(String requestRealTimeFormat){
        RealTimeStreamingData realTimeStreamingData = GsonUtil.getInstance()
                .fromJson (requestRealTimeFormat, RealTimeStreamingData.class);
        mPieChart = new PieChart(getApplicationContext());

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mPieChart.setLayoutParams(params);
        Description description = new Description();
        description.setText("");
        mPieChart.setDescription(description);
        linearLayout.addView(mPieChart);


        startId = 0;

        PieDataSet pieDataSet = createPieDataSet();// init only pie data set because each parameter is entry
        List<Integer> parameterColors = new ArrayList<>();// init color of parameter list

        parameterColors.add(Color.BLACK);
        parameterColors.add(Color.GRAY);
        parameterColors.add(Color.DKGRAY);
        parameterColors.add(Color.GREEN);
        parameterColors.add(Color.YELLOW);
        parameterColors.add(Color.RED);


        for(ParameterObjectRealTimeData parameterObjectRealTimeData : realTimeStreamingData.getObjects()) {

            String hostName = parameterObjectRealTimeData.getHostname();
            for (AddressParameterRealTimeData addressParameterRealTimeData : parameterObjectRealTimeData.getAddresses()) {
                mIdMapping.put(hostName + ":" + addressParameterRealTimeData.getAddress(), startId);// save id of parameter on hashmap and use set data on websocket incoming data
                parameterColors.add(Color.parseColor("#1bade1"));// add color for parameter
                pieDataSet.addEntry(new PieEntry(PIE_CHART_NULLABLE_VALUE, startId));// int value null data by zero

                startId++;
            }
        }


        pieDataSet.setColors(parameterColors); // add list color of pie data set
        pieDataSet.setValueFormatter(new PercentFormatter());


        PieData pieData = new PieData(pieDataSet);
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.BLUE);
        mPieChart.setData(pieData);
    }

    PieChart mPieChart;
    WebSocket.WebSocketConnectionObserver realTimeRetrieveHandle;

    private void connectToWebsocket(String wsURL) {
        String webSocketRealTimeDataUri = "wss://dataengine.globiots.com:443/data-engine/mobile/realtime";
        final String messageTogetDataRealTime = "{\"objects\": [{\"addresses\": [{\"address\":\"3000\",\"dataType\":\"integer_16\",\"length\":2,\"value\":\"\"},{\"address\":\"3002\",\"dataType\":\"integer_16\",\"length\":2,\"value\":\"\"},{\"address\":\"3004\",\"dataType\":\"integer_16\",\"length\":2,\"value\":\"\"},{\"address\": \"201A\",\"dataType\": \"float\",\"length\": 4},{\"address\": \"2020\",\"dataType\": \"float\",\"length\": 4},{\"address\": \"2000\",\"dataType\": \"float\",\"length\": 4}],\"hostname\": \"0.0.0.254\"}],\"sessionId\": \"\",\"timezone\": \"GMT+07:00\",\"updateTime\": 3}";

        // start init data - build layout
        setupDataBeforeRealtimeData(messageTogetDataRealTime);
        // end init data - transport data to layout
        try{
            realTimeRetrieveHandle =
                    new WebSocket.WebSocketConnectionObserver() {

                        @Override
                        public void onOpen() {
                            mWebSocketConnection.sendTextMessage(messageTogetDataRealTime);
                        }

                        @Override
                        public void onTextMessage(final String dataRealTime) {
                            refreshDataRealtimeToPiechart(dataRealTime);
                        }

                        @Override
                        public void onRawTextMessage(byte[] payload) {

                        }

                        @Override
                        public void onBinaryMessage(byte[] payload) {

                        }

                        @Override
                        public void onClose(final WebSocketCloseNotification code, final String reason) {

                        }
                    };
            WebSocketOptions websocketOptions = new WebSocketOptions();
            websocketOptions.setSocketConnectTimeout(15000);//ms ~ 15 s
            websocketOptions.setSocketReceiveTimeout(15000);//ms ~ 15 s

            mWebSocketConnection.connect(
                    new URI(webSocketRealTimeDataUri),
                    realTimeRetrieveHandle,
                    websocketOptions);

        } catch (final Exception e){
            Log.w("WEB_SOCKET", e.toString());

        }
    }

    private void refreshDataRealtimeToPiechart(final String dataRealTime) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                PieData pieData = mPieChart.getData();
                RealTimeStreamingData realTimeStreamingData = GsonUtil.getInstance()
                        .fromJson(dataRealTime, RealTimeStreamingData.class);

                for (ParameterObjectRealTimeData parameterObjectRealTimeData : realTimeStreamingData.getObjects()) {
                    String hostName = parameterObjectRealTimeData.getHostname();
                    for (AddressParameterRealTimeData addressParameterRealTimeData : parameterObjectRealTimeData.getAddresses()) {
                        Float value = null;
                        try {
                            value = new Float(addressParameterRealTimeData.getValue());
                        } catch (Exception e){
                            value = PIE_CHART_NULLABLE_VALUE;
                        }
                        if(mIdMapping.containsKey(hostName + ":" + addressParameterRealTimeData.getAddress())){
                            int sliceEntryIndex = mIdMapping.get(hostName + ":" + addressParameterRealTimeData.getAddress());

                            int dataSetIndex = 0;
                            IPieDataSet iPieDataSet = pieData.getDataSetByIndex(dataSetIndex);
                            PieDataSet pieDataSet = (PieDataSet) iPieDataSet;
                            if(pieDataSet != null){
                                Entry entry = pieDataSet.getEntryForIndex(sliceEntryIndex);
                                entry.setY(value);
                                pieDataSet.notifyDataSetChanged();
                            }

                        }
                    }
                }

                mPieChart.notifyDataSetChanged();
                mPieChart.invalidate();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWebSocketConnection.isConnected()){
            mWebSocketConnection.disconnect();
        }
        mWebSocketConnection = null;
    }
}
