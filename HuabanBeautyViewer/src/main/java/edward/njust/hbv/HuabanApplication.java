package edward.njust.hbv;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by 朱凌峰 on 6-12.
 */
public class HuabanApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(getApplicationContext());
    }
}
