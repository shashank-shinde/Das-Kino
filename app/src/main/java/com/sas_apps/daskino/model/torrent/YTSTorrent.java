package com.sas_apps.daskino.model.torrent;
/*
 * Created by Shashank Shinde.
 */

import com.google.gson.annotations.SerializedName;

public class YTSTorrent {

    @SerializedName("status")
    private String status;

    @SerializedName("data")
    private YTSData ytsData;

    public YTSData getYtsData() {
        return ytsData;
    }

    public String getStatus() {
        return status;
    }
}
