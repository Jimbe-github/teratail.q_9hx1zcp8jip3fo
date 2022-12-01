package com.teratail.q_9hx1zcp8jip3fo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.*;

import android.content.Intent;
import android.os.*;
import android.view.*;
import android.widget.*;

import java.util.*;

public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    MainModel mainModel = new ViewModelProvider(this).get(MainModel.class);

    ListView listView = findViewById(R.id.listView);

    getLifecycle().addObserver(new DefaultLifecycleObserver() {
      @Override
      public void onStart(@NonNull LifecycleOwner owner) {
        DefaultLifecycleObserver.super.onStart(owner);
        mainModel.reload();
        mainModel.getDataList().observe(MainActivity.this, data -> {
          listView.setAdapter(new DataAdapter(data));
        });
      }
    });

    listView.setOnItemClickListener((parent, view, position, id) -> {
      Intent intent = new Intent(this, SubActivity.class);
      intent.putExtra(SubActivity.EXTRA_DATA, (Data)listView.getItemAtPosition(position));
      startActivity(intent);
    });
  }
}

class DataAdapter extends BaseAdapter {
  private static class ViewHolder {
    final TextView text1View, text2View;
    ViewHolder(View itemView) {
      text1View = itemView.findViewById(android.R.id.text1);
      text2View = itemView.findViewById(android.R.id.text2);
    }
  }

  private List<Data> list;
  DataAdapter(List<Data> list) {
    this.list = list;
  }
  @Override
  public int getCount() {
    return list.size();
  }
  @Override
  public Object getItem(int position) {
    return list.get(position);
  }
  @Override
  public long getItemId(int position) {
    return list.get(position).id;
  }
  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    if(convertView == null) {
      convertView = LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
      convertView.setTag(new ViewHolder(convertView));
    }
    ViewHolder vh = (ViewHolder)convertView.getTag();
    vh.text1View.setText(list.get(position).text1);
    vh.text2View.setText(list.get(position).text2);
    return convertView;
  }
}