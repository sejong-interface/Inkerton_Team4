package com.example.aninterface;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CheckListAdapter extends BaseAdapter {
  private Context context;
  private List<Check> checkList;

  public CheckListAdapter(Context context, List<Check> checkList) {
    this.context = context;
    this.checkList = checkList;
  }

  @Override
  public int getCount() {
    return checkList.size();
  }

  @Override
  public Object getItem(int i) {
    return checkList.get(i);
  }

  @Override
  public long getItemId(int i) {
    return i;
  }

  @Override
  public View getView(int i, View convertView, ViewGroup parent) {
    View v = View.inflate(context, R.layout.check, null);
    TextView unitText = (TextView)v.findViewById(R.id.unitText);
    TextView nameText = (TextView)v.findViewById(R.id.nameText);

    unitText.setText(checkList.get(i).getUnit());
    nameText.setText(checkList.get(i).getName());


    v.setTag(checkList.get(i).getName());
    return v;
  }


}
