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

import com.example.administrator.my_shoujiyingyin.bean.AudioItem;
import com.example.administrator.my_shoujiyingyin.activity.AudioPlayerActivity;
import com.example.administrator.my_shoujiyingyin.interfaces.Keys;
import com.example.administrator.my_shoujiyingyin.R;
import com.example.administrator.my_shoujiyingyin.utils.Utils;
import com.example.administrator.my_shoujiyingyin.adapter.AudioListAdapter;

import java.util.ArrayList;

public class AudioListFragment extends Fragment {
    private View rootView;
    private ListView mListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_media_list, null);
        initView();
        initData();
        initListener();
        return mListView;
    }

    public void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                ArrayList<AudioItem> audioItems = getAudioItems(cursor);
                enterAudioPlayerActivity(audioItems, position);
            }
        });
    }


    protected void enterAudioPlayerActivity(ArrayList<AudioItem> audioItems, int position) {
        Intent intent = new Intent(getActivity(), AudioPlayerActivity.class);
        intent.putExtra(Keys.ITEM_LIST, audioItems);
        intent.putExtra(Keys.CURRENT_POSITION, position);
        startActivity(intent);
    }


    protected ArrayList<AudioItem> getAudioItems(Cursor cursor) {
        ArrayList<AudioItem> audioItems = new ArrayList<AudioItem>();
        cursor.moveToFirst();
        do {
            audioItems.add(AudioItem.fromCursor(cursor));
        } while (cursor.moveToNext());
        return audioItems;
    }

    private void initData() {
        AsyncQueryHandler mQueryHandler=new AsyncQueryHandler(getActivity().getContentResolver()) {

            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                Utils.printCursor(cursor);
               AudioListAdapter adapter=new AudioListAdapter(getActivity(),cursor);
                mListView.setAdapter(adapter);
            }
        };
        int token = 0;
        Object cookie = null;
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {
                MediaStore.Audio.Media._ID, MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.SIZE, MediaStore.Audio.Media.DATA, MediaStore.Audio.Media.ARTIST,
        };
        String selection = null;
        String[] selectionArgs = null;
        String orderBy = MediaStore.Audio.Media.TITLE + " ASC";
        mQueryHandler.startQuery(token, cookie, uri, projection, selection, selectionArgs, orderBy

        );
    }

    private void initView() {
        mListView=(ListView)rootView;
    }
}
