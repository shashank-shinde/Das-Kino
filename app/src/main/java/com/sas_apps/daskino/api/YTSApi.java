package com.sas_apps.daskino.api;
/*
 * Created by Shashank Shinde.
 */

import com.sas_apps.daskino.model.torrent.YTSTorrent;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface YTSApi {

    @GET("list_movies.json")
    Call<YTSTorrent> getTorrent(@Query("query_term") String searchQuery);
}
