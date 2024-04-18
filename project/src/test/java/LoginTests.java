import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import pages.definitions.LoginPageDefinition;
import pages.definitions.PagesDefinition;
import pages.specificPages.AuthenticationPage;
import singleton.WebDriverSingleton;
import utilities.Accounts;
import utilities.Functions;

import java.util.List;

public class LoginTests {
    WebDriver driver;
    AuthenticationPage authenticationPage;
    Accounts accounts;

    @BeforeTest
    public void setUp() {
        driver = WebDriverSingleton.getInstance();
        authenticationPage = new AuthenticationPage(driver);
    }

    @BeforeMethod
    public void openLoginPage() {
        driver.get(PagesDefinition.LOGIN_PAGE);
    }

    @Test
    public void testElementsPresence() {
        String actualPage = driver.getCurrentUrl();
        Assert.assertTrue(actualPage.equalsIgnoreCase(PagesDefinition.LOGIN_PAGE), "Not expected initial page\n"
                + "The expected page should be: " + PagesDefinition.LOGIN_PAGE + " but found: " + actualPage);

        String pageTitle = driver.findElement(By.cssSelector(LoginPageDefinition.TITLE_PATH)).getText();
        Assert.assertTrue(pageTitle.equalsIgnoreCase(LoginPageDefinition.TITLE_VALUE), "Not expected title on the current page.\n"
                + "The expected title should be: " + LoginPageDefinition.TITLE_VALUE + " but found: " + pageTitle);
    }

    @Test
    public void testLoginWithoutCredentials() {
        authenticationPage.login("", "");

        String actualMessage = driver.findElement(By.xpath(LoginPageDefinition.CREDENTIALS_ERROR_PATH)).getText();
        Assert.assertTrue(actualMessage.equalsIgnoreCase(LoginPageDefinition.NO_CREDENTIALS_ERROR_MESSAGE), "Not expected message when no credentials are provided. \n" +
                "The expected message should be: " + LoginPageDefinition.NO_CREDENTIALS_ERROR_MESSAGE + " but found: " + actualMessage);
    }

    @Test
    public void testLoginWithWrongCredentials() {
        List<String> usernames = new Accounts().getUsernames();
        String password = new Accounts().getPassword();

        String randomPassword = new Functions().createRandomString();
        String randomUsername = new Functions().createRandomString();

        authenticationPage.login(usernames.get(0), randomPassword);
        String actualMessage = driver.findElement(By.xpath(LoginPageDefinition.CREDENTIALS_ERROR_PATH)).getText();
        Assert.assertTrue(actualMessage.equalsIgnoreCase(LoginPageDefinition.WRONG_CREDENTIALS_MESSAGE), "Not expected message when wrong password is provided. \n" +
                "The expected message should be: " + LoginPageDefinition.WRONG_CREDENTIALS_MESSAGE + " but found: " + actualMessage);

        authenticationPage.login(randomUsername, password);
        actualMessage = driver.findElement(By.xpath(LoginPageDefinition.CREDENTIALS_ERROR_PATH)).getText();
        Assert.assertTrue(actualMessage.equalsIgnoreCase(LoginPageDefinition.WRONG_CREDENTIALS_MESSAGE), "Not expected message when wrong password is provided. \n" +
                "The expected message should be: " + LoginPageDefinition.WRONG_CREDENTIALS_MESSAGE + " but found: " + actualMessage);
    }

    @Test
    public void testLoginWithCorrectCredentials() throws Exception {
        List<String> usernames = new Accounts().getUsernames();
        String password = new Accounts().getPassword();

        for (String username: usernames) {
            switch (username){
                case "standard_user":
                case "problem_user":
                case "error_user":
                case "visual_user":
                    authenticationPage.login(username, password);
                    Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(PagesDefinition.HOME_PAGE_AFTER_LOGIN), "Not expected page after Log-In with SUCCESS. \n" +
                            "The expected page should be: " + PagesDefinition.HOME_PAGE_AFTER_LOGIN + " but found: " + driver.getCurrentUrl());
                    authenticationPage.logout();
                    break;

                case "locked_out_user":
                    authenticationPage.login(username, password);
                    String actualMessage = driver.findElement(By.xpath(LoginPageDefinition.CREDENTIALS_ERROR_PATH)).getText();
                    Assert.assertTrue(actualMessage.equalsIgnoreCase(LoginPageDefinition.LOCKED_OUT_USER_MESSAGE), "Not expected message when the user: "
                            + username + " is provided\n" + "The expected message should be: " + LoginPageDefinition.LOCKED_OUT_USER_MESSAGE + " but found: " + actualMessage);
                    break;

                case "performance_glitch_user":
                    authenticationPage.login(username, password);
                    Thread.sleep(10000);
                    Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(PagesDefinition.HOME_PAGE_AFTER_LOGIN), "Not expected page after Log-In with SUCCESS. \n" +
                            "The expected page should be: " + PagesDefinition.HOME_PAGE_AFTER_LOGIN + " but found: " + driver.getCurrentUrl());
                    authenticationPage.logout();
                    break;

                default:
                    throw new Exception("Not expected user!");
            }
        }
    }

    @AfterTest
    public void quit(){
        driver.quit();
    }
}
