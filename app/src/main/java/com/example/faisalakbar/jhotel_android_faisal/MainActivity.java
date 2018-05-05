package com.example.faisalakbar.jhotel_android_faisal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import static com.android.volley.toolbox.Volley.newRequestQueue;


public class MainActivity extends AppCompatActivity {

    MenuListAdapter listAdapter;
    ExpandableListView expListView;

    private ArrayList<Hotel> listHotel = new ArrayList<>();
    private ArrayList<Room> listRoom = new ArrayList<>();
    private HashMap<Hotel, ArrayList<Room>> childMapping = new HashMap<>();

    HashMap<String, Hotel> hotelHashMap = new HashMap<>();
    HashMap<String, ArrayList<Room>> roomsMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.expanded_menu);

        // preparing list data
        refreshlist();

        //listAdapter = new MenuListAdapter(this, listHotel, childMapping);

        // setting list adapter
        //expListView.setAdapter(listAdapter);

    }

    public void refreshlist() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for(int i=0 ; i<jsonResponse.length() ; i++) {
                        JSONObject temp = jsonResponse.getJSONObject(i).getJSONObject("hotel");
                        JSONObject lokasi = temp.getJSONObject("lokasi");
                        JSONObject room_temp = jsonResponse.getJSONObject(i);

                        Hotel h = new Hotel(temp.getInt("id"), temp.getString("nama"),
                                new Lokasi(lokasi.getDouble("x"), lokasi.getDouble("y"), lokasi.getString("deskripsi")),
                                temp.getInt("bintang"));
                        hotelHashMap.put(h.getNama(), h);
                        Room room = new Room(room_temp.getString("nomorKamar"), room_temp.getString("statusKamar"),
                                room_temp.getDouble("dailyTariff"), room_temp.getString("tipeKamar"));
                        if(!roomsMap.containsKey(h.getNama())) {
                            //ArrayList<Room> listRoom = new ArrayList<>();
                            listRoom.add(room);
                            roomsMap.put(h.getNama(), listRoom);
                        } else {
                            //ArrayList<Room> listRoom = roomsMap.get(h.getNama());
                            listRoom.add(room);
                            roomsMap.put(h.getNama(), listRoom);
                        }
                    }

                    for(String key : hotelHashMap.keySet()) {
                        listHotel.add(hotelHashMap.get(key));
                        childMapping.put(hotelHashMap.get(key), roomsMap.get(key));
                    }

                    listAdapter = new MenuListAdapter(MainActivity.this, listHotel, childMapping);
                    expListView.setAdapter(listAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };

        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}




