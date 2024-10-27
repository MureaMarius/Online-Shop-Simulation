import models.Product;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.definitions.PagesDefinition;
import pages.specificPages.AuthenticationPage;
import pages.specificPages.CartPage;
import pages.specificPages.InventoryPage;
import singleton.WebDriverSingleton;
import utilities.Accounts;
import java.util.List;

import static pages.definitions.PagesDefinition.HOME_PAGE_AFTER_LOGIN;

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

        driver.get(HOME_PAGE_AFTER_LOGIN);
        new InventoryPage(driver).removeFromCart(true);
    }

    @Test
    public void testAddMultipleProductsToCart() {
        new InventoryPage(driver).addToCart(false);
        driver.get(PagesDefinition.CART_PAGE);

        int actualNumberOfProduts = new CartPage(driver).getNumberOfProductsFromCart();
        Assert.assertEquals(actualNumberOfProduts, 6, "Not expected number of products in the Cart. \n" +
                "The expected number should be: 6, " + "but found: " + actualNumberOfProduts);

        driver.get(HOME_PAGE_AFTER_LOGIN);
        new InventoryPage(driver).removeFromCart(false);
    }

    @Test
    public void testRedirectToShoppingPage() {
        driver.get(PagesDefinition.CART_PAGE);
        new CartPage(driver).goBackToShopping();

        Assert.assertEquals(driver.getCurrentUrl(), HOME_PAGE_AFTER_LOGIN, "Not expected page after clicking the \"Continue shopping\" button. " +
                "Check this feature manually");
    }

    @Test
    public void testRemoveProductsFromCart() {
        new InventoryPage(driver).addToCart(false);
        driver.get(PagesDefinition.CART_PAGE);

        new CartPage(driver).removeAllProductsFromCart();

        int actualNumberOfProduts = new CartPage(driver).getNumberOfProductsFromCart();
        Assert.assertEquals(actualNumberOfProduts, 0, "Not expected number of products in the Cart. \n" +
                "The expected number should be: 0, " + "but found: " + actualNumberOfProduts);
    }

    @Test
    public void testListOfProductsAdded() {
        InventoryPage inventoryPage = new InventoryPage(driver);
        CartPage cartPage = new CartPage(driver);

        inventoryPage.addToCart(false);
        inventoryPage.extractProductsDetails();

        List<Product> expectedProducts = inventoryPage.getProducts();

        driver.get(PagesDefinition.CART_PAGE);

        cartPage.extractProductsDetails();
        List<Product> actualProducts = cartPage.getProducts();

        for (int i = 0; i < expectedProducts.size(); i++) {
            Assert.assertEquals(expectedProducts.get(i).getProductName(), actualProducts.get(i).getProductName());
            Assert.assertEquals(expectedProducts.get(i).getProductDescription(), actualProducts.get(i).getProductDescription());
            Assert.assertEquals(expectedProducts.get(i).getProductPrice(), actualProducts.get(i).getProductPrice());
        }
    }

    @AfterMethod
    public void logout() {
        if (!driver.getCurrentUrl().equalsIgnoreCase(HOME_PAGE_AFTER_LOGIN)) {
            driver.navigate().back();
        }

        authenticationPage.logout();
    }

    @AfterTest
    public void quit(){
        driver.quit();
    }
}
