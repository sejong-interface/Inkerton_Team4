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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

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

import static com.example.aninterface.MainActivity.noticeFlag;
import static com.example.aninterface.MainActivity.NoticeTv;


public class NoticeAcitivty extends AppCompatActivity {
  static String EditTextData;
  private View view;
  static EditText NoticeEdit;
  private String notice;
  private boolean validate = false;
  private AlertDialog dialog;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_notice_acitivty);




    NoticeEdit = (EditText)findViewById(R.id.NoticeEditText);
    Button NoticeEditBtn = (Button)findViewById(R.id.NoticeEditButton);


    Toolbar Ntoolbar = (Toolbar)findViewById(R.id.Ntoolbar);
    setSupportActionBar(Ntoolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);

    NoticeEditBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        Dialog();

      }
    });

  }
  public void Dialog(){

    AlertDialog.Builder NoticeCheckBuilder = new AlertDialog.Builder(this);
    NoticeCheckBuilder.setTitle("공지사항을 변경하시겠습니까?");

    NoticeCheckBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {


        Intent ReturnMainIntent = new Intent(getApplicationContext(), MainActivity.class);
        noticeFlag = 1;
        startActivity(ReturnMainIntent);
      }
    }).setNegativeButton("취소", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });

    NoticeCheckBuilder.show();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu){
    getMenuInflater().inflate(R.menu.menu, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item){
    switch(item.getItemId()) {
      case android.R.id.home : {
        // 팝업메뉴 실행
        Toast.makeText(this, "탭 클릭", Toast.LENGTH_SHORT).show();


        PopupMenu popupMenu = new PopupMenu(this, view, Gravity.RIGHT);

        popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());

        popupMenu.setGravity(Gravity.RIGHT);
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
          @Override
          public boolean onMenuItemClick(MenuItem item) {
            switch (item.getItemId()) {
              case R.id.setNotice:      // 공지사항 눌렸을때
                Intent NoticeIntent = new Intent(getApplicationContext(), NoticeAcitivty.class);
                startActivity(NoticeIntent); // 공지사항 액티비티 실행
                break;
              /*
              case R.id.setCalender: // 행사 눌렀을 때
                Intent CalenderIntent = new Intent(getApplicationContext(), CalenderActivity.class);
                startActivity(CalenderIntent);
                break;
              case R.id.setArticle: // 물품정리 눌렀을 때
                Intent ArticleIntent = new Intent(getApplicationContext(), ArticleActivity.class);
                startActivity(ArticleIntent);
                break;
               */
            }

            return false;

          }
        });

        popupMenu.show();

        break;
      }


    }



    return super.onOptionsItemSelected(item);



  }




}
