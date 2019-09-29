package net.rusnet.sb.rupasswords;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordStrengthCheckerTest {
    public static final String[] INPUTS = {
            "",
            "asdfasdf",
            "asdfasdA",
            "asdfasd1",
            "asdfasd.",

            "asdfasA1",
            "asdfasA.",
            "asdfas1.",

            "asdfqA1.",

            "asdfaA",
            "asdfA.",
            "asdf1A.",

            "asd",
            "a",
            "aA1."
    };

    public static final PasswordStrength[] RESULTS = {
            PasswordStrength.EMPTY,
            PasswordStrength.MEDIUM,
            PasswordStrength.NORMAL,
            PasswordStrength.NORMAL,
            PasswordStrength.NORMAL,

            PasswordStrength.GOOD,
            PasswordStrength.GOOD,
            PasswordStrength.GOOD,

            PasswordStrength.GREAT,

            PasswordStrength.MEDIUM,
            PasswordStrength.MEDIUM,
            PasswordStrength.MEDIUM,

            PasswordStrength.LOW,
            PasswordStrength.LOW,
            PasswordStrength.LOW
    };

    private PasswordStrengthChecker checker;

    @Before
    public void setUp() throws Exception {
        checker = new PasswordStrengthChecker();
    }

    @Test
    public void checkPasswordStrength() {
        assertTrue("Error in test case", INPUTS.length == RESULTS.length);
        for (int i = 0; i < INPUTS.length; i++) {
            assertEquals(RESULTS[i], checker.checkPasswordStrength(INPUTS[i]));
        }

    }
}