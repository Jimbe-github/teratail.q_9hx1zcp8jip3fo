package com.teratail.q_9hx1zcp8jip3fo;

import android.os.Bundle;

import androidx.annotation.*;
import androidx.fragment.app.*;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.*;

public class DetailFragment extends Fragment {
  private static final String ARG_REQUESTKEY = "requestKey";
  private static final String ARG_DATA = "data";

  public static DetailFragment newInstance(String requestKey, Data data) {
    DetailFragment fragment = new DetailFragment();
    Bundle args = new Bundle();
    args.putString(ARG_REQUESTKEY, requestKey);
    args.putSerializable(ARG_DATA, data);
    fragment.setArguments(args);
    return fragment;
  }

  public DetailFragment() {
    super(R.layout.fragment_detail);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    String requestKey = getArguments().getString(ARG_REQUESTKEY);
    Data data = (Data)getArguments().getSerializable(ARG_DATA);

    TextView idView = view.findViewById(R.id.id);
    idView.setText("id=" + data.id);
    EditText text1Edit = view.findViewById(R.id.text1);
    text1Edit.setText(data.text1);
    EditText text2Edit = view.findViewById(R.id.text2);
    text2Edit.setText(data.text2);

    MainModel mainModel = new ViewModelProvider(requireActivity()).get(MainModel.class);

    FragmentManager fm = getParentFragmentManager();

    Button deleteButton = view.findViewById(R.id.delete);
    deleteButton.setOnClickListener(v -> {
      mainModel.delete(data.id);
      fm.setFragmentResult(requestKey, new Bundle());
    });

    Button updateButton = view.findViewById(R.id.update);
    updateButton.setOnClickListener(v -> {
      mainModel.update(new Data(data.id, text1Edit.getText().toString(), text2Edit.getText().toString()));
      fm.setFragmentResult(requestKey, new Bundle());
    });
  }
}