package edward.njust.hbv.ViewHolders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import edward.njust.hbv.R;

public class ImageCardHolder extends RecyclerView.ViewHolder {
    public SimpleDraweeView sdv;

    public ImageCardHolder(View itemView) {
        super(itemView);
        sdv = (SimpleDraweeView) itemView.findViewById(R.id.sdv);
    }
}
