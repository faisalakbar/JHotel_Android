package com.example.faisalakbar.jhotel_android_faisal;

public class Lokasi {
    private double x_coord;
    private double y_coord;
    private String deskripsiLokasi;

    public Lokasi(double x_coord, double y_coord, String deskripsi) {
        this.x_coord = x_coord;
        this.y_coord = y_coord;
        this.deskripsiLokasi = deskripsi;
    }

    public double getX_coord() {
        return x_coord;
    }

    public void setX_coord(double x_coord) {
        this.x_coord = x_coord;
    }

    public double getY_coord() {
        return y_coord;
    }

    public void setY_coord(double y_coord) {
        this.y_coord = y_coord;
    }

    public String getDeskripsi() {
        return deskripsiLokasi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsiLokasi = deskripsi;
    }
}
