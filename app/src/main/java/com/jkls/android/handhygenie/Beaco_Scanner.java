/*
package com.jkls.android.handhygenie;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.cloud.model.Color;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import estimote.BeaconID;
import estimote.EstimoteCloudBeaconDetails;
import estimote.EstimoteCloudBeaconDetailsFactory;
import estimote.ProximityContentManager;

*/
/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 *//*

public class Beaco_Scanner extends IntentService {
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.jkls.android.handhygenie.action.FOO";
    private static final String ACTION_BAZ = "com.jkls.android.handhygenie.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.jkls.android.handhygenie.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.jkls.android.handhygenie.extra.PARAM2";

    public Beaco_Scanner() {
        super("Beaco_Scanner");
    }

    */
/**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
    Intent intent = new Intent(context, Beaco_Scanner.class);
    intent.setAction(ACTION_FOO);
    intent.putExtra(EXTRA_PARAM1, param1);
    intent.putExtra(EXTRA_PARAM2, param2);
    context.startService(intent);
    }

     *
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
    Intent intent = new Intent(context, Beaco_Scanner.class);
    intent.setAction(ACTION_BAZ);
    intent.putExtra(EXTRA_PARAM1, param1);
    intent.putExtra(EXTRA_PARAM2, param2);
    context.startService(intent);
    }*//*


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {

            final String  email = intent.getStringExtra("email");
            System.out.println("made it this far");
            scan(email);
            scan2();
        }
    }

    */
/**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     *//*

   */
/* private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    *//*
*/
/**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     *//*
*/
/*
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }*//*



   // private static final String TAG = "MainActivity";
    public void scan(final String email){
      final Map<Color, Integer> BACKGROUND_COLORS = new HashMap<>();

    {
        BACKGROUND_COLORS.put(Color.ICY_MARSHMALLOW, android.graphics.Color.rgb(109, 170, 199));
        BACKGROUND_COLORS.put(Color.BLUEBERRY_PIE, android.graphics.Color.rgb(98, 84, 158));
        BACKGROUND_COLORS.put(Color.MINT_COCKTAIL, android.graphics.Color.rgb(155, 186, 160));
    }

     final int BACKGROUND_COLOR_NEUTRAL = android.graphics.Color.rgb(160, 169, 172);

     ProximityContentManager proximityContentManager;



        System.out.println("Hey12");

        proximityContentManager = new ProximityContentManager(this,
                Arrays.asList(
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 47598, 34527),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 19684, 32215),
                        new BeaconID("B9407F30-F5F8-466E-AFF9-25556B57FE6D", 37128, 45132)),
                new EstimoteCloudBeaconDetailsFactory());
        proximityContentManager.setListener(new ProximityContentManager.Listener() {
            @Override
            public void onContentChanged(Object content) {
                System.out.println("heyerye");
                String text;
                Integer backgroundColor;
                if (content != null) {
                    EstimoteCloudBeaconDetails beaconDetails = (EstimoteCloudBeaconDetails) content;
                    text = "You're in " + beaconDetails.getBeaconName() + "'s range!";
                    Beacon bae = (Beacon) content;
                   int major = bae.getMajor();
                    int minor = bae.getMinor();
                    text = beaconDetails.getBeaconName();

                    RequestParams params = new RequestParams();
                    params.put("email", email);
                    params.put("major", major);
                    params.put("minor", minor);

                    NotificationCompat.Builder mBuilder =
                            new NotificationCompat.Builder(getApplicationContext())
                                    .setSmallIcon(R.drawable.logo_small)
                                    .setContentText("Beacon found")
                                    .setContentTitle("Hand Hygenie");
                    int mNotificationId = 001;
// Gets an instance of the NotificationManager service
                    NotificationManager mNotifyMgr =
                            (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
// Builds the notification and issues it.
                    mNotifyMgr.notify(mNotificationId, mBuilder.build());
                   // invokeWS(params, email, major, minor);
                    backgroundColor = BACKGROUND_COLORS.get(beaconDetails.getBeaconColor());
                } else {
                    text = "No beacons in range.";
                    backgroundColor = null;
                }

            }
        });
    }
    public void scan2() {
    final   BeaconManager beaconManager = new BeaconManager(getApplicationContext());
// add this below:
        beaconManager.setMonitoringListener(new BeaconManager.MonitoringListener() {
            @Override
            public void onEnteredRegion(Region region, List<Beacon> list) {
                showNotification(
                        "Your gate closes in 47 minutes.",
                        "Current security wait time is 15 minutes, "
                                + "and it's a 5 minute walk from security to the gate. "
                                + "Looks like you've got plenty of time!");
            }
            @Override
            public void onExitedRegion(Region region) {
                // could add an "exit" notification too if you want (-:
            }
        });
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {



            @Override
            public void onServiceReady() {
                beaconManager.startMonitoring(new Region(
                        "monitored region",
                        UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"),
                        47598, 34527));

            }
        });


     */
/* public void invokeWS(RequestParams params, String num, int major, int minor){
        // Show Progress Dialog
        // prgDialog.show();
        // Make RESTful webservice call using AsyncHttpClient object

        AsyncHttpClient client = new AsyncHttpClient();
        final String URL = "http://192.168.0.102:8080/nearby" ;
       // final JSONObject jsonBody = new JSONObject({\"major\": major \"} );
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, URL, (String)null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                      // mTxtDisplay.setText("Response: " + response.toString());
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO Auto-generated method stub

                    }
                });;

                        client.get(URL,params ,new AsyncHttpResponseHandler() {
                            // When the response returned by REST has Http response code '200'
                            @Override
                            public void onSuccess( String response) {
                                // Hide Progress Dialog
                                //prgDialog.hide();
                                // Toast.makeText(getApplicationContext(), "population: " + response, Toast.LENGTH_SHORT).show();
                                System.out.println("sent");
                            }
                            @Override
                            public void onFailure(int statusCode, Throwable error,
                                                  String content) {
                                // Hide Progress Dialog
                                //prgDialog.hide();
                                // When Http response code is '404'

                                if(statusCode == 404){
                                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                                }
                                // When Http response code is '500'
                                else if(statusCode == 500){
                                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                                }
                                // When Http response code other than 404, 500
                                else{
                                    String s_code =  Integer.toString(statusCode);
                                    Toast.makeText(getApplicationContext(),URL , Toast.LENGTH_LONG).show();
                                }
                            }
                        });
        }

    }

    public void showNotification(String title, String message) {
        Intent notifyIntent = new Intent(this, SignInActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivities(this, 0,
                new Intent[] { notifyIntent }, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(this)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .build();
        notification.defaults |= Notification.DEFAULT_SOUND;
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, notification);
    }

}
*/
