package net.rusnet.sb.rupasswords;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordsHelperTest {

    public static final String[] RUS = {"й", "ц", "у", "к", "е", "н"};
    public static final String[] ENG = {"q", "w", "e", "r", "t", "y"};

    public static final String[] SOURCES = {
      "",
      "некуцй",
      "ЙЦУКЕН",
      "зщшгне"
    };

    public static final String[] RESULTS = {
       "",
       "ytrewq",
       "QWERTY",
       "зщшгyt"
    };

    private PasswordsHelper helper;

    @Before
    public void setUp() throws Exception {
        helper = new PasswordsHelper(RUS, ENG);
    }

    @Test
    public void testConvert() {
        //assertEquals("", helper.convert(""));
        //assertEquals("a", helper.convert("")); //провалит тест

        //проверка самого теста
        assertTrue("Error in test case", SOURCES.length == RESULTS.length);

        for (int i = 0; i < SOURCES.length; i++) {
            assertEquals(RESULTS[i], helper.convert(SOURCES[i]));
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void convertIsThrows() {
        new PasswordsHelper(RUS, new String[] {});
    }

    @Test(expected =  NullPointerException.class)
    public void testNullThrows() {
        helper.convert(null);
    }

}