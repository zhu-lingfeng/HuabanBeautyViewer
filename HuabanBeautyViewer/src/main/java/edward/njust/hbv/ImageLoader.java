package edward.njust.hbv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

public class ImageLoader {
	private static LruCache<String, Bitmap> mMemoryCache;
	private static ImageLoader mImageLoader;

	private ImageLoader() {
		int maxMemory = (int) Runtime.getRuntime().maxMemory();
		int cacheSize = maxMemory / 8;
		mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
			protected int sizeOf(String key, Bitmap bitmap) {
				return bitmap.getByteCount();
			}
		};
	}

	public static ImageLoader getInstance() {
		if (mImageLoader == null) {
			mImageLoader = new ImageLoader();
		}
		return mImageLoader;
	}

	public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
		if (getBitmapFromMemoryCache(key) == null) {
			mMemoryCache.put(key, bitmap);
		}
	}

	public Bitmap getBitmapFromMemoryCache(String key) {
		return mMemoryCache.get(key);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth) {
		final int width = options.outWidth;
		int inSampleSize = 1;
		if (width > reqWidth) {
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = widthRatio;
		}
		return inSampleSize;
	}

	public static Bitmap decodeSampledBitmapFromResource(String pathName,
			int reqWidth) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(pathName, options);
		options.inSampleSize = calculateInSampleSize(options, reqWidth);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(pathName, options);
	}
}
