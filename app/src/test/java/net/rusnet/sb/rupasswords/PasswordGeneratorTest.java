package net.rusnet.sb.rupasswords;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordGeneratorTest {
    PasswordGenerator generator;

    @Before
    public void setUp() throws Exception {
        generator = new PasswordGenerator();
    }

    @Test
    public void generatePassword() {
        for (int length = 3; length < 10; length++) {
            assertEquals(length, generator.generatePassword(
                    length,
                    false,
                    false,
                    false).length());

            assertEquals(length, generator.generatePassword(
                    length,
                    true,
                    false,
                    false).length());

            assertEquals(length, generator.generatePassword(
                    length,
                    true,
                    true,
                    false).length());

            assertEquals(length, generator.generatePassword(
                    length,
                    true,
                    true,
                    true).length());
        }
        assertEquals("", generator.generatePassword(0,false,false, false));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArgumentThrows() {
        generator.generatePassword(1,true, true, false);
    }
}