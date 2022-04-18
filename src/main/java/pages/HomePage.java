package pages;

import framework.BasePage;
import framework.Utils;
import framework.WebDriverUtils;
import models.Gender;
import models.UserData;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class HomePage extends BasePage {

    @FindBy(name = FIRST_NAME_NAME)
    public WebElement firstNameInput;
    private static final String FIRST_NAME_NAME = "FirstName";

    @FindBy(name = LAST_NAME_NAME)
    WebElement lastNameInput;
    private static final String LAST_NAME_NAME = "LastName";

    @FindBy(name = EMAIL_NAME)
    WebElement emaiInput;
    private static final String EMAIL_NAME = "Email";

    @FindBy(name = PHONE_NUMBER_NAME)
    WebElement phoneNumberInput;
    private static final String PHONE_NUMBER_NAME = "PhoneNumber";

    @FindBy(xpath = MALE_RADIOBUTTON_XPATH)
    WebElement maleRadioButton;
    private static final String MALE_RADIOBUTTON_XPATH = "//input[@value='Male']";

    @FindBy(xpath = FEMALE_RADIOBUTTON_XPATH)
    WebElement femaleRadioButton;
    private static final String FEMALE_RADIOBUTTON_XPATH = "//input[@value='Female']";

    @FindBy(name = AGREEMENT_NAME)
    WebElement agreementCheckbox;
    private static final String AGREEMENT_NAME = "Agreement";

    @FindBy(name = SUBMIT_NAME)
    WebElement submitButton;
    private static final String SUBMIT_NAME = "submitbutton";

    @FindBy(xpath = FIRST_NAME_ERROR_XPATH)
    WebElement firstNameError;
    private static final String FIRST_NAME_ERROR_XPATH = "//input[@name='FirstName']/following-sibling::p[1]";

    @FindBy(xpath = LAST_NAME_ERROR_XPATH)
    WebElement lastNameError;
    private static final String LAST_NAME_ERROR_XPATH = "//input[@name='LastName']/following-sibling::p[1]";

    @FindBy(xpath = EMAIL_ERROR_XPATH)
    WebElement emailError;
    private static final String EMAIL_ERROR_XPATH = "//input[@name='Email']/following-sibling::p[1]";

    @FindBy(xpath = PHONE_NUMBER_ERROR_XPATH)
    WebElement phoneNumberError;
    private static final String PHONE_NUMBER_ERROR_XPATH = "//input[@name='PhoneNumber']/following-sibling::p[1]";

    @FindBy(xpath = GENDER_ERROR_XPATH)
    WebElement genderError;
    private static final String GENDER_ERROR_XPATH = "//label[text()='Gender']/following-sibling::p[1]";

    @FindBy(xpath = AGREEMENT_ERROR_XPATH)
    WebElement agreementError;
    private static final String AGREEMENT_ERROR_XPATH = "//input[@name='Agreement']/following-sibling::p[1]";

    public void clearData() {
        logger.info("Refreshing the page to clear inputs");
        driver.navigate().refresh();
        waitForElements();
        validateInputsEmpty();
    }

    public void fillUserData(UserData userData)
    {
        logger.info("Filling inputs with the following user data: " + Utils.UserDataToJson(userData));
        waitForElements();

        enterValueToInputIfNotNull(firstNameInput, userData.getFirstName());
        enterValueToInputIfNotNull(lastNameInput, userData.getLastName());
        enterValueToInputIfNotNull(emaiInput, userData.getEmail());
        enterValueToInputIfNotNull(phoneNumberInput, userData.getPhoneNumber());

        selectGenderRadioButton(userData);

        selectAgreementCheckbox(userData);
    }

    public void clickSubmitButton()
    {
        logger.info("Submitting user data");
        WebDriverUtils.waitForElementClickable(submitButton);
        submitButton.click();
    }

    public void assertSuccessResult(UserData userData) {
        logger.info("Validating alert");
        WebDriverUtils.waitForAlert();

        String expectedText = Utils.UserDataToJson(userData);
        String resultText = driver.switchTo().alert().getText();
        Assert.assertEquals(resultText, expectedText, "The result is not correct!");
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public void validateNoErrorsShown(){
        logger.info("Checking that no error messages are shown");
        Assert.assertFalse(WebDriverUtils.isElementDisplayed(firstNameError), "First name error message is displayed!");
        Assert.assertFalse(WebDriverUtils.isElementDisplayed(lastNameError), "Last name error message is displayed!");
        Assert.assertFalse(WebDriverUtils.isElementDisplayed(emailError), "Email error message is displayed!");
        Assert.assertFalse(WebDriverUtils.isElementDisplayed(phoneNumberError), "Phone number error message is displayed!");
        Assert.assertFalse(WebDriverUtils.isElementDisplayed(genderError), "Gender error message is displayed!");
        Assert.assertFalse(WebDriverUtils.isElementDisplayed(agreementError), "Agreement error message is displayed!");
    }

    public void validateErrors() {
        waitForErrors();

        logger.info("Validating error messages are correct");
        Assert.assertTrue(firstNameError.getText().contains(FIRST_NAME_ERROR_MESSAGE), "First name error message is incorrect!");
        Assert.assertTrue(lastNameError.getText().contains(LAST_NAME_ERROR_MESSAGE), "Last name error message is incorrect!");
        Assert.assertTrue(emailError.getText().contains(EMAIL_ERROR_MESSAGE), "Email error message is incorrect!");
        Assert.assertTrue(phoneNumberError.getText().contains(PHONE_NUMBER_ERROR_MESSAGE), "Phone number error message is incorrect!");
        Assert.assertTrue(genderError.getText().contains(GENDER_ERROR_MESSAGE), "Gender error message is incorrect!");
        Assert.assertTrue(agreementError.getText().contains(AGREEMENT_ERROR_MESSAGE), "Agreement error message is incorrect!");
    }

    private void waitForElements(){
        logger.info("Waiting for inputs to be present");
        WebDriverUtils.waitForElementPresent(firstNameInput);
        WebDriverUtils.waitForElementPresent(lastNameInput);
        WebDriverUtils.waitForElementPresent(emaiInput);
        WebDriverUtils.waitForElementPresent(phoneNumberInput);
        WebDriverUtils.waitForElementPresent(maleRadioButton);
        WebDriverUtils.waitForElementPresent(femaleRadioButton);
        WebDriverUtils.waitForElementPresent(agreementCheckbox);
        WebDriverUtils.waitForElementPresent(submitButton);
    }

    private void waitForErrors(){
        logger.info("Waiting for error messages to appear");
        WebDriverUtils.waitForElementPresent(firstNameError);
        WebDriverUtils.waitForElementPresent(lastNameError);
        WebDriverUtils.waitForElementPresent(emailError);
        WebDriverUtils.waitForElementPresent(phoneNumberError);
        WebDriverUtils.waitForElementPresent(genderError);
        WebDriverUtils.waitForElementPresent(agreementError);
    }

    private void validateInputsEmpty() {
        Assert.assertEquals(WebDriverUtils.getElementValue(firstNameInput), "", "First name input is not empty!");
        Assert.assertEquals(WebDriverUtils.getElementValue(lastNameInput), "", "Last name input is not empty!");
        Assert.assertEquals(WebDriverUtils.getElementValue(emaiInput), "", "Email input is not empty!");
        Assert.assertEquals(WebDriverUtils.getElementValue(phoneNumberInput), "", "Phone number input is not empty!");
        Assert.assertFalse(maleRadioButton.isSelected(), "Male radiobutton should not be selected!");
        Assert.assertFalse(femaleRadioButton.isSelected(), "Female radiobutton should not be selected!");
        Assert.assertFalse(agreementCheckbox.isSelected(), "Agreement checkbox should not be checked!");
    }

    private void enterValueToInputIfNotNull(WebElement inputToFill, String value)
    {
        if (value != null)
            inputToFill.sendKeys(value);
    }

    private void selectGenderRadioButton(UserData userData)
    {
        // changing value to avoid NPE
        if (userData.getGender() == null)
            userData.setGender(Gender.None);

        switch (userData.getGender()) {
            case Male -> maleRadioButton.click();
            case Female -> femaleRadioButton.click();
            case None -> {}
            default -> throw new RuntimeException("Unknown gender!");
        }
    }

    private void selectAgreementCheckbox(UserData userData) {
        if (userData.getAgreement() != null)
        {
            if (agreementCheckbox.isSelected() != userData.getAgreement())
                agreementCheckbox.click();
        }
    }

    private static final String FIRST_NAME_ERROR_MESSAGE = "Valid first name is required.";
    private static final String LAST_NAME_ERROR_MESSAGE = "Valid last name is required.";
    private static final String EMAIL_ERROR_MESSAGE = "Valid email is required.";
    private static final String PHONE_NUMBER_ERROR_MESSAGE = "Valid phone number is required.";
    private static final String GENDER_ERROR_MESSAGE = "Choose your gender.";
    private static final String AGREEMENT_ERROR_MESSAGE = "You must agree to the processing of personal data.";
}
