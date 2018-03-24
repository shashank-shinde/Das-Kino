package com.sas_apps.daskino;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.sas_apps.daskino.adaptor.CastListAdaptor;
import com.sas_apps.daskino.adaptor.OnItemClicked;
import com.sas_apps.daskino.adaptor.OnSuggestionClicked;
import com.sas_apps.daskino.adaptor.SuggestionAdaptor;
import com.sas_apps.daskino.adaptor.TorrentsAdaptor;
import com.sas_apps.daskino.api.TMDbApi;
import com.sas_apps.daskino.api.YTSApi;
import com.sas_apps.daskino.model.Movie;
import com.sas_apps.daskino.model.MovieList;
import com.sas_apps.daskino.model.movieDetails.Cast;
import com.sas_apps.daskino.model.movieDetails.MovieCast;
import com.sas_apps.daskino.model.movieDetails.MovieDetails;
import com.sas_apps.daskino.model.torrent.Torrent;
import com.sas_apps.daskino.model.torrent.YTSTorrent;
import com.sas_apps.daskino.utils.Utils;

import java.text.ParseException;
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

public class DetailActivity extends AppCompatActivity implements Callback<MovieDetails>, OnSuggestionClicked,
        OnItemClicked {

    private static final String TAG = "DetailActivity";

    private Retrofit retrofit = null;
    private TMDbApi tmDbApi;
    private Call<MovieDetails> movieDetailsCall;

    private YTSApi ytsApi;
    private Call<YTSTorrent> torrentCall;
    private Retrofit torrentRetrofit = null;
    private List<Torrent> torrentList = new ArrayList<>();
    private Dialog dialogTorrent;
    private TorrentsAdaptor torrentsAdaptor;
    TextView textDialog;
    RecyclerView recyclerViewDialog;

    MovieDetails movieDetails;

    private CastListAdaptor adaptorCast;
    private LinearLayoutManager layoutManager;
    private List<MovieCast> castList = new ArrayList<>();
    private Call<Cast> movieCastcall;

    private SuggestionAdaptor adaptorSuggestion;
    private LinearLayoutManager layoutManagerSuggestion;
    private List<Movie> movieList = new ArrayList<>();
    private Call<MovieList> movieListCall;


    @BindView(R.id.recyclerView_cast)
    RecyclerView recyclerViewCast;
    @BindView(R.id.recyclerView_peopleAlsoLike)
    RecyclerView recyclerViewPeopleAlsoLike;
    ExpandableTextView expandableTextView;
    @BindView(R.id.image_background)
    ImageView imageBackground;
    @BindView(R.id.image_posterDetails)
    ImageView imagePosterDetails;
    @BindView(R.id.text_titleDetails)
    TextView textTitleDetails;
    @BindView(R.id.text_releaseDateDetails)
    TextView textReleaseDateDetails;
    @BindView(R.id.textView1_ratingsDetails)
    TextView textView1RatingsDetails;
    @BindView(R.id.toolbar_detail)
    Toolbar toolbarDetail;
    @BindView(R.id.collapsingToolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.textView_runTimeDetails)
    TextView textViewRunTimeDetails;
    @BindView(R.id.textView_originalTitleDetails)
    TextView textViewOriginalTitleDetails;
    @BindView(R.id.textView_originalLanguageDetails)
    TextView textViewOriginalLanguageDetails;
    @BindView(R.id.expandable_text)
    TextView expandableText;
    @BindView(R.id.expand_collapse)
    ImageButton expandCollapse;
    @BindView(R.id.expand_text_view)
    ExpandableTextView expandTextView;
    @BindView(R.id.textView_budgetDetails)
    TextView textViewBudgetDetails;
    @BindView(R.id.textView_boxOfficeDetails)
    TextView textViewBoxOfficeDetails;
    @BindView(R.id.button_torrent)
    Button buttonTorrent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        expandableTextView = findViewById(R.id.expand_text_view);
//        expandableTextView.setText(getString(R.string.demoText));
        String id = getIntent().getStringExtra(getResources().getString(R.string.intentKey));
//        Log.d(TAG, "onCreate: " + id);

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Utils.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        tmDbApi = retrofit.create(TMDbApi.class);
        ytsApi = retrofit.create(YTSApi.class);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adaptorCast = new CastListAdaptor(this, castList, this);
        recyclerViewCast.setLayoutManager(layoutManager);
        recyclerViewCast.setAdapter(adaptorCast);

        layoutManagerSuggestion = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        adaptorSuggestion = new SuggestionAdaptor(this, movieList, this);
        recyclerViewPeopleAlsoLike.setLayoutManager(layoutManagerSuggestion);
        recyclerViewPeopleAlsoLike.setAdapter(adaptorSuggestion);
        call(id);
        callCast(id);
        callSuggestion(id);
    }

    private void callSuggestion(String id) {
        movieListCall = tmDbApi.getSuggestion(id, Utils.API_KEY);
        Log.d(TAG, "callSuggestion: " + movieListCall.request().url());
        movieListCall.enqueue(new Callback<MovieList>() {
            @Override
            public void onResponse(Call<MovieList> call, Response<MovieList> response) {
                if (response.code() == 200) {
//                    Log.d(TAG, "onResponse: \n" + response.body().getMovieList().toString());
                    movieList.addAll(response.body().getMovieList());
                    adaptorSuggestion.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<MovieList> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void call(String id) {
        movieDetailsCall = tmDbApi.getMovieDetails(id, Utils.API_KEY);
//        Log.d(TAG, "onCreate: " + movieDetailsCall.request().url());
        movieDetailsCall.enqueue(this);
    }

    private void callCast(String id) {
        movieCastcall = tmDbApi.getMovieCast(id, Utils.API_KEY);
//        Log.d(TAG, "callCast: " + movieCastcall.request().url());
        movieCastcall.enqueue(new Callback<Cast>() {
            @Override
            public void onResponse(@NonNull Call<Cast> call, @NonNull Response<Cast> response) {
                if (response.code() == 200) {
//                    Log.d(TAG, "onResponse: \n" + response.body().getCastList().toString());
                    castList.addAll(response.body().getCastList());
                    adaptorCast.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Cast> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    @OnClick(R.id.button_torrent)
    public void onViewClicked() {
        showTorrentDialog();
        if (torrentRetrofit == null) {
            torrentRetrofit = new Retrofit.Builder()
                    .baseUrl(Utils.YTS_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        ytsApi = torrentRetrofit.create(YTSApi.class);
        callTorrent(textTitleDetails.getText().toString());
    }

    private void showTorrentDialog() {
        dialogTorrent = new Dialog(this);
        dialogTorrent.setContentView(R.layout.dialog_torrent);
        ProgressBar progressBarDialog = dialogTorrent.findViewById(R.id.progressBar_dialog);
        textDialog = dialogTorrent.findViewById(R.id.text_dialogTitle);
        recyclerViewDialog = dialogTorrent.findViewById(R.id.recyclerView_torrentList);
        Button buttonCancel = dialogTorrent.findViewById(R.id.button_dialogCancel);
        dialogTorrent.setCanceledOnTouchOutside(true);
        dialogTorrent.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        textDialog.setText(textTitleDetails.getText());
        torrentsAdaptor = new TorrentsAdaptor(torrentList, this);
        recyclerViewDialog.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewDialog.setAdapter(torrentsAdaptor);
        dialogTorrent.show();
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTorrent.cancel();
            }
        });
    }

    private void callTorrent(String searchQuery) {
        torrentCall = ytsApi.getTorrent(searchQuery);
        torrentCall.enqueue(new Callback<YTSTorrent>() {
            @Override
            public void onResponse(@NonNull Call<YTSTorrent> call, @NonNull Response<YTSTorrent> response) {
                if (response.code() == 200) {
                    if (Integer.parseInt(response.body().getYtsData().getMovieCount()) > 0) {
                        torrentList = response.body().getYtsData().getYtsMoviesList().get(0).getTorrentList();
                        Log.d(TAG, torrentList.toString());
                    } else {
                        Log.d(TAG, "onResponse: No torrents found");
                        textDialog.setText("No torrents found");
                    }
                }
            }

            @Override
            public void onFailure(Call<YTSTorrent> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }


    private void setContent() {
        toolbarDetail.setTitle(movieDetails.getTitle());
        Glide.with(this)
                .load(Utils.POSTER_URL + movieDetails.getBackdropPath())
                .into(imageBackground);
        Glide.with(this)
                .load(Utils.THUMBNAIL_URL + movieDetails.getPosterPath())
                .into(imagePosterDetails);
        textTitleDetails.setText(movieDetails.getTitle());
        textTitleDetails.setSelected(true);

        textView1RatingsDetails.setText(Html.fromHtml("<b>" + movieDetails.getRatings().substring(0, 3) + "</b> " + "/10"));

        try {
            textReleaseDateDetails.setText(Utils.getDate(movieDetails.getReleaseDate()));
        } catch (ParseException e) {
            e.printStackTrace();
            textReleaseDateDetails.setText("");
        }

        expandableTextView.setText(movieDetails.getOverview());
        textViewBoxOfficeDetails.setText(Utils.KMBconversion(movieDetails.getBoxOffice()));
        textViewRunTimeDetails.setText(Utils.getHours(movieDetails.getRuntime()));
        textViewOriginalTitleDetails.setText(movieDetails.getOriginalTitle());
        textViewBudgetDetails.setText(Utils.KMBconversion(movieDetails.getBudget()));
        textViewOriginalLanguageDetails.setText(Utils.getLanguage(movieDetails.getOriginalLanguage()));

        callTorrent(textTitleDetails.getText().toString());
    }

    @Override
    public void onResponse(@NonNull Call<MovieDetails> call, @NonNull Response<MovieDetails> response) {
        if (response.code() == 200) {
//            Log.d(TAG, response.body().toString());
            movieDetails = response.body();
            setContent();
        }
    }


    @Override
    public void onFailure(@NonNull Call<MovieDetails> call, @NonNull Throwable t) {
        Log.e(TAG, "onFailure: " + t.getMessage());
        Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onSuggestionClicked(View view, String id) {
        Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
        intent.putExtra(getString(R.string.intentKey), id);
        startActivity(intent);
    }

    @Override
    public void onItemClicked(View view, String id) {

    }


}
