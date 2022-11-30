package com.teratail.q_9hx1zcp8jip3fo;

import android.os.Bundle;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.*;

public class AddSpendingFragment extends Fragment {
  private static final String ARG_DATA = "data";

  public static AddSpendingFragment newInstance(Data data) {
    AddSpendingFragment fragment = new AddSpendingFragment();
    Bundle args = new Bundle();
    args.putSerializable(ARG_DATA, data);
    fragment.setArguments(args);
    return fragment;
  }

  public AddSpendingFragment() {
    super(R.layout.fragment_add_spending);
  }
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    Data data = (Data)getArguments().getSerializable(ARG_DATA);

    TextView idView = view.findViewById(R.id.id);
    idView.setText("id=" + data.id);
    EditText text1Edit = view.findViewById(R.id.text1);
    text1Edit.setText(data.text1);
    EditText text2Edit = view.findViewById(R.id.text2);
    text2Edit.setText(data.text2);

    Button deleteButton = view.findViewById(R.id.delete);
    deleteButton.setOnClickListener(v -> {
      Bundle result = new Bundle();
      result.putSerializable(MainActivity.RESULT_DELETE_ID, data.id);
      getParentFragmentManager().setFragmentResult(MainActivity.REQUEST_UPDATE_DATA, result);
    });
    Button updateButton = view.findViewById(R.id.update);
    updateButton.setOnClickListener(v -> {
      Bundle result = new Bundle();
      Data newData = new Data(data.id, text1Edit.getText().toString(), text2Edit.getText().toString());
      result.putSerializable(MainActivity.RESULT_DATA, newData);
      getParentFragmentManager().setFragmentResult(MainActivity.REQUEST_UPDATE_DATA, result);
    });
  }
}