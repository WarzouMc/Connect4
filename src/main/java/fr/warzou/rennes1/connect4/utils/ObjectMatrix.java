package fr.warzou.rennes1.connect4.utils;

import fr.warzou.rennes1.connect4.utils.math.MathValidator;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

/**
 * Allow to create a matrix of some object.
 * <br>
 * This not a math matrix, just a bi-dimensional array.
 * @param <O> object type
 */
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

        for (int i = 0; i < width * height; i++)
            Array.set(this.matrix, i, fill);
    }

    /**
     * @param x target x
     * @param y target y
     * @return value a target x and y position
     */
    public O getValue(int x, int y) {
        checkIndexAccess(x, y);
        Object o = Array.get(this.matrix, x + y * width);
        return this.type.cast(o);
    }

    public void setValue(int x, int y, O value) {
        checkIndexAccess(x, y);
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

    private void checkIndexAccess(int x, int y) {
        MathValidator.inInterval(x, 0, this.width, true, false);
        MathValidator.inInterval(y, 0, this.height, true, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        ObjectMatrix<?> that = (ObjectMatrix<?>) o;

        if (width != that.width)
            return false;
        if (height != that.height)
            return false;
        if (!Objects.equals(type, that.type))
            return false;
        if (!Objects.equals(arrayType, that.arrayType))
            return false;
        return Arrays.equals(this.arrayType.cast(matrix), this.arrayType.cast(that.matrix));
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (arrayType != null ? arrayType.hashCode() : 0);
        result = 31 * result + (matrix != null ? matrix.hashCode() : 0);
        result = 31 * result + width;
        result = 31 * result + height;
        return result;
    }

    @Override
    public String toString() {
        return "ObjectMatrix{" +
                "type=" + type +
                ", arrayType=" + arrayType +
                ", matrix=" + Arrays.toString(arrayType.cast(matrix)) +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
