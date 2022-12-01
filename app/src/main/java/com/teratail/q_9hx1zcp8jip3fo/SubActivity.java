package com.teratail.q_9hx1zcp8jip3fo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.*;

import android.os.Bundle;

public class SubActivity extends AppCompatActivity {
  static final String EXTRA_DATA = "data";
  private static final String REQUESTKEY_EDIT = "edit";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_sub);

    FragmentManager fm = getSupportFragmentManager();
    fm.setFragmentResultListener(REQUESTKEY_EDIT, this, (requestKey, result) -> {
      finish();
    });

    if(savedInstanceState == null) {
      Data data = (Data)getIntent().getSerializableExtra(EXTRA_DATA);
      fm.beginTransaction().replace(R.id.fragment_container, DetailFragment.newInstance(REQUESTKEY_EDIT, data)).commit();
    }
  }
}