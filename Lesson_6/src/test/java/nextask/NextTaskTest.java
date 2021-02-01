package nextask;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

public class NextTaskTest {

    private static Stream<Arguments> arrayElementsWithFour() {
        return Stream.of(
                Arguments.arguments(new int[]{1, 0, 5, 7, 6}, new int[]{10, 2, 5, 4, 1, 0, 5, 7, 6}),
                Arguments.arguments(new int[]{9, 8, 8, 9, 1, 10, 3}, new int[]{10, 4, 0, 4, 9, 8, 8, 9, 1, 10, 3}),
                Arguments.arguments(new int[]{5, 6, 7, 8, 9, 10, 11}, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11}),
                Arguments.arguments(new int[]{10, 11}, new int[]{8, 4, 10, 11})
        );
    }

    private static Stream<Arguments> arrayElementsWithOneAndFourAndWithout() {
        return Stream.of(
                Arguments.arguments(true, new int[]{10, 2, 5, 4, 1, 0, 5, 7, 6}),
                Arguments.arguments(true, new int[]{10, 4, 0, 4, 9, 8, 8, 9, 1, 10, 3}),
                Arguments.arguments(true, new int[]{1, 2, 3, 8, 5, 6, 7, 8, 9, 10}),
                Arguments.arguments(false, new int[]{10, 2, 5, 9, 10, 0, 5, 7, 6}),
                Arguments.arguments(false, new int[]{10, 5, 0, 2, 9, 8, 8, 9, 2, 10, 3}),
                Arguments.arguments(false, new int[]{8, 2, 3, 8, 5, 6, 7, 8, 9, 10})

        );
    }

    @ParameterizedTest
    @MethodSource("arrayElementsWithFour")
    void shouldReturnAllArrayElementsAfterFour(int[] expected, int[] actual) {
        Assertions.assertEquals(Arrays.toString(expected), Arrays.toString(NextTask.newArray(actual)));
    }

    @Test
    void shouldThrowRuntimeExceptionWhenFourNotFoundInArray() {
        Assertions.assertThrows(java.lang.RuntimeException.class, () -> NextTask.newArray(new int[]{
                10, 2, 5, 1, 1, 0, 5, 7, 6
        }));
    }

    @ParameterizedTest
    @MethodSource("arrayElementsWithOneAndFourAndWithout")
    void shouldBeTrueIfThereIsOneOrFourInTheArray(boolean expected, int[] actual) {
        Assertions.assertEquals(expected, NextTask.chekForOneAndFour(actual));
    }

}
