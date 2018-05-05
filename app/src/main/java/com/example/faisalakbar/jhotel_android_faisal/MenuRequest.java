package com.example.faisalakbar.jhotel_android_faisal;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;


public class MenuRequest extends StringRequest{
    private static final String Regis_URL = "http://192.168.137.161:8080/vacantrooms";

    public MenuRequest(Response.Listener<String> listener) {
        super(Method.GET,Regis_URL,listener,null);
    }

}
