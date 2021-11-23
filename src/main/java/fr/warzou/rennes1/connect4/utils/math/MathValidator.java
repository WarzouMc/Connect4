package fr.warzou.rennes1.connect4.utils.math;

import fr.warzou.rennes1.connect4.utils.math.exception.MathValidatorException;

import static fr.warzou.rennes1.connect4.utils.math.MathChars.*;

/**
 * Check some things on maths values.
 */
public class MathValidator {

    private static final String NEGATIVE_OR_0_ERROR = "Invalid value : %s <= 0";
    private static final String NEGATIVE_ERROR = "Invalid value : %s < 0";
    private static final String NULL_ERROR = "Invalid value : %s cannot be null (0)";
    private static final String NOT_IN_INTERVAL = "Invalid value : %s " + NOT_IN + " %s%s ; %s%s !";
    private static final String NON_EQUALS = "%s " + NOT_EQUAL_TO + " %s !";

    public static void nonNegativeOrNull(int check) {
        if (check <= 0)
            throw new MathValidatorException(NEGATIVE_OR_0_ERROR, check);
    }

    public static void nonNegativeOrNull(long check) {
        if (check <= 0)
            throw new MathValidatorException(NEGATIVE_OR_0_ERROR, check);
    }

    public static void nonNegativeOrNull(byte check) {
        if (check <= 0)
            throw new MathValidatorException(NEGATIVE_OR_0_ERROR, check);
    }

    public static void nonNegativeOrNull(double check) {
        if (check <= 0)
            throw new MathValidatorException(NEGATIVE_OR_0_ERROR, check);
    }

    public static void nonNegativeOrNull(float check) {
        if (check <= 0)
            throw new MathValidatorException(NEGATIVE_OR_0_ERROR, check);
    }

    public static void nonNegative(int check) {
        if (check < 0)
            throw new MathValidatorException(NEGATIVE_ERROR, check);
    }

    public static void nonNegative(long check) {
        if (check < 0)
            throw new MathValidatorException(NEGATIVE_ERROR, check);
    }

    public static void nonNegative(byte check) {
        if (check < 0)
            throw new MathValidatorException(NEGATIVE_ERROR, check);
    }

    public static void nonNegative(float check) {
        if (check < 0)
            throw new MathValidatorException(NEGATIVE_ERROR, check);
    }

    public static void nonNegative(double check) {
        if (check < 0)
            throw new MathValidatorException(NEGATIVE_ERROR, check);
    }

    public static void nonNull(int check) {
        if (check == 0)
            throw new MathValidatorException(NULL_ERROR, check);
    }

    public static void nonNull(long check) {
        if (check == 0)
            throw new MathValidatorException(NULL_ERROR, check);
    }

    public static void nonNull(byte check) {
        if (check == 0)
            throw new MathValidatorException(NULL_ERROR, check);
    }

    public static void nonNull(float check) {
        if (check == 0.0f)
            throw new MathValidatorException(NULL_ERROR, check);
    }

    public static void nonNull(double check) {
        if (check == 0.0d)
            throw new MathValidatorException(NULL_ERROR, check);
    }

    public static void inIncludeInterval(int check, double a, double b) {
        inInterval(check, a, b, true, true);
    }

    public static void inIncludeInterval(long check, double a, double b) {
        inInterval(check, a, b, true, true);
    }

    public static void inIncludeInterval(byte check, double a, double b) {
        inInterval(check, a, b, true, true);
    }

    public static void inIncludeInterval(float check, double a, double b) {
        inInterval(check, a, b, true, true);
    }

    public static void inIncludeInterval(double check, double a, double b) {
        inInterval(check, a, b, true, true);
    }

    public static void inExcludeInterval(int check, double a, double b) {
        inInterval(check, a, b, false, false);
    }

    public static void inExcludeInterval(long check, double a, double b) {
        inInterval(check, a, b, false, false);
    }

    public static void inExcludeInterval(byte check, double a, double b) {
        inInterval(check, a, b, false, false);
    }

    public static void inExcludeInterval(float check, double a, double b) {
        inInterval(check, a, b, false, false);
    }

    public static void inExcludeInterval(double check, double a, double b) {
        inInterval(check, a, b, false, false);
    }

    public static void inInterval(int check, double a, double b, boolean includeA, boolean includeB) {
        inInterval((double) check, a, b, includeA, includeB);
    }

    public static void inInterval(long check, double a, double b, boolean includeA, boolean includeB) {
        inInterval((double) check, a, b, includeA, includeB);
    }

    public static void inInterval(byte check, double a, double b, boolean includeA, boolean includeB) {
        inInterval((double) check, a, b, includeA, includeB);
    }

    public static void inInterval(float check, double a, double b, boolean includeA, boolean includeB) {
        inInterval((double) check, a, b, includeA, includeB);
    }

    public static void inInterval(double check, double a, double b, boolean includeA, boolean includeB) {
        double aTemp = a;
        a = Math.min(a, b);
        b = Math.max(aTemp, b);

        if (includeA && check < a)
            throw new MathValidatorException(NOT_IN_INTERVAL, check, OPEN_INTERVAL_INCLUDE, a, b,
                    includeB ? CLOSE_INTERVAL_INCLUDE : CLOSE_INTERVAL_EXCLUDE);

        if (!includeA && check <= a)
            throw new MathValidatorException(NOT_IN_INTERVAL, check, OPEN_INTERVAL_EXCLUDE, a, b,
                    includeB ? CLOSE_INTERVAL_INCLUDE : CLOSE_INTERVAL_EXCLUDE);

        if (includeB && check > b)
            throw new MathValidatorException(NOT_IN_INTERVAL, check, includeA ? OPEN_INTERVAL_INCLUDE : OPEN_INTERVAL_EXCLUDE,
                    a, b, CLOSE_INTERVAL_INCLUDE);

        if (!includeB && check >= b)
            throw new MathValidatorException(NOT_IN_INTERVAL, check, includeA ? OPEN_INTERVAL_INCLUDE : OPEN_INTERVAL_EXCLUDE,
                    a, b, CLOSE_INTERVAL_EXCLUDE);
    }

    public static void equal(int a, int b) {
        if (a != b)
            throw new MathValidatorException(NON_EQUALS, a, b);
    }

    public static void equal(long a, long b) {
        if (a != b)
            throw new MathValidatorException(NON_EQUALS, a, b);
    }

    public static void equal(byte a, byte b) {
        if (a != b)
            throw new MathValidatorException(NON_EQUALS, a, b);
    }

    public static void equal(double a, double b) {
        if (a != b)
            throw new MathValidatorException(NON_EQUALS, a, b);
    }

    public static void equal(float a, float b) {
        if (a != b)
            throw new MathValidatorException(NON_EQUALS, a, b);
    }
}
