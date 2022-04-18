import framework.BasePage;
import framework.BaseTest;
import framework.Utils;
import models.UserData;
import models.UserDataSets;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.HomePage;

import java.util.List;

public class NegativeTest extends BaseTest {

    private HomePage homePage;

    @BeforeMethod(alwaysRun = true)
    public void openLoginPage() {
        homePage = BasePage.initPage(HomePage.class);
        homePage.clearData();
    }

    @Test(dataProvider = "invalidUserData")
    public void validateInvalidUserDataTest(UserData userData) {
        homePage.fillUserData(userData);
        homePage.clickSubmitButton();
        homePage.validateErrors();
    }

    @DataProvider
    public static Object[][] invalidUserData() {
        List<UserData> validUsers = List.of(
                UserDataSets.emptyUserData(),
                UserDataSets.tooShortUserData(),
                UserDataSets.tooLongUserData(),
                UserDataSets.invalidEmailPhone(),
                UserDataSets.invalidEmailAndSpacebars());

        return validUsers.stream().map(userData -> new Object[]{userData}).toArray(Object[][]::new);
    }
}
