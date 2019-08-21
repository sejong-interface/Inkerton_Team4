package com.example.aninterface;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

public class CalenderActivity extends AppCompatActivity {
  private View view;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_calender);

    Toolbar Ntoolbar = (Toolbar)findViewById(R.id.Ctoolbar);
    setSupportActionBar(Ntoolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);



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
