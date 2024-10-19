import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.definitions.PagesDefinition;
import pages.specificPages.AuthenticationPage;
import pages.specificPages.CartPage;
import pages.specificPages.InventoryPage;
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

    @Test
    public void testAddOneProductToCart() {
        new InventoryPage(driver).addToCart(true);
        driver.get(PagesDefinition.CART_PAGE);
        
        int actualNumberOfProduts = new CartPage(driver).getNumberOfProductsFromCart();
        Assert.assertEquals(actualNumberOfProduts, 1, "Not expected number of products in the Cart. \n" +
                "The expected number should be: 1, " + "but found: " + actualNumberOfProduts);

        new CartPage(driver).removeAllProductsFromCart();
    }

    @Test
    public void testAddMultipleProductsToCart() {
        new InventoryPage(driver).addToCart(false);
        driver.get(PagesDefinition.CART_PAGE);

        int actualNumberOfProduts = new CartPage(driver).getNumberOfProductsFromCart();
        Assert.assertEquals(actualNumberOfProduts, 6, "Not expected number of products in the Cart. \n" +
                "The expected number should be: 6, " + "but found: " + actualNumberOfProduts);

        new CartPage(driver).removeAllProductsFromCart();
    }

    @AfterMethod
    public void logout() {
        if (!driver.getCurrentUrl().equalsIgnoreCase(PagesDefinition.HOME_PAGE_AFTER_LOGIN)) {
            driver.navigate().back();
        }

        authenticationPage.logout();
    }

    @AfterTest
    public void quit(){
        driver.quit();
    }
}
