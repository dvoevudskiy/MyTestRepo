package models;

import framework.RandomDataGenerator;

public class UserDataSets {
    public static UserData randomUserData() {
        return new UserData(
                RandomDataGenerator.getRandomString(6),
                RandomDataGenerator.getRandomString(10),
                RandomDataGenerator.getRandomEmail(),
                RandomDataGenerator.getRandomPhone(10),
                Gender.Male,
                true);
    }

    public static UserData randomShortUserData() {
        return new UserData(
                RandomDataGenerator.getRandomString(2),
                RandomDataGenerator.getRandomString(2),
                RandomDataGenerator.getRandomEmail(),
                RandomDataGenerator.getRandomPhone(7),
                Gender.Female,
                true);
    }

    public static UserData randomLongUserData() {
        return new UserData(
                RandomDataGenerator.getRandomString(25),
                RandomDataGenerator.getRandomString(25),
                RandomDataGenerator.getRandomEmail(),
                RandomDataGenerator.getRandomPhone(12),
                Gender.Male,
                true);
    }

    public static UserData randomSpecialCharsUserData() {
        return new UserData(
                RandomDataGenerator.getRandomStringSpecialChar(12),
                RandomDataGenerator.getRandomStringSpecialChar(12),
                RandomDataGenerator.getRandomEmail(),
                RandomDataGenerator.getRandomPhone(11),
                Gender.Male,
                true);
    }

    public static UserData emptyUserData() {
        return new UserData();
    }

    public static UserData tooShortUserData() {
        return new UserData(
                RandomDataGenerator.getRandomString(1),
                RandomDataGenerator.getRandomString(1),
                RandomDataGenerator.getRandomString(1),
                RandomDataGenerator.getRandomPhone(1),
                Gender.None,
                false);
    }

    public static UserData tooLongUserData() {
        return new UserData(
                RandomDataGenerator.getRandomString(26),
                RandomDataGenerator.getRandomString(26),
                RandomDataGenerator.getRandomString(10) + "@",
                RandomDataGenerator.getRandomPhone(13),
                Gender.None,
                false);
    }

    public static UserData invalidEmailPhone() {
        UserData userData = new UserData();
        userData.setEmail(RandomDataGenerator.getRandomString(8) + "@" + RandomDataGenerator.getRandomString(5));
        userData.setPhoneNumber(RandomDataGenerator.getRandomStringSpecialChar(10));
        return userData;
    }

    public static UserData invalidEmailAndSpacebars() {
        return new UserData(" ",
                " ",
                RandomDataGenerator.getRandomString(8) + "@" + RandomDataGenerator.getRandomString(5) + ".",
                " ",
                Gender.None,
                false
        );
    }
}
