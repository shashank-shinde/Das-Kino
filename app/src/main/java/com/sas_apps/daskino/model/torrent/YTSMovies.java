package com.sas_apps.daskino.model.torrent;
/*
 * Created by Shashank Shinde.
 */

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class YTSMovies {


    @SerializedName("torrents")
    private List<Torrent> torrentList;

    @SerializedName("yt_trailer_code")
    private String trailerCode;

    public List<Torrent> getTorrentList() {
        return torrentList;
    }
}
