package edward.njust.hbv;

/**
 * Created by 朱凌峰 on 6-11.
 */
public class ImageInfo {
    private int pinId;
    private String key;
    private float ratio;

    public ImageInfo(int pinId, String key, float ratio) {
        this.pinId = pinId;
        this.key = key;
        this.ratio = ratio;
    }

    public float getRatio() {
        return ratio;
    }

    public void setRatio(float ratio) {
        this.ratio = ratio;
    }

    public int getPinId() {
        return pinId;
    }

    public void setPinId(int pinId) {
        this.pinId = pinId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        String s = "PinId:" + pinId + " Key:" + key + " ratio:" + ratio;
        return s;
    }
}
