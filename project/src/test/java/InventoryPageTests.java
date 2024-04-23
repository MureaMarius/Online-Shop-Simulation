import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.definitions.InventoryPageDefinition;
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
        actualNumberOfProductsAdded = new InventoryPage(driver).getNumberOfProductsAdded();
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
        actualNumberOfProductsAdded = new InventoryPage(driver).getNumberOfProductsAdded();
        Assert.assertEquals(actualNumberOfProductsAdded, "0", "Not expected number of products after " +
                "removing them into the cart.\n The expected number should be: 0" + " but found: " + actualNumberOfProductsAdded);
    }

    @Test
    public void testRemoveProductFromCart() {
        new InventoryPage(driver).addToCart(true);
        new InventoryPage(driver).removeFromCart(true);

        String actualNumberOfProductsAdded = new InventoryPage(driver).getNumberOfProductsAdded();
        Assert.assertEquals(actualNumberOfProductsAdded, "0", "Not expected number of products after " +
                "removing them into the cart.\n The expected number should be: 0" + " but found: " + actualNumberOfProductsAdded);
    }

    @Test
    public void testTitlePresenceOnInventoryPage() {
        String actualTitle = driver.findElement(By.cssSelector(InventoryPageDefinition.TITLE_INVENTORY_PAGE_SELECTOR)).getText();
        String expectedTitle = InventoryPageDefinition.TITLE_INVENTORY_PAGE;

        Assert.assertEquals(actualTitle, expectedTitle, "Not expected title on the inventory page. The expected " +
                "title should be: " + expectedTitle + " but found: " + actualTitle);
    }

    @Test
    public void testNumberOfItemsInTheMenuElements() {
        int actualNumberOfMenuItems = new InventoryPage(driver).getNumberOfMenuItems();
        Assert.assertEquals(actualNumberOfMenuItems, 4, "Not expected number of items in the menu section. " +
                "The expected number should be: 4. But found: " + actualNumberOfMenuItems);
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
