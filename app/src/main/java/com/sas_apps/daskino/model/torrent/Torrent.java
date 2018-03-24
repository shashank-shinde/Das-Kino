package com.sas_apps.daskino.model.torrent;
/*
 * Created by Shashank Shinde.
 */

import com.google.gson.annotations.SerializedName;

public class Torrent {

    @SerializedName("url")
    private String url;
    @SerializedName("quality")
    private String quality;
    @SerializedName("seeds")
    private String seeds;
    @SerializedName("peers")
    private String peers;
    @SerializedName("size")
    private String size;


    public String getUrl() {
        return url;
    }

    public String getQuality() {
        return quality;
    }

    public String getSeeds() {
        return seeds;
    }

    public String getPeers() {
        return peers;
    }

    public String getSize() {
        return size;
    }

    @Override
    public String toString() {
        return "\n\nurl='" + url +
                "\n quality='" + quality +
                "\n seeds='" + seeds +
                "\n peers='" + peers +
                "\n size='" + size +
                '\n';
    }
}
