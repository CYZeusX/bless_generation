package com.CYZco.nygreets;

import java.util.List;
import android.net.Uri;
import android.os.Build;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.LayoutInflater;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.exoplayer2.ui.PlayerView;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.CardViewHolder>
{
    private List<String> videoPaths;
    private List<String> which;

    public TutorialAdapter(List<String> videoPaths, List<String> which)
    {this.videoPaths = videoPaths; this.which = which;}

    @Override
    public CardViewHolder onCreateViewHolder(ViewGroup parent, int viwType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tutorial_items, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardViewHolder holder, int position)
    {
        String videoPath = videoPaths.get(position);
        String which_tut = which.get(position);

        holder.which.setText(which_tut);
        holder.bindVideo(videoPath);
    }

    @Override
    public int getItemCount() {return videoPaths.size();}

    public static class CardViewHolder extends RecyclerView.ViewHolder
    {
        PlayerView playerView;
        ExoPlayer player;
        TextView which;

        public CardViewHolder(View itemView)
        {
            super(itemView);
            playerView = itemView.findViewById(R.id.generate);
            which = itemView.findViewById(R.id.which_tutorial);
            player = new ExoPlayer.Builder(itemView.getContext()).build();
            playerView.setPlayer(player);
        }

        public void bindVideo(String videoPath)
        {
            Uri uri = Uri.parse(videoPath);
            MediaItem mediaItem = MediaItem.fromUri(uri);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.setPlayWhenReady(true);
        }

        public void releaseVideo()
        {player.release();}
    }

    @Override
    public void onViewRecycled(CardViewHolder holder)
    {
        super.onViewRecycled(holder);
        holder.releaseVideo();
    }
}