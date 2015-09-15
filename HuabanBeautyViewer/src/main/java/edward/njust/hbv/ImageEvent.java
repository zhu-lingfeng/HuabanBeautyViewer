package edward.njust.hbv;

/**
 * Created by 朱凌峰 on 6-11.
 */
public class ImageEvent {
    private int sourceKind;
    private int positionStart;
    private int itemCount;

    public ImageEvent(int kind, int start, int count) {
        sourceKind = kind;
        positionStart = start;
        itemCount = count;
    }

    public int getSourceKind() {
        return sourceKind;
    }

    public int getPositionStart() {
        return positionStart;
    }

    public int getItemCount() {
        return itemCount;
    }
}
