package com.sas_apps.daskino.adaptor;
/*
 * Created by Shashank Shinde.
 */

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sas_apps.daskino.DetailActivity;
import com.sas_apps.daskino.R;
import com.sas_apps.daskino.model.torrent.Torrent;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TorrentsAdaptor extends RecyclerView.Adapter<TorrentsAdaptor.TorrentListHolder> {

    private List<Torrent> torrentList;
    private Context context;

    public TorrentsAdaptor(List<Torrent> torrentList, Context context) {
        this.torrentList = torrentList;
        this.context = context;
    }

    @Override
    public TorrentListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_torrent_list, parent, false);
        return new TorrentListHolder(view);
    }

    @Override
    public void onBindViewHolder(TorrentListHolder holder, int position) {
        final Torrent torrent = torrentList.get(position);
        holder.textQuality.setText("Quality: " + torrent.getQuality());
        holder.textPeers.setText("Peers: " + torrent.getPeers());
        holder.textSeeds.setText("Seeds: " + torrent.getSeeds());
        holder.textSize.setText("Size: " + torrent.getSize());
        holder.imageDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(torrent.getUrl()));
                request.setDescription("Downloading torrent");
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Toast.makeText(context,"Downloading torrent",Toast.LENGTH_SHORT).show();
                DetailActivity.dialogTorrent.dismiss();
                assert manager != null;
                manager.enqueue(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return torrentList.size();
    }

    public class TorrentListHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_quality)
        TextView textQuality;
        @BindView(R.id.text_size)
        TextView textSize;
        @BindView(R.id.text_peers)
        TextView textPeers;
        @BindView(R.id.text_seeds)
        TextView textSeeds;
        @BindView(R.id.image_download)
        ImageView imageDownload;

        TorrentListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
