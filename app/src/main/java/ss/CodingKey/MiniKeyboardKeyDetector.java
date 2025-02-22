package ss.CodingKey;

import ss.CodingKey.Keyboard.Key;

class MiniKeyboardKeyDetector extends KeyDetector {
    private static final int MAX_NEARBY_KEYS = 1;

    private final int mSlideAllowanceSquare;
    private final int mSlideAllowanceSquareTop;

    public MiniKeyboardKeyDetector(float slideAllowance) {
        super();
        mSlideAllowanceSquare = (int)(slideAllowance * slideAllowance);
        // Top slide allowance is slightly longer (sqrt(2) times) than other edges.
        mSlideAllowanceSquareTop = mSlideAllowanceSquare * 2;
    }

    @Override
    protected int getMaxNearbyKeys() {
        return MAX_NEARBY_KEYS;
    }

    @Override
    public int getKeyIndexAndNearbyCodes(int x, int y, int[] allKeys) {
        final Key[] keys = getKeys();
        final int touchX = getTouchX(x);
        final int touchY = getTouchY(y);
        int closestKeyIndex = LatinKeyboardBaseView.NOT_A_KEY;
        int closestKeyDist = (y < 0) ? mSlideAllowanceSquareTop : mSlideAllowanceSquare;
        final int keyCount = keys.length;
        for (int i = 0; i < keyCount; i++) {
            final Key key = keys[i];
            int dist = key.squaredDistanceFrom(touchX, touchY);
            if (dist < closestKeyDist) {
                closestKeyIndex = i;
                closestKeyDist = dist;
            }
        }
        if (allKeys != null && closestKeyIndex != LatinKeyboardBaseView.NOT_A_KEY)
            allKeys[0] = keys[closestKeyIndex].getPrimaryCode();
        return closestKeyIndex;
    }
}
