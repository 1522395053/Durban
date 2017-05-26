/*
 * Copyright © Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yanzhenjie.durban.sample;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.util.DisplayUtils;
import com.yanzhenjie.album.widget.recyclerview.AlbumVerticalGirdDecoration;
import com.yanzhenjie.durban.Durban;

import java.util.ArrayList;

/**
 * Created by Yan Zhenjie on 2017/5/22.
 */
public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    GridAdapter mGridAdapter;
    ArrayList<String> mImageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayUtils.initScreen(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.decoration_white, null);
        mRecyclerView.addItemDecoration(new AlbumVerticalGirdDecoration(drawable));

        assert drawable != null;
        int itemSize = (DisplayUtils.screenWidth - (drawable.getIntrinsicWidth() * 4)) / 3;
        mGridAdapter = new GridAdapter(this, itemSize);
        mRecyclerView.setAdapter(mGridAdapter);

        mImageList = new ArrayList<>();
    }

    /**
     * Select images.
     */
    private void selectImage() {
        Album.album(this)
                .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .toolBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .navigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryBlack))
                .selectCount(3)
                .requestCode(100)
                .start();
    }

    /**
     * Crop images.
     */
    private void cropImage(ArrayList<String> imagePathList) {
        String cropDirectory = Utils.getAppRootPath(this).getAbsolutePath();

        Log.i("CropSample", "Save directory: " + cropDirectory);

        Durban.with(MainActivity.this)
                .statusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                .toolBarColor(ContextCompat.getColor(this, R.color.colorPrimary))
                .navigationBarColor(ContextCompat.getColor(this, R.color.colorPrimaryBlack))
                // Image path list/array.
                .inputImagePaths(imagePathList)
                // Image output directory.
                .outputDirectory(cropDirectory)
                // Image size limit.
                .maxWidthHeight(500, 500)
                // Aspect ratio.
                .aspectRatio(1, 1)
                // Output format: JPEG, PNG.
                .compressFormat(Durban.COMPRESS_JPEG)
                // Compress quality, see Bitmap#compress(Bitmap.CompressFormat, int, OutputStream)
                .compressQuality(90)
                // Gesture: ROTATE, SCALE, ALL, NONE.
                .gesture(Durban.GESTURE_ALL)
                .requestCode(200)
                .start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case 100: {
                // Analyze the selection results of the album.
                ArrayList<String> imagePathList = Album.parseResult(data);
                cropImage(imagePathList);
                break;
            }
            case 200: {
                // Analyze the list of paths after cropping.
                mImageList = Durban.parseResult(data);
                mGridAdapter.notifyDataSetChanged(mImageList);
                break;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_album: {
                selectImage();
                break;
            }
        }
        return true;
    }
}