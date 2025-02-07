package net.alphalightning.bedwars.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class MathUtil {

    public static double toPercentage(double value, int scale) {
        return round(value * 100, scale);
    }

    public static double round(double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException();
        }

        return BigDecimal.valueOf(value).setScale(scale, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
