package fr.warzou.rennes1.connect4.utils.math.exception;

/**
 * Just an exception throw when a validation fail into {@link fr.warzou.rennes1.connect4.utils.math.MathValidator}.
 *
 * @see fr.warzou.rennes1.connect4.utils.math.MathValidator
 */
public class MathValidatorException extends RuntimeException {

    /**
     * @param message target message
     * @param args message argument
     */
    public MathValidatorException(String message, Object... args) {
        super(String.format(message, args));
    }

}
