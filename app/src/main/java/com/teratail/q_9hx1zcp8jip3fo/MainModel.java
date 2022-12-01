package com.teratail.q_9hx1zcp8jip3fo;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.*;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.util.*;

public class MainModel extends AndroidViewModel {
  private final String FILENAME = "datas.json";

  private MutableLiveData<List<Data>> dataListLiveData = new MutableLiveData<>(null);

  public MainModel(@NonNull Application application) {
    super(application);
    try {
      readFile();
    } catch(FileNotFoundException e) {
      //初期値でファイルも作成
      ArrayList<Data> dataList = new ArrayList<>();
      dataList.add(new Data(1, "AAA", "あああ"));
      dataList.add(new Data(2, "BBB", "いいい"));
      dataList.add(new Data(3, "CCC", "ううう"));
      dataList.add(new Data(4, "DDD", "えええ"));
      dataList.add(new Data(5, "EEE", "おおお"));
      dataListLiveData.setValue(dataList);
      writeFile();
    }
  }
  private void readFile() throws FileNotFoundException {
    try(Reader r = new InputStreamReader(getApplication().openFileInput(FILENAME))) {
      dataListLiveData.setValue(new Gson().fromJson(r, new TypeToken<List<Data>>(){}.getType()));
    } catch(FileNotFoundException e) {
      throw e;
    } catch(IOException e) {
      e.printStackTrace();
    }
  }
  private void writeFile() {
    try(Writer w = new PrintWriter(getApplication().openFileOutput(FILENAME, Context.MODE_PRIVATE))) {
      w.write(new Gson().toJson(dataListLiveData.getValue()));
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  LiveData<List<Data>> getDataList() { return dataListLiveData; }

  void delete(long id) {
    if(deleteFromList(id)) writeFile();
  }
  private boolean deleteFromList(long id) {
    List<Data> list = dataListLiveData.getValue();
    for(int i=0; i<list.size(); i++) {
      if(list.get(i).id == id) {
        list.remove(i);
        dataListLiveData.setValue(list); //一応オブザーバへ通知
        return true; //更新有り
      }
    }
    return false;
  }

  void update(Data data) {
    if(updateListElement(data)) writeFile();
  }
  private boolean updateListElement(Data data) {
    List<Data> list = dataListLiveData.getValue();
    for(int i=0; i<list.size(); i++) {
      if(list.get(i).id == data.id) {
        list.set(i, data);
        dataListLiveData.setValue(list); //一応オブザーバへ通知
        return true; //更新有り
      }
    }
    return false;
  }

  void reload() {
    try {
      readFile();
    } catch(FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}