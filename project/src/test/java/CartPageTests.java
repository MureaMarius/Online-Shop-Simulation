import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.definitions.PagesDefinition;
import pages.specificPages.AuthenticationPage;
import singleton.WebDriverSingleton;
import utilities.Accounts;

public class CartPageTests {
    WebDriver driver;
    AuthenticationPage authenticationPage;
    Accounts accounts;

    @BeforeTest
    public void setUp() {
        driver = WebDriverSingleton.getInstance();
        authenticationPage = new AuthenticationPage(driver);
        accounts = new Accounts();
    }

    @BeforeMethod
    public void openInventoryPage() {
        driver.get(PagesDefinition.LOGIN_PAGE);
        authenticationPage.login(accounts.getUsernames().get(0), accounts.getPassword());
    }



    @AfterMethod
    public void logout() {
        if (!driver.getCurrentUrl().equalsIgnoreCase(PagesDefinition.HOME_PAGE_AFTER_LOGIN)){
            driver.navigate().back();
        }

        authenticationPage.logout();
    }

    @AfterTest
    public void quit(){
        driver.quit();
    }
}
