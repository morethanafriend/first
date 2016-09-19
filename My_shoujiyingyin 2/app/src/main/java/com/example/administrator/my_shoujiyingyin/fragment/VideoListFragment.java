package com.example.administrator.my_shoujiyingyin.fragment;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.activity.VideoPlayerActivity;
import com.example.administrator.my_shoujiyingyin.adapter.VideoListAdapter;
import com.example.administrator.my_shoujiyingyin.bean.VideoItem;
import com.example.administrator.my_shoujiyingyin.interfaces.Keys;

import java.util.ArrayList;

public class VideoListFragment extends Fragment {
   private View rootView;
    private ListView mListView;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup parent,  Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_media_list, null);
        initData();
        initView();
        initListener();
        return mListView;


    }

    private void initListener() {
        mListView.setOnItemClickListener(new
                 AdapterView.OnItemClickListener() {
               @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 Cursor cursor=(Cursor)parent.getItemAtPosition(position);
                 ArrayList<VideoItem> videoItems=getVideoList(cursor);
                 enterVideoPlayerActivity(videoItems,position);
                        }
                          });
    }

    private void enterVideoPlayerActivity(ArrayList<VideoItem> videoItems, int position) {
        Intent intent=new Intent();
        intent.setClass(getActivity(),VideoPlayerActivity.class);
        intent.putExtra(Keys.ITEM_LIST,videoItems);
        intent.putExtra(Keys.CURRENT_POSITION,position);
        startActivity(intent);
    }

    private ArrayList<VideoItem> getVideoList(Cursor cursor) {
        if (cursor==null){
            return null;
        }
        ArrayList<VideoItem> videoItems=new ArrayList<VideoItem>();
        cursor.moveToFirst();
        do {
            videoItems.add(VideoItem.fromCursor(cursor));
        }while (cursor.moveToNext());
        return videoItems;
    }

    private void initView() {
        mListView = (ListView) rootView;
    }


    private void initData() {
        AsyncQueryHandler mQueryHandler = new AsyncQueryHandler(getActivity().getContentResolver()) {


            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
              VideoListAdapter adapter = new VideoListAdapter(getActivity(), cursor);
              mListView.setAdapter(adapter);
            }

        };
        int token = 0;
        Object cookie = null;
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Video.Media._ID, MediaStore.Video.Media.TITLE, MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE, MediaStore.Video.Media.DATA
        };
        String selection = null;
        String[] selectionArgs = null;
        String orderBy = MediaStore.Video.Media.TITLE + " ASC";
        mQueryHandler.startQuery(token, cookie, uri, projection, selection, selectionArgs, orderBy);

    }

}


