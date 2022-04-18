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

public class PositiveTest extends BaseTest {

    private HomePage homePage;
    private UserData userData;

    @BeforeMethod(alwaysRun = true)
    public void openLoginPage() {
        homePage = BasePage.initPage(HomePage.class);
        homePage.clearData();
        userData = UserDataSets.randomUserData();
    }

    @Test(dataProvider = "validUserData")
    public void fillUserDataTest(UserData userData) {
        homePage.fillUserData(userData);
        homePage.clickSubmitButton();
        homePage.assertSuccessResult(userData);
        homePage.acceptAlert();
    }

    @Test
    public void fillEmptyUserDataThenCorrectUserDataTest() {
        homePage.clickSubmitButton();
        homePage.validateErrors();
        homePage.fillUserData(userData);
        homePage.validateNoErrorsShown();
        homePage.clickSubmitButton();
        homePage.assertSuccessResult(userData);
        homePage.acceptAlert();
    }

    @DataProvider
    public static Object[][] validUserData() {
        List<UserData> validUsers = List.of(
                UserDataSets.randomUserData(),
                UserDataSets.randomShortUserData(),
                UserDataSets.randomLongUserData(),
                UserDataSets.randomSpecialCharsUserData());

        return validUsers.stream().map(userData -> new Object[]{userData}).toArray(Object[][]::new);
    }
}
