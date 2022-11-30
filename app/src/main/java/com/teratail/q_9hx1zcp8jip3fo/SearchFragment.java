package com.teratail.q_9hx1zcp8jip3fo;

import android.os.Bundle;
import android.view.*;
import android.widget.*;

import androidx.annotation.*;
import androidx.fragment.app.Fragment;

import java.util.*;

public class SearchFragment extends Fragment {
  private static final String ARG_LIST = "list";

  public static SearchFragment newInstance(ArrayList<Data> list) {
    SearchFragment fragment = new SearchFragment();
    Bundle args = new Bundle();
    args.putSerializable(ARG_LIST, list);
    fragment.setArguments(args);
    return fragment;
  }
  public SearchFragment() {
    super(R.layout.fragment_search);
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    ArrayList<Data> list = (ArrayList)getArguments().getSerializable(ARG_LIST);

    ListView listView = view.findViewById(R.id.listView);
    listView.setAdapter(new DataAdapter(list));
    listView.setOnItemClickListener((parent, view1, position, id) -> {
      Bundle result = new Bundle();
      result.putSerializable(MainActivity.RESULT_DATA, (Data)listView.getItemAtPosition(position));
      getParentFragmentManager().setFragmentResult(MainActivity.REQUEST_SELETED_DATA, result);
    });
  }

  private static class DataAdapter extends BaseAdapter {
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
}