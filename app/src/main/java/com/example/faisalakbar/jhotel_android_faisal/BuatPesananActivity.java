package com.example.faisalakbar.jhotel_android_faisal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.math.BigDecimal;

public class BuatPesananActivity extends AppCompatActivity
{
    private int currentUserId, banyakHari, idHotel;
    private double tariff;
    private String roomNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_pesanan);

        final TextView room_number = findViewById(R.id.room_number);
        final TextView tariffView = findViewById(R.id.tariff);
        final EditText durasi = findViewById(R.id.durasi_hari);
        final TextView total = findViewById(R.id.total_biaya);
        final Button hitungButton = findViewById(R.id.hitung);
        final Button pesanButton = findViewById(R.id.pesan);

        pesanButton.setVisibility(View.INVISIBLE);
        room_number.setText(roomNumber);
        tariffView.setText(String.valueOf(this.tariff));
        total.setText("0");

        hitungButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                banyakHari = Integer.valueOf(durasi.getText().toString());

                BigDecimal bd1 = new BigDecimal(tariff);
                BigDecimal bd2 = new BigDecimal(Double.valueOf(String.valueOf(banyakHari)));
                BigDecimal result = bd1.multiply(bd2);
                total.setText(String.valueOf(result.doubleValue()));
            }
        });
    }
}

