package com.example.aninterface;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.aninterface.MainActivity.noticeFlag;

public class CheckActivity extends AppCompatActivity {
  private AlertDialog dialog;
  private TextView checkedTv;

  private ListView checkListView;
  private CheckListAdapter checkadapter;
  private List<Check> checkList;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_check);
    Button CheckBtn = (Button)findViewById(R.id.CheckBt);
    final EditText nameET = (EditText)findViewById(R.id.nameEditText);
    final EditText unitET = (EditText)findViewById(R.id.unitEditText);

    checkListView = (ListView)findViewById(R.id.CheckedListView);

    checkList = new ArrayList<Check>();

    checkadapter = new CheckListAdapter(getApplicationContext(), checkList);

    checkListView.setAdapter(checkadapter);




    CheckBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String name = nameET.getText().toString();
        String unit = unitET.getText().toString();

        Response.Listener<String> responseListener = new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            try{
              /*
              JSONObject jsonResponse = new JSONObject(response);
              boolean success = jsonResponse.getBoolean("success");
              if(success){
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckActivity.this);
                dialog = builder.setMessage("정보 전송에 성공했습니다.")
                        .setPositiveButton("확인", null)
                        .create();
                dialog.show();
                finish();
              } else{
                AlertDialog.Builder builder = new AlertDialog.Builder(CheckActivity.this);
                dialog = builder.setMessage("정보 전송에 실패했습니다.")
                        .setNegativeButton("확인", null)
                        .create();
                dialog.show();

              }
              */
            }catch (Exception e){
              e.printStackTrace();
            }
          }
        };

        RegisterNameUnit registerNameUnit = new RegisterNameUnit(name, unit, responseListener);
        RequestQueue queue = Volley.newRequestQueue(CheckActivity.this);
        queue.add(registerNameUnit);

        AlertDialog.Builder CheckedBuilder = new AlertDialog.Builder(CheckActivity.this);
        CheckedBuilder.setTitle("정보를 보내시겠습니까?");

        CheckedBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {


            Intent ReturnCheckIntent = new Intent(getApplicationContext(), CheckActivity.class);

            startActivity(ReturnCheckIntent);
          }
        }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
          }
        });

        CheckedBuilder.show();

      }
    });


    new BackgorundTask2().execute();

  }

  @Override
  protected void onStop() {
    super.onStop();
    if (dialog != null) {
      dialog.dismiss();
      dialog = null;
    }

  }

  class BackgorundTask2 extends AsyncTask<Void, Void, String> {
    String target;
    @Override
    protected  void onPreExecute(){
      target = "http://jkey20.cafe24.com/CheckBack.php";
    }

    @Override
    protected String doInBackground(Void... voids) {

      try{
        URL url = new URL(target);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String temp;
        StringBuilder stringBuilder = new StringBuilder();
        while((temp = bufferedReader.readLine()) != null){
          stringBuilder.append(temp + "\n");
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return stringBuilder.toString().trim();



      }catch (Exception e){
        e.printStackTrace();
      }

      return null;
    }
    @Override
    public void onProgressUpdate(Void... values){
      super.onProgressUpdate();
    }

    @Override
    public void onPostExecute(String result){
      try{


        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("response");
        int count =0;
        String Checkname, Checkunit;
        while(count < jsonArray.length()){
          JSONObject object = jsonArray.getJSONObject(count);
          Checkname = object.getString("name");
          Checkunit = object.getString("unit");

          Check check = new Check(Checkname, Checkunit);
          checkList.add(check);

          checkadapter.notifyDataSetChanged();
          count++;
        }

      }catch (Exception e){
        e.printStackTrace();
      }
    }


  }




}

