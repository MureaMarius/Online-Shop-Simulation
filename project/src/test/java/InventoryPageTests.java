import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.definitions.PagesDefinition;
import pages.specificPages.AuthenticationPage;
import pages.specificPages.InventoryPage;
import singleton.WebDriverSingleton;
import utilities.Accounts;

public class InventoryPageTests {
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

    @Test
    public void testAddOneProductToCart() {
        new InventoryPage(driver).addToCart(true);
        String actualNumberOfProductsAdded = new InventoryPage(driver).getNumberOfProductsAdded();

        Assert.assertEquals(actualNumberOfProductsAdded, "1", "Not expected number of products after " +
                "adding them into the cart.\n The expected number should be: 1" + " but found: " + actualNumberOfProductsAdded);

        new InventoryPage(driver).removeFromCart(true);
        Assert.assertEquals(actualNumberOfProductsAdded, "0", "Not expected number of products after " +
                "removing them into the cart.\n The expected number should be: 0" + " but found: " + actualNumberOfProductsAdded);
    }

    @Test
    public void testAddMultipleProductsToCart() {
        new InventoryPage(driver).addToCart(false);
        String actualNumberOfProductsAdded = new InventoryPage(driver).getNumberOfProductsAdded();

        Assert.assertEquals(actualNumberOfProductsAdded, "6", "Not expected number of products after " +
                "adding them into the cart.\n The expected number should be: 6" + " but found: " + actualNumberOfProductsAdded);

        new InventoryPage(driver).removeFromCart(false);
        Assert.assertEquals(actualNumberOfProductsAdded, "0", "Not expected number of products after " +
                "removing them into the cart.\n The expected number should be: 0" + " but found: " + actualNumberOfProductsAdded);
    }

    @AfterMethod
    public void logout() {
        authenticationPage.logout();
    }

    @AfterTest
    public void quit(){
        driver.quit();
    }
}
