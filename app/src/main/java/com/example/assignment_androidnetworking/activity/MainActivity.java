package com.example.assignment_androidnetworking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.assignment_androidnetworking.EndlessRecyclerViewScrollListener;
import com.example.assignment_androidnetworking.R;
import com.example.assignment_androidnetworking.adapter.MyAdapter;
import com.example.assignment_androidnetworking.model.Example;
import com.example.assignment_androidnetworking.model.PhotoFavorite;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvList;
    private static final String FULL_EXTRAS = "views,media,path_alias,url_sq,url_t,url_s,url_q,url_m,url_n,url_z,url_c,url_l,url_o";
    private static final String USER_ID = "186985306@N02";
    private static final String KEY_TOKEN1 = "1628b2d0ed5fb84aa200450cd084886a";
    private static final String GET_FAVORITE = "flickr.favorites.getList";
    int pages = 1;
    List<PhotoFavorite> photoFavoriteList;

    MyAdapter myAdapter;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    Example exampleFavorite;
    EndlessRecyclerViewScrollListener endlessRecyclerViewScrollListener;
    SwipeRefreshLayout srlRefesh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidNetworking.initialize(getApplicationContext());
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        rvList = findViewById(R.id.rvList);
        srlRefesh = findViewById(R.id.srlRefesh);

        srlRefesh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myAdapter.notifyDataSetChanged();
                srlRefesh.setRefreshing(false);
            }
        });
        photoFavoriteList = new ArrayList<>();
        myAdapter = new MyAdapter(photoFavoriteList, MainActivity.this);
        rvList.setHasFixedSize(true);
        rvList.setAdapter(myAdapter);
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        rvList.setLayoutManager(staggeredGridLayoutManager);
        Log.e("11", 3333 + "");

        AndroidFast();
        // thực thi lệnh loadmore khi kéo xuống
        endlessRecyclerViewScrollListener = new EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                pages++;
                Log.e("11", 11111 + "");
                AndroidFast();

            }
        };
        rvList.addOnScrollListener(endlessRecyclerViewScrollListener);
    }


    public void AndroidFast() {
        AndroidNetworking.get("https://www.flickr.com/services/rest/")
                .addQueryParameter("method", GET_FAVORITE)
                .addQueryParameter("api_key", KEY_TOKEN1)
                .addQueryParameter("user_id", USER_ID)
                .addQueryParameter("extras", FULL_EXTRAS)
                .addQueryParameter("per_page", "10")
                .addQueryParameter("page", String.valueOf(pages))
                .addQueryParameter("format", "json")
                .addQueryParameter("nojsoncallback", "1")
                .setTag(this)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(final JSONObject response) {
                        exampleFavorite = new Gson().fromJson(response.toString(), Example.class);
                        // thêm toàn bộ dữ liệu vào list
                        photoFavoriteList.addAll(exampleFavorite.getPhotosFavorite().getPhotoFavorite());
                        // thông báo cập nhật lại một vị trí được thêm mới
                        myAdapter.notifyItemInserted(photoFavoriteList.size());
                        PictureActivity.photoFavoriteList=photoFavoriteList;
                        Log.e("11", 2222 + "");
                        //nếu đến page cuối thì không load nữa
                        if (exampleFavorite.getPhotosFavorite().getPhotoFavorite().size() == exampleFavorite.getPhotosFavorite().getPhotoFavorite().size() - 1) {
                            rvList.addOnScrollListener(null);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        Log.e("Lỗi", error.getMessage());
                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        MenuItem search = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                myAdapter.getFilter().filter(s);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.galleries:
                startActivity(new Intent(MainActivity.this, GalleriesActivity.class));
                overridePendingTransition(R.xml.slide_in_from_right, R.xml.slide_out_to_left);
                return true;
            case R.id.home:
                startActivity(new Intent(MainActivity.this, MainActivity.class));
                overridePendingTransition(R.xml.slide_in_from_right, R.xml.slide_out_to_left);
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}