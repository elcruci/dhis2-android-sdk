package org.hisp.dhis.rules.functions;

import org.hisp.dhis.rules.RuleVariableValue;

import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Rounds the input argument down to the nearest whole number.
 */
final class RuleFunctionFloor extends RuleFunction {
    static final String D2_FLOOR = "d2:floor";

    @Nonnull
    static RuleFunctionFloor create() {
        return new RuleFunctionFloor();
    }

    @Nonnull
    @Override
    public String evaluate(@Nonnull List<String> arguments,
            Map<String, RuleVariableValue> valueMap) {
        if (arguments == null) {
            throw new IllegalArgumentException("One argument is expected");
        } else if (arguments.size() != 1) {
            throw new IllegalArgumentException("One argument was expected, " +
                    arguments.size() + " were supplied");
        }

        return String.valueOf(new Double(Math.floor(toDouble(arguments.get(0), 0.0))).intValue());
    }

    private static double toDouble(@Nullable final String str, final double defaultValue) {
        if (str == null) {
            return defaultValue;
        }

        try {
            return Double.parseDouble(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }
}
