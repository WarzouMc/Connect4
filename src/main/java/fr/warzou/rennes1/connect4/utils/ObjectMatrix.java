package fr.warzou.rennes1.connect4.utils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class ObjectMatrix<O> {

    private final Class<O> type;
    private final Class<O[]> arrayType;
    private final Object matrix;
    private final int width;
    private final int height;

    public ObjectMatrix(Class<O> type, Class<O[]> arrayType, int width, int height, O fill) {
        this.type = type;
        this.arrayType = arrayType;
        this.matrix = Array.newInstance(type, width * height);
        this.width = width;
        this.height = height;

        for (int i = 0; i < width * height; i++) {
            Array.set(this.matrix, i, fill);
        }
    }

    public O getValue(int x, int y) {
        Object o = Array.get(this.matrix, x + y * width);
        return this.type.cast(o);
    }

    public void setValue(int x, int y, O value) {
        Array.set(this.matrix, x + y * width, value);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public O[] getMatrix() {
        return arrayType.cast(this.matrix);
    }

    public String toPrettyString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < this.height; i++) {
            builder.append('[');
            for (int j = 0; j < this.width; j++)
                builder.append(getValue(j, i)).append("  ");
            builder.replace(builder.length() - 2, builder.length(), "]\n");
        }
        return builder.substring(0, builder.length() - 1);
    }

    @Override
    public String toString() {
        return "IntMatrix{" +
                "type=" + type +
                ", arrayType=" + arrayType +
                ", matrix=" + Arrays.toString(arrayType.cast(matrix)) +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
