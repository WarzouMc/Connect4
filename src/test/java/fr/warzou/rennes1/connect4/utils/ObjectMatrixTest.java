package fr.warzou.rennes1.connect4.utils;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ObjectMatrixTest {

    @Test
    void setValue() {
        ObjectMatrix<Integer> matrix = new ObjectMatrix<>(Integer.class, Integer[].class, 2, 3, 0);
        matrix.setValue(0, 2, -1);
        matrix.setValue(1, 0, 2);
        assertArrayEquals(new Object[] {0, 2, 0, 0, -1, 0}, matrix.getMatrix());
    }

    @Test
    void toPrettyString() {
        ObjectMatrix<Integer> matrix = new ObjectMatrix<>(Integer.class, Integer[].class, 2, 3, 0);
        matrix.setValue(0, 2, -1);
        matrix.setValue(1, 0, 2);
        String string = """
                [0  2]
                [0  0]
                [-1  0]""";
        assertEquals(string, matrix.toPrettyString());
    }

    @Test
    void testEquals() {
        ObjectMatrix<Integer> matrix = new ObjectMatrix<>(Integer.class, Integer[].class, 2, 3, 0);
        matrix.setValue(0, 2, -1);
        matrix.setValue(1, 0, 2);

        ObjectMatrix<Integer> equal = new ObjectMatrix<>(Integer.class, Integer[].class, 2, 3, 0);
        equal.setValue(0, 2, -1);
        equal.setValue(1, 0, 2);

        ObjectMatrix<Integer> nonEqual = new ObjectMatrix<>(Integer.class, Integer[].class, 2, 3, 0);
        nonEqual.setValue(0, 2, -1);
        nonEqual.setValue(1, 1, 7);
        nonEqual.setValue(1, 0, 2);

        assertEquals(equal, matrix);
        assertNotEquals(nonEqual, matrix);
    }
}