package com.teratail.q_9hx1zcp8jip3fo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.*;

import android.os.*;

import java.io.Serializable;
import java.util.*;

public class MainActivity extends AppCompatActivity {
  static final String REQUEST_SELETED_DATA = "selectedData";
  static final String REQUEST_UPDATE_DATA = "updateData";
  static final String RESULT_DATA = "data";
  static final String RESULT_DELETE_ID = "deleteId";

  ArrayList<Data> dataList = new ArrayList<>(); //Serializable

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    FragmentManager fm = getSupportFragmentManager();
    //画面遷移用リスナ群
    //SearchFragment → AddSpendingFragment
    fm.setFragmentResultListener(REQUEST_SELETED_DATA, this, (requestKey, result) -> {
      Data data = (Data)result.get(RESULT_DATA);
      fm.beginTransaction()
              .replace(R.id.main_container, AddSpendingFragment.newInstance(data))
              .addToBackStack(null)
              .commit();
    });
    //AddSpendingFragment → SearchFragment
    fm.setFragmentResultListener(REQUEST_UPDATE_DATA, this, (requestKey, result) -> {
      Data data = (Data)result.get(RESULT_DATA);
      if(data != null) {
        update(data);
      } else if(result.containsKey(RESULT_DELETE_ID)) {
        delete(result.getLong(RESULT_DELETE_ID));
      }
      fm.beginTransaction()
              .replace(R.id.main_container, SearchFragment.newInstance(dataList))
              .commit();
    });

    //初期表示
    if(savedInstanceState == null) {
      //テストデータ
      dataList.add(new Data(1, "AAA", "あああ"));
      dataList.add(new Data(2, "BBB", "いいい"));
      dataList.add(new Data(3, "CCC", "ううう"));
      dataList.add(new Data(4, "DDD", "えええ"));
      dataList.add(new Data(5, "EEE", "おおお"));

      fm.beginTransaction()
              .replace(R.id.main_container, SearchFragment.newInstance(dataList))
              .commit();
    }
  }
  private void update(Data data) {
    for(int i = 0; i < dataList.size(); i++) {
      if(dataList.get(i).id == data.id) {
        dataList.set(i, data);
        return;
      }
    }
  }
  private void delete(long id) {
    for(int i=0; i<dataList.size(); i++) {
      if(dataList.get(i).id == id) {
        dataList.remove(i);
        return;
      }
    }
  }
}

class Data implements Serializable {
  final long id;
  final String text1, text2;
  Data(long id, String text1, String text2) {
    this.id = id;
    this.text1 = text1;
    this.text2 = text2;
  }
}