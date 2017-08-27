package com.jkls.android.handhygenie;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.UUID;

public class MyApplication extends Application {
    int minor;
    int major;
    String e_name = "";
    private BeaconManager beaconManager;

    @Override
    public void onCreate() {
        super.onCreate();

        beaconManager = new BeaconManager(getApplicationContext());
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {

                showNotification(
                        "Your gate closes in 47 minutes.",
                        "Current security wait time is 15 minutes, "
                                + "and it's a 5 minute walk from security to the gate. "
                                + "Looks like you've got plenty of time!");

               minor = region.getMinor();
                major = region.getMajor();
                RequestQueue mRequestQueue;

                // Instantiate the cache
                Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);

                // Setup the network to use the HTTPURLConnection client
                Network network = new BasicNetwork(new HurlStack());

                // Instantiate the request queue
                mRequestQueue = new RequestQueue(cache, network);

                // Start the queue
                mRequestQueue.start();

                final String URL = "http://10.136.117.202:8080/nearby/" + e_name +"/" + major + "/" + minor ;
               /* final JSONObject jsonBody;
                try {
                    jsonBody = new JSONObject("body : {\"email\":\"" + e_name + "\" , \"major\":" + major + ",\"minor\":" + minor + "}");
                   *//* jsonBody = new JSONObject("{\"major\":\"" + major + "\"}");
                    jsonBody = new JSONObject("{\"minor\":\"" + minor + "\"}");*//*

               *//* try {
                    jsonBody.put("major", major);
                    jsonBody.put("minor", minor);
                    jsonBody.put("email", e_name);

                } catch (JSONException e) {
                    e.printStackTrace();
                }*//*

               *//* "message" : { "header" : {"version" : "1.2" },
                    "body"   : {"event" : "plan"}
                }*//*

                JsonObjectRequest jsObjRequest = new JsonObjectRequest
                        (Request.Method.GET, URL, jsonBody, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {
                                // mTxtDisplay.setText("Response: " + response.toString());
                                System.out.println("email aols");

                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO Auto-generated method stub
                                System.out.println("the email is: "  + jsonBody + error.getMessage() + error.getCause());

                               // System.out.println(jsonBody);


                            }
                        });;

                mRequestQueue.add(jsObjRequest);
                } catch (JSONException e) {
                    e.printStackTrace();
                    System.out.println("hey");
                }*/
                RequestParams params = new RequestParams();
                params.put("email", e_name);
                params.put("major", major);
                params.put("minor", minor);

                AsyncHttpClient client = new AsyncHttpClient();
                client.get(URL,params ,new AsyncHttpResponseHandler() {
                    // When the response returned by REST has Http response code '200'
                    @Override
                    public void onSuccess( String response) {
                        // Hide Progress Dialog
                        //prgDialog.hide();
                        Toast.makeText(getApplicationContext(), "Beacon Found", Toast.LENGTH_SHORT).show();
                        System.out.println("Success");
                    }
                    @Override
                    public void onFailure(int statusCode, Throwable error,
                                          String content) {
                        // Hide Progress Dialog
                        //prgDialog.hide();
                        // When Http response code is '404'

                        if(statusCode == 404){
                            Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                            System.out.println("Error" + statusCode);
                        }
                        // When Http response code is '500'
                        else if(statusCode == 500){
                            Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                            System.out.println("Error" + statusCode);
                        }
                        // When Http response code other than 404, 500
                        else{
                            String s_code =  Integer.toString(statusCode);
                           // Toast.makeText(getApplicationContext(),URL , Toast.LENGTH_LONG).show();
                            System.out.println("Error" + statusCode);
                        }
                    }
                });

            }

            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
            }
        });
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region("monitored region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 47598, 34527));
                beaconManager.startMonitoring(new Region("monitored region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 19684, 32215));
                beaconManager.startMonitoring(new Region("monitored region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), 37128, 45132));
            }
        });
    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, SignInActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        //PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
          //      new Intent[]{notifyIntent}, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Hand Hygenie")
                .setContentText("Beacon found")
                .setAutoCancel(true)
             //   .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }
}

