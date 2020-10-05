package org.andresoviedo.android_3d_model_engine.model;

import androidx.annotation.NonNull;

public class Object3DDataColor {

    private static final float[] COLOR_DEFAULT = {0.1f, 0.5f, 0.9f, 1f};
    private static final float[] COLOR_RED= {1.0f, 0.0f, 0.0f, 1f};
    private static final float[] COLOR_GREEN = {0.0f, 1.0f, 0.0f, 1f};
    private static final float[] COLOR_WHITE = {1f, 1f, 1f, 1f};
    private static final float[] COLOR_BLACK = {0f, 0f, 0f, 1f};
    private static final float[] COLOR_BLUE = {0f, 0f, 1f, 1f};
    private static final float[] COLOR_YELLOW =  { 1f, 1f, 0f, 1f };

    private ColorEnum mColorEnum;
    private float[] mColorArray;

    public Object3DDataColor(ColorEnum colorEnum) {
        super();
        mColorArray = getColorArray(colorEnum);
        mColorEnum = colorEnum;
    }

    private float[] getColorArray(ColorEnum colorEnum) {
        float[] result = COLOR_DEFAULT;
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
            case BLACK:
                result = COLOR_BLACK;
                break;
//            case RED:
//                result = COLOR_RED;
//                break;
        }
        return result;
    }


    public float[] getColorArray() {
        return mColorArray;
    }

    public String getColorName() {
        return mColorEnum.toString().toLowerCase();
    }


    public enum ColorEnum {
        BLUE, GREEN, WHITE, YELLOW, BLACK, RED;

        @NonNull
        @Override
        public String toString() {
            String result = "Ошибка";
            switch (this) {
                case BLUE:
                    result = "Синий";
                    break;
                case GREEN:
                    result = "Зеленый";
                    break;
                case WHITE:
                    result = "Белый";
                    break;
                case YELLOW:
                    result = "Жёлтый";
                    break;
                case BLACK:
                    result = "Чёрный";
                    break;
//                case RED:
//                    result = "Красный";
//                    break;
            }
            return result;
        }
    }
}
