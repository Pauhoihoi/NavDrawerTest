package com.example.bobek.navdrawertest.LogBookModule.ascentpicker;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.bobek.navdrawertest.DataModule.DatabaseReadWrite;
import com.example.bobek.navdrawertest.LogBookModule.LogBookListArrayAdapter;
import com.example.bobek.navdrawertest.LogBookModule.ViewModelAddClimb;
import com.example.bobek.navdrawertest.LogBookModule.ViewModelLogBook;
import com.example.bobek.navdrawertest.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentAscentHolder#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentAscentHolder extends Fragment {

    private ViewModelAddClimb mViewModelAddClimb;
    private ArrayList<AscentArrayListItem> ascentArrayList;
    AscentArrayAdapter adapter;
    Context mContext;
    ListView listView;

    public FragmentAscentHolder() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModelAddClimb = ViewModelProviders.of(getActivity()).get(ViewModelAddClimb.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview_parent_list, container, false);
        mContext = getActivity();
        mapViews(view);
        refreshData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mViewModelAddClimb.setOutputAscent(adapter.getItem(position).getId());
                mViewModelAddClimb.setOutputStringAscentType(adapter.getItem(position).getAscentType());
                exitFragment();
            }
        });

        return view;
    }

    private void mapViews(View view) {
        listView=view.findViewById(R.id.parent_listview);
    }

    public void refreshData() {
        ascentArrayList = DatabaseReadWrite.getAscentArrayList(mContext);
        adapter = new AscentArrayAdapter(mContext, ascentArrayList);
        listView.setAdapter(adapter);
    }

    protected void exitFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            Log.i("MainActivity", "nothing on backstack, calling super");
            //super.onBackPressed();
        }
    }

}
