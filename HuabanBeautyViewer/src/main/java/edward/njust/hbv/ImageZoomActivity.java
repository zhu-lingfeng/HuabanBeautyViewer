package edward.njust.hbv;

import android.app.Activity;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;

import edward.njust.hbv.photodraweeview.OnPhotoTapListener;
import edward.njust.hbv.photodraweeview.OnViewTapListener;
import edward.njust.hbv.photodraweeview.PhotoDraweeView;


public class ImageZoomActivity extends Activity {
    private PhotoDraweeView pdv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_zoom);
        String key = getIntent().getStringExtra("key");

        pdv = (PhotoDraweeView) findViewById(R.id.pdv);
        PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
        controller.setUri(Uri.parse(HuabanImages.generateImageUrl(key)));
        controller.setOldController(pdv.getController());
        // You need setControllerListener
        controller.setControllerListener(new BaseControllerListener<com.facebook.imagepipeline.image.ImageInfo>() {
            @Override
            public void onFinalImageSet(String id, com.facebook.imagepipeline.image.ImageInfo imageInfo, Animatable animatable) {
                super.onFinalImageSet(id, imageInfo, animatable);
                if (imageInfo == null || pdv == null) {
                    return;
                }
                pdv.update(imageInfo.getWidth(), imageInfo.getHeight());
            }
        });
        pdv.setController(controller.build());
        pdv.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                Toast.makeText(view.getContext(), "onPhotoTap :  x =  " + x + ";" + " y = " + y,
                        Toast.LENGTH_SHORT).show();
            }
        });
        pdv.setOnViewTapListener(new OnViewTapListener() {
            @Override
            public void onViewTap(View view, float x, float y) {
                Toast.makeText(view.getContext(), "onViewTap", Toast.LENGTH_SHORT).show();
            }
        });

        pdv.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(v.getContext(), "onLongClick", Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_image_zoom, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
