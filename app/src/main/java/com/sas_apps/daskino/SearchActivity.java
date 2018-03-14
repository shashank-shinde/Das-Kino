package com.sas_apps.daskino;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sas_apps.daskino.adaptor.ListAdaptor;
import com.sas_apps.daskino.adaptor.OnItemClicked;
import com.sas_apps.daskino.api.TMDbApi;
import com.sas_apps.daskino.model.Movie;
import com.sas_apps.daskino.model.MovieList;
import com.sas_apps.daskino.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity implements TextView.OnEditorActionListener,
        TextWatcher,OnItemClicked, Callback<MovieList> {

    private static final String TAG = "SearchActivity";

    ListAdaptor adaptor;
    LinearLayoutManager layoutManager;


    private Retrofit retrofit = null;
    private TMDbApi tmDbApil;
    private Call<MovieList> movieListCall;
    private List<Movie> movieList = new ArrayList<>();

    @BindView(R.id.image_back)
    ImageView imageBack;
    @BindView(R.id.edit_search)
    EditText editSearch;
    @BindView(R.id.image_clear)
    ImageView imageClear;
    @BindView(R.id.toolbar_search)
    Toolbar toolbarSearch;
    @BindView(R.id.recyclerView_search)
    RecyclerView recyclerViewSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        editSearch.setOnEditorActionListener(this);
        editSearch.addTextChangedListener(this);
        imageClear.setVisibility(View.INVISIBLE);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Utils.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        tmDbApil = retrofit.create(TMDbApi.class);

        layoutManager = new LinearLayoutManager(this);
        adaptor = new ListAdaptor(SearchActivity.this, movieList, this);
        recyclerViewSearch.setLayoutManager(layoutManager);
        recyclerViewSearch.setAdapter(adaptor);
    }

    @OnClick({R.id.image_back, R.id.image_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.image_back:
                finish();
                break;
            case R.id.image_clear:
                imageClear.setVisibility(View.INVISIBLE);
                editSearch.setText("");
                break;
        }
    }


    public void search(String query) {
        movieListCall = tmDbApil.getSearchResults(Utils.API_KEY, query);
        Log.d(TAG, "onCreate: " + movieListCall.request().url());
        movieListCall.enqueue(this);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            search(editSearch.getText().toString());
            movieList.clear();
            adaptor.notifyDataSetChanged();
            hideSoftKeyboard();
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (editSearch.getText().length() > 0) {
            imageClear.setVisibility(View.VISIBLE);
        } else {
            imageClear.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private void hideSoftKeyboard() {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    @Override
    public void onItemClicked(View view, String id) {
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra(getString(R.string.intentKey), id);
        startActivity(intent);
    }

    @Override
    public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {
        if (response.code()==200){
            Log.d(TAG, "onResponse: \n "+response.body().getMovieList().toString());
            movieList.addAll(response.body().getMovieList());
            adaptor.notifyDataSetChanged();
        }
    }

    @Override
    public void onFailure(Call<MovieList> call, Throwable t) {
        Log.e(TAG, "onFailure: "+t.getMessage() );
    }
}
