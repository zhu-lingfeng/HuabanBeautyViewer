package edward.njust.hbv;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HuabanImages {
    //    private static final String IMAGE_RE = "\"pin_id\":([0-9]+),([^}]*)\"board_id\"([^}]*)\"key\":\"([^\"]+)\"";
    private static final String IMAGE_RE = "\"pin_id\":([0-9]+),([^}]*)\"board_id\"([^}]*)\"key\":\"([^\"]+)\",([^}]*)\"width\":([0-9]+),\"height\":([0-9]+)";
    private static final String NUMBER_RE = "([0-9]+)";
    private static final String KEY_RE = "([0-9a-zA-Z-]{15,})";

    private static final int SMALL_SIZE = 192;
    private static final int MIDDLE_SIZE = 236;
    public static boolean isLoading;
    private static Pattern patternImage;
    private static String singleImage;
    private static Matcher matcherImage;
    private static OkHttpClient client;
    private static Gson gson;

    static {
        isLoading = false;
        patternImage = Pattern.compile(IMAGE_RE);
        client = new OkHttpClient();
        gson = new Gson();
    }

    public static void loadNewestImages(int limit, Callback callback) {
        if (!isLoading) {
            synchronized (HuabanImages.class) {
                if (!isLoading) {
                    isLoading = true;
                    String url = "http://api.huaban.com/favorite/beauty?limit=" + limit;
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    client.newCall(request).enqueue(callback);
                }
            }
        }
    }

    public static void loadImages(int imagePinId, int limit, Callback callback) {
        if (!isLoading) {
            synchronized (HuabanImages.class) {
                if (!isLoading) {
                    isLoading = true;
                    String url = "http://api.huaban.com/favorite/beauty?max=" + imagePinId + "&limit=" + limit;
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    client.newCall(request).enqueue(callback);
                }
            }
        }
    }

    public static List<ImageInfo> generateImageInfos(String input) {
        ArrayList<ImageInfo> infos = new ArrayList<ImageInfo>();
        matcherImage = patternImage.matcher(input);
        while (matcherImage.find()) {
            singleImage = matcherImage.group();
            JsonModel jm = gson.fromJson("{" + singleImage + "}}", JsonModel.class);
            FileModel fm = jm.getFile();
            infos.add(new ImageInfo(jm.getPin_id(), fm.getKey(), fm.getWidth() / (fm.getHeight() + 0.0f)));
        }
        return infos;
    }

    public static String generateImageUrl(String key) {
        return ("http://img.hb.aicdn.com/" + key);
    }

    public static String generateImageThumbnailUrl(String key) {
        return ("http://img.hb.aicdn.com/" + key + "_fw192w");
    }

}
