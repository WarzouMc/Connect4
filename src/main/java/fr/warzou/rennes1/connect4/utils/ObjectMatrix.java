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

        fillMatrix(fill);
    }

    /**
     * Returns value at a target index in matrix.
     * @param index target index
     * @return value at a target index
     */
    public O getValue(int index) {
        int[] indexes = toIndexes(index);
        return getValue(indexes[0], indexes[1]);
    }

    /**
     * Returns value at a target index in matrix.
     * @param x target x
     * @param y target y
     * @return value at a target x and y position
     */
    public O getValue(int x, int y) {
        checkIndexAccess(x, y);
        Object o = Array.get(this.matrix, x + y * width);
        return this.type.cast(o);
    }

    /**
     * Set a value at a target index in matrix.
     * @param index target index
     * @param value new value to set
     */
    public void setValue(int index, O value) {
        int[] indexes = toIndexes(index);
        setValue(indexes[0], indexes[1], value);
    }

    /**
     * Set a value at a target index in matrix.
     * @param x target x
     * @param y target y
     * @param value new value to set
     */
    public void setValue(int x, int y, O value) {
        checkIndexAccess(x, y);
        Array.set(this.matrix, x + y * width, value);
    }

    /**
     * Fill this matrix with a specific value.
     * @param fill value
     */
    public void fillMatrix(O fill) {
        for (int i = 0; i < this.width * this.height; i++)
            setValue(i, fill);
    }

    /**
     * Returns matrix width.
     * @return matrix width
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Returns matrix height.
     * @return matrix height
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Returns this matrix into a unidimensional array.
     * @return this matrix
     */
    public O[] getMatrix() {
        return arrayType.cast(this.matrix);
    }

    /**
     * Returns a formatted string of matrix values.
     * @return formatted matrix values
     */
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
    public String toString() {
        return "ObjectMatrix{" +
                "type=" + type +
                ", arrayType=" + arrayType +
                ", matrix=" + Arrays.toString(arrayType.cast(matrix)) +
                ", width=" + width +
                ", height=" + height +
                '}';
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

    /**
     * Parse an index to a couple of indexes
     * @param index from index
     * @return indexes
     */
    private int[] toIndexes(int index) {
        int x = index % this.width;
        index -= x;
        int y = index / this.width;
        return new int[] {x, y};
    }

    /**
     * Check if targets index exist for this matrix.
     * @param x x index
     * @param y y index
     */
    private void checkIndexAccess(int x, int y) {
        MathValidator.inInterval(x, 0, this.width, true, false);
        MathValidator.inInterval(y, 0, this.height, true, false);
    }
}
