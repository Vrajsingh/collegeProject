package com.iiitr.shubham.prescriptions.Volley;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Requests {

    private Context context;
    public Requests(Context ctx)
    {
        context = ctx;
    }

    public void sendStringRequest(String url, final HashMap sendMap,final HashMap headers)
    {

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Toast.makeText(context, "Sent", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return headers;
            }

            @Override
            public byte[] getBody() {
                return new JSONObject(sendMap).toString().getBytes();
            }
        };

        RequestSingleton.getInstance(context).addToRequestQueue(request);

    }

    public void getRequest(String url, HashMap headers, final JSONArrayResponseListener listener)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                listener.onResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestSingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public interface JSONArrayResponseListener
    {
        void onResponse(JSONArray array);
    }

}
