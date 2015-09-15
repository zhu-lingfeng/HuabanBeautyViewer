package edward.njust.hbv.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import edward.njust.hbv.HuabanImages;
import edward.njust.hbv.ImageInfo;
import edward.njust.hbv.ImageZoomActivity;
import edward.njust.hbv.MainActivity;
import edward.njust.hbv.R;
import edward.njust.hbv.ViewHolders.ImageCardHolder;

public class RecyclerAdapter extends RecyclerView.Adapter<ImageCardHolder> {
    private List<ImageInfo> data;
    private int dataSize;
    private Context context;
    private LayoutInflater mInflater;

    public RecyclerAdapter(List<ImageInfo> data, Context context) {
        mInflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }

    @Override
    public ImageCardHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ImageCardHolder holder = new ImageCardHolder(mInflater.inflate(
                R.layout.item_image_card, viewGroup, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final ImageCardHolder imageCardHolder, final int i) {
        if (i + 20 > dataSize) {
            HuabanImages.loadImages(data.get(dataSize - 1).getPinId(), 50, MainActivity.callback1);
        }
        final String key = data.get(i).getKey();
        Uri uri = Uri.parse(HuabanImages.generateImageThumbnailUrl(key));
        imageCardHolder.sdv.setAspectRatio(data.get(i).getRatio());
        imageCardHolder.sdv.setImageURI(uri);
        imageCardHolder.sdv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ImageZoomActivity.class);
                i.putExtra("key", key);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        dataSize = data.size();
        return dataSize;
    }
}
