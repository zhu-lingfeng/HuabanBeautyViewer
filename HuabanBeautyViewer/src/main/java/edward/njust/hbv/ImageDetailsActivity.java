package edward.njust.hbv;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Window;


public class ImageDetailsActivity extends Activity {


//	private ZoomImageView zoomImageView;

	private Bitmap bitmap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.image_details);
//		zoomImageView = (ZoomImageView) findViewById(R.id.zoom_image_view);
		String imagePath = getIntent().getStringExtra("image_path");
		bitmap = BitmapFactory.decodeFile(imagePath);
//		zoomImageView.setImageBitmap(bitmap);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (bitmap != null) {
			bitmap.recycle();
		}
	}

}
