package com.nawsldriver.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.nawsldriver.database.AppDatabase;
import com.nawsldriver.database.RWServices;
import com.nawsldriver.global.Constants;
import com.nawsldriver.global.Variables;

import java.util.HashMap;
import java.util.Map;

public class RequestServices {
    private static final String TAG = RequestServices.class.getName();
    private Context context;

    public RequestServices (Context context) {
        this.context = context;
    }

    public void acceptRide(final String tripId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String tajiUrl = "https://taji.kennedykitho.me/taji/firebase/request-ride/accepted";

        final String rType = "703";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tajiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "===================================== COMPLETE");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "===================================== ERROR " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                AppDatabase appDatabase = AppDatabase.getDatabase(context);
                RWServices rwServices = new RWServices(appDatabase);

                String vehicleMake = rwServices.getVehicleMake();
                String vehicleModel = rwServices.getVehicleModel();
                String vehicleRegNo = rwServices.getVehicleRegNo();
                String vehicleDesc = vehicleMake + " " + vehicleModel;

                params.put("name", Variables.ACCOUNT_NAME);
                params.put("request_type", rType);
                params.put("driver_phone", Variables.ACCOUNT_PHONE);
                params.put("reg_no", vehicleRegNo);
                params.put("vehicle_make", vehicleDesc);
                params.put("passenger_phone", Variables.PASSENGER_PHONE);
                params.put("trip_id", tripId);

                Log.d(TAG, "=====================================" + params);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void startTrip(final String passengerPhone) {
        RequestQueue queue = Volley.newRequestQueue(context);

        String tajiUrl = Constants.API_HEADER + Constants.TRIP_ALERT_START;

        final String rType = "805";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tajiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "===================================== COMPLETE");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "===================================== ERROR " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("passenger_phone", passengerPhone);

                Log.d(TAG, "=====================================" + params);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        queue.add(stringRequest);
    }

    public void endRide(final String passengerPhone, final String tripCost, final String tripId) {
        RequestQueue queue = Volley.newRequestQueue(context);
        String tajiUrl = "https://taji.kennedykitho.me/taji/firebase/trip/end";

        final String rType = "802";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, tajiUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "===================================== COMPLETE");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "===================================== ERROR " + error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("passenger_phone", passengerPhone);
                params.put("cost", tripCost);
                params.put("trip_id", tripId);

                Log.d(TAG, "=====================================" + params);

                return params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/x-www-form-urlencoded");
                return params;
            }
        };

        queue.add(stringRequest);
    }

}
