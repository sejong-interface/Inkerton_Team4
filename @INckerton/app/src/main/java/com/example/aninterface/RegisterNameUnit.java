package com.example.aninterface;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterNameUnit extends StringRequest {
  final static  private String URL = "http://jkey20.cafe24.com/Check.php";
  private Map<String, String> parameters;

  public  RegisterNameUnit(String name, String unit, Response.Listener<String> listener){
    super(Request.Method.POST, URL, listener, null);
    parameters = new HashMap<>();
    parameters.put("name", name);
    parameters.put("unit", unit);

  }

  @Override
  public Map<String, String> getParams(){
    return parameters;
  }
}
