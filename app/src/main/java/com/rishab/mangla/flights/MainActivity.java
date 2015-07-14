package com.rishab.mangla.flights;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.android.volley.toolbox.JsonObjectRequest;
import com.rishab.mangla.flights.adater.CustomListAdapter;
import com.rishab.mangla.flights.app.AppController;
import com.rishab.mangla.flights.model.Flight;

public class MainActivity extends Activity {
    // Log tag
    private static final String TAG = MainActivity.class.getSimpleName();

    // Flights json url
    private static final String url = "http://blog.ixigo.com/sampleflightdata.json";
    private ProgressDialog pDialog;
    private List<Flight> flightList = new ArrayList<Flight>();
    private List<String> airlineList = new ArrayList<String>();
    private Map<String, String> airlineMap = new HashMap<String, String>();

    private ListView listView;
    private CustomListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, flightList);
        listView.setAdapter(adapter);

        airlineList.add("SJ");
        airlineList.add("AI");
        airlineList.add("G8");
        airlineList.add("JA");
        airlineList.add("IN");

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest flightReq = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {

                        try {
                            Log.i("rishab", "onResponse " + jsonObject.toString());
                            hidePDialog();

                            JSONObject airlineMapObj = jsonObject.getJSONObject("airlineMap");
                            for (String filter : airlineList) {
                                airlineMap.put(filter, airlineMapObj.getString(filter));
                            }

                            JSONObject airportMapObj = jsonObject.getJSONObject("airportMap");
                            JSONArray flightsData = jsonObject.getJSONArray("flightsData");


                            for (int i = 0; i < flightsData.length(); ++i) {
                                Flight flight = new Flight();

                                JSONObject obj = flightsData.getJSONObject(i);
                                flight.setFlight(obj.getString("airlineCode") + " : " + airlineMap.get(obj.getString("airlineCode")));
                                flight.setFlightClass(obj.getString("class"));
                                flight.setSrc(obj.getString("originCode"));
                                flight.setDes(obj.getString("destinationCode"));
                                flight.setLandingTime(obj.getLong("landingTime"));
                                flight.setTakeOffTime(obj.getLong("takeoffTime"));
                                flight.setPrice(obj.getInt("price"));

                                // adding flight to flights array
                                flightList.add(flight);
                            }


                            // notifying list adapter about data changes
//						// so that it renders the list view with updated data
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                hidePDialog();

            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(flightReq);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_flights, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_sort_price:
                Collections.sort(flightList, new Comparator<Flight>() {
                    @Override
                    public int compare(Flight lhs, Flight rhs) {
                        return lhs.getPrice() - rhs.getPrice();
                    }
                });
                adapter.notifyDataSetChanged();
                break;

            case R.id.action_sort_takeOff:
                Collections.sort(flightList, new Comparator<Flight>() {
                    @Override
                    public int compare(Flight lhs, Flight rhs) {
                        return (int) (lhs.getTakeOffMilis() - rhs.getTakeOffMilis());
                    }
                });
                adapter.notifyDataSetChanged();
                break;
        }
        return super.onOptionsItemSelected(item);

    }
}
