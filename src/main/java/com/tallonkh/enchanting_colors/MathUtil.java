package com.tallonkh.enchanting_colors;

public class MathUtil {
    public static float lerpf(float a, float b, float x) {
        return a + x * (b - a);
    }

    public static int[] breakColor(int argb) {
        return new int[]{
                (argb >> 24) & 0xFF,
                (argb >> 16) & 0xFF,
                (argb >> 8) & 0xFF,
                (argb & 0xFF)
        };
    }

    public static int makeColor(int a, int r, int g, int b) {
        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static int colorFromHexCode(String str){
        String colorString = str.toUpperCase();
        if (colorString.startsWith("0X")) {
            colorString = colorString.substring(2);
        }
        return Integer.parseInt(colorString, 16) | 0xFF000000;
    }
}
