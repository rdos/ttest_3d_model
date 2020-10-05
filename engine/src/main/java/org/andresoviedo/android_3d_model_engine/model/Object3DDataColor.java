package org.andresoviedo.android_3d_model_engine.model;

public class Object3DDataColor {
    private float[] mColorArray;

    private static final float[] COLOR_RED_DEFAULT = {1.0f, 0.0f, 0.0f, 1f};
    private static final float[] COLOR_GREEN = {0.0f, 1.0f, 0.0f, 1f};
    private static final float[] COLOR_WHITE = {1f, 1f, 1f, 1f};
    private static final float[] COLOR_BLACK = {0f, 0f, 0f, 1f};
    private static final float[] COLOR_BLUE = {0f, 0f, 1f, 1f};

    private static final float[] COLOR_YELLOW =  { 1f, 1f, 0f, 1f };

    public Object3DDataColor(ColorEnum colorEnum) {
        super();
        mColorArray = getColorArray(colorEnum);
    }

    private float[] getColorArray(ColorEnum colorEnum) {
        float[] result = COLOR_RED_DEFAULT;
        switch (colorEnum) {
            case BLUE:
                result = COLOR_BLUE;
                break;
            case GREEN:
                result = COLOR_GREEN;
                break;
            case WHITE:
                result = COLOR_WHITE;
                break;
            case YELLOW:
                result = COLOR_YELLOW;
                break;
        }
        return result;
    }


    public float[] getColorArray() {
        return mColorArray;
    }


    public enum ColorEnum {
        BLUE, GREEN, WHITE, YELLOW, RED
    }
}
