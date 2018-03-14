package com.sas_apps.daskino;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sas_apps.daskino.adaptor.ListAdaptor;
import com.sas_apps.daskino.adaptor.OnItemClicked;
import com.sas_apps.daskino.api.TMDbApi;
import com.sas_apps.daskino.model.Movie;
import com.sas_apps.daskino.model.MovieList;
import com.sas_apps.daskino.adaptor.GridSpaceItemDecoration;
import com.sas_apps.daskino.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A simple {@link  Fragment} subclass.
 */
public class TopRatedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnItemClicked,
        Callback<MovieList> {

    public TopRatedFragment() {
        // Required empty public constructor
    }

    private static final String TAG = "TopRatedFragment";
    private Retrofit retrofit = null;
    private TMDbApi tmDbApil;
    private Call<MovieList> movieListCall;
    LinearLayoutManager layoutManager;
    private List<Movie> movieList = new ArrayList<>();

    //    Pagination
    private int pageNumber = 1;
    private int itemCount = 10;
    private int maxPages = 10;
    private int totalItem, pastTotal, visibleItem, pastVisible = 0;
    private boolean isLoading = true;

    @BindView(R.id.swipeLayout_topRated)
    SwipeRefreshLayout swipeNowPlaying;
    @BindView(R.id.recyclerView_topRated)
    RecyclerView recyclerViewTopRated;

    Unbinder unbinder;
    private ListAdaptor adaptor;

    @BindColor(R.color.colorAccent)
    int colorAccent;
    @BindColor(R.color.colorAccent1)
    int colorAccent1;
    @BindColor(R.color.colorAccent2)
    int colorAccent2;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_top_rated, container, false);
        unbinder = ButterKnife.bind(this, view);
        swipeNowPlaying.setOnRefreshListener(this);
        swipeNowPlaying.setColorSchemeColors(colorAccent, colorAccent1, colorAccent2);
        layoutManager = new LinearLayoutManager(getContext());
        adaptor = new ListAdaptor(getContext(), movieList, this);
        recyclerViewTopRated.setLayoutManager(layoutManager);
        recyclerViewTopRated.addItemDecoration(new GridSpaceItemDecoration(2, 15, true));
        recyclerViewTopRated.setAdapter(adaptor);
        return view;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Utils.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        tmDbApil = retrofit.create(TMDbApi.class);
        call();
    }

    public void call() {
        movieListCall = tmDbApil.getTopRated(Utils.API_KEY, pageNumber);
        Log.d(TAG, "onCreate: " + movieListCall.request().url());
        movieListCall.enqueue(this);
    }


    @Override
    public void onResponse(@NonNull Call<MovieList> call, @NonNull Response<MovieList> response) {

        if (response.code() == 200) {
//            Log.d(TAG, "onResponse      NOW_PLAYING\n: " + response.body().getMovieList().toString());
//            Toast.makeText(getActivity(), "Page " + pageNumber, Toast.LENGTH_SHORT).show();
            try {
                adaptor.loadMoreResults(response.body().getMovieList());
            }catch (NullPointerException e){
                Log.d(TAG, "NullPointerException: "+e.getMessage());
            }
            if (pageNumber == 1) {
                pageNumber++;
            }
        }

        if (layoutManager != null) {
            recyclerViewTopRated.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    visibleItem = layoutManager.getChildCount();
                    totalItem = layoutManager.getItemCount();
                    pastVisible = layoutManager.findFirstVisibleItemPosition();

                    if (dy > 0) {
                        if (isLoading) {
                            if (totalItem > pastTotal) {
                                isLoading = false;
                                pastTotal = totalItem;
                            }
                        }
                        if (!isLoading && (totalItem - visibleItem) <= (pastVisible + maxPages)) {
                            call();
                            isLoading = true;
                            pageNumber += 1;
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onFailure(@NonNull Call<MovieList> call, @NonNull Throwable t) {
        Log.e(TAG, "onFailure: " + t.getMessage());
    }


    @Override
    public void onRefresh() {
        swipeNowPlaying.setRefreshing(false);
        call();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onItemClicked(View view, String id) {
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra(getString(R.string.intentKey), id);
        startActivity(intent);
    }
}
