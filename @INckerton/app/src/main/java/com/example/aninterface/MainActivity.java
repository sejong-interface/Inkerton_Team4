package com.example.aninterface;

import android.content.Intent;
import android.location.Address;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.aninterface.NoticeAcitivty.EditTextData;


public class MainActivity extends AppCompatActivity {

    private View view;
    static PopupWindow popupWindow;
    static int noticeFlag = 0;
    static TextView NoticeTv;
    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NoticeTv = (TextView)findViewById(R.id.noticeTv);

        noticeListView = (ListView)findViewById(R.id.noticeListView);

        noticeList = new ArrayList<Notice>();

        adapter = new NoticeListAdapter(getApplicationContext(), noticeList);



        view = new View(this);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //액션바 설정

        //홈버튼 표시
       // ab.setDisplayHomeAsUpEnabled(true);

        noticeListView.setAdapter(adapter);

        new BackgorundTask().execute();







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


                PopupMenu popupMenu = new PopupMenu(this, view, Gravity.RIGHT);

                popupMenu.getMenuInflater().inflate(R.menu.popup, popupMenu.getMenu());

                popupMenu.setGravity(Gravity.RIGHT);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.setNotice:      // 공지사항 눌렸을때

                                /*
                                Intent NoticeIntent = new Intent(getApplicationContext(), NoticeAcitivty.class);
                                startActivity(NoticeIntent); // 공지사항 액티비티 실행
                                break;
                                */
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
                            case R.id.setCheck:
                                Intent CheckIntent = new Intent(getApplicationContext(), CheckActivity.class);
                                startActivity(CheckIntent);
                                break;

                        }

                        return false;

                    }
                });

                popupMenu.show();

                break;
            }


        }
        //or switch문을 이용하면 될듯 하다.



        return super.onOptionsItemSelected(item);



    }

    class BackgorundTask extends AsyncTask<Void, Void, String> {
        String target;
        @Override
        protected  void onPreExecute(){
            target = "http://jkey20.cafe24.com/NoticeList.php";
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
                String noticeContent, noticeName, noticeDate;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                    noticeList.add(notice);

                    adapter.notifyDataSetChanged();
                    count++;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }


    }


}
