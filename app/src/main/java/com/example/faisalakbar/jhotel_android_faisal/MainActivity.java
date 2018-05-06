package com.example.faisalakbar.jhotel_android_faisal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
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
    private ArrayList<Hotel> listHotel = new ArrayList<>();
    private ArrayList<Room> listRoom = new ArrayList<>();
    private HashMap<Hotel, ArrayList<Room>> childMapping = new HashMap<>();
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    HashMap<String, Hotel> hotelHashMap = new HashMap<>();
    HashMap<String, ArrayList<Room>> roomsMap = new HashMap<>();
    private int currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent menuIntent = getIntent();
        currentUserId = menuIntent.getIntExtra("id",0);
        expListView = findViewById(R.id.lvExp);

        refreshList();

        listAdapter = new MenuListAdapter(this, listHotel, childMapping);
        expListView.setAdapter(listAdapter);
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View view, int groupPosition, int childPosition, long id) {
                Room selected = childMapping.get(listHotel.get(groupPosition)).get(childPosition);
                Hotel hotel = listHotel.get(groupPosition);
                String nomor_kamar = selected.getRoomNumber();
                Intent childIntent = new Intent(MainActivity.this, BuatPesananActivity.class);
                childIntent.putExtra("id_customer", currentUserId);
                childIntent.putExtra("nomor_kamar", nomor_kamar);
                childIntent.putExtra("id_hotel", hotel.getId());
                startActivity(childIntent);
                return false;
            }
        });

        final Button pesanan = findViewById(R.id.pesanan);
        pesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SelesaiPesananActivity.class);
                i.putExtra("id",currentUserId);
                startActivity(i);
            }
        });
    }

    public void refreshList() {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonResponse = new JSONArray(response);
                    for (int i = 0; i < jsonResponse.length(); i++) {
                        JSONObject e = jsonResponse.getJSONObject(i).getJSONObject("hotel");
                        JSONObject lokasi = e.getJSONObject("lokasi");
                        JSONObject room = jsonResponse.getJSONObject(i);
                        Hotel hotel = new Hotel(e.getInt("id"),e.getString("nama"),
                                new Lokasi(lokasi.getDouble("x"), lokasi.getDouble("y"), lokasi.getString("deskripsi")),
                                e.getInt("bintang"));
                        hotelHashMap.put(hotel.getNama(), hotel);
                        Room room1 = new Room(room.getString("nomorKamar"), room.getString("statusKamar"),
                                room.getDouble("dailyTariff"), room.getString("tipeKamar"));

                        if (!roomsMap.containsKey(hotel.getNama())) {
                            ArrayList<Room> rooms = new ArrayList<>();
                            rooms.add(room1);
                            roomsMap.put(hotel.getNama(), rooms);
                        } else {
                            ArrayList<Room> rooms = roomsMap.get(hotel.getNama());
                            rooms.add(room1);
                            roomsMap.put(hotel.getNama(), rooms);
                        }
                    }

                    for (String key : hotelHashMap.keySet()) {
                        listHotel.add(hotelHashMap.get(key));

                        childMapping.put(hotelHashMap.get(key), roomsMap.get(key));
                    }
                    listAdapter = new MenuListAdapter(MainActivity.this, listHotel, childMapping);
                    expListView.setAdapter(listAdapter);

                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
            }
        };
        MenuRequest menuRequest = new MenuRequest(responseListener);
        RequestQueue queue = newRequestQueue(MainActivity.this);
        queue.add(menuRequest);
    }
}