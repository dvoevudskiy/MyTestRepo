package framework;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomDataGenerator {
    public static String getRandomString(int length)
    {
        return RandomStringUtils.randomAlphabetic(length);
    }

    public static String getRandomStringSpecialChar(int length)
    {
        return RandomStringUtils.randomAscii(length);
    }

    public static String getRandomEmail()
    {
        return String.format("%s@%s.%s", RandomStringUtils.randomAlphabetic(8), RandomStringUtils.randomAlphabetic(5), RandomStringUtils.randomAlphabetic(3));
    }

    public static String getRandomPhone(Integer length)
    {
        return RandomStringUtils.randomNumeric(length);
    }
}
