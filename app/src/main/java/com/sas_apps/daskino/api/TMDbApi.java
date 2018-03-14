package com.sas_apps.daskino.api;
/*
 * Created by Shashank Shinde.
 */

import com.sas_apps.daskino.model.MovieList;
import com.sas_apps.daskino.model.movieDetails.Cast;
import com.sas_apps.daskino.model.movieDetails.MovieDetails;
import com.sas_apps.daskino.model.person.Person;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDbApi {

    @GET("movie/now_playing")
    Call<MovieList> getNowPlaying(@Query("api_key") String apiKey, @Query("page") int pageNumber);

    @GET("movie/upcoming")
    Call<MovieList> getUpComing(@Query("api_key") String apiKey, @Query("page") int pageNumber);

    @GET("movie/popular")
    Call<MovieList> getPopular(@Query("api_key") String apiKey, @Query("page") int pageNumber);

    @GET("movie/top_rated")
    Call<MovieList> getTopRated(@Query("api_key") String apiKey, @Query("page") int pageNumber);

    @GET("search/movie")
    Call<MovieList> getSearchResults(@Query("api_key") String apiKey, @Query("query") String query);

    @GET("movie/{id}")
    Call<MovieDetails> getMovieDetails(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("movie/{id}/credits")
    Call<Cast> getMovieCast(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("person/{id}")
    Call<Person> getPersonDetails(@Path("id") String id, @Query("api_key") String apiKey);

    @GET("movie/{id}/recommendations")
    Call<MovieList> getSuggestion(@Path("id") String id, @Query("api_key") String apiKey);

//    https://api.themoviedb.org/3/movie/278/recommendations?api_key=5cbefd2315d88e62a174ad57bf0e0ba0

}
