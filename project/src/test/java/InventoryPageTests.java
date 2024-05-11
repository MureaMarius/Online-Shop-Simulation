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

import java.util.ArrayList;
import java.util.List;

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
    public void testTitleOnHeader() {
        String actualTitle = driver.findElement(By.cssSelector(InventoryPageDefinition.TITLE_HEADER_SELECTOR)).getText();
        String expectedTitle = InventoryPageDefinition.TITLE_HEADER_VALUE;

        Assert.assertEquals(actualTitle, expectedTitle, "Not expected title on the header inventory page. The expected " +
                "title should be: " + expectedTitle + " but found: " + actualTitle);
    }

    @Test
    public void testNumberOfItemsInTheMenuElements() {
        int actualNumberOfMenuItems = new InventoryPage(driver).getNumberOfMenuItems();
        Assert.assertEquals(actualNumberOfMenuItems, 4, "Not expected number of items in the menu section. " +
                "The expected number should be: 4. But found: " + actualNumberOfMenuItems);
    }

    @Test
    public void testResetAppState() throws InterruptedException {
        new InventoryPage(driver).resetAppStateFunction();

        String actualNumberOfProductsAdded = new InventoryPage(driver).getNumberOfProductsAdded();
        Assert.assertEquals(actualNumberOfProductsAdded, "0", "Not expected number of products after " +
                "adding them into the cart.\n The expected number should be: 0" + " but found: " + actualNumberOfProductsAdded);
    }

    @Test
    public void testRedirectAllItemsPage() throws Exception {
        new InventoryPage(driver).redirectPages("All Items");

        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(PagesDefinition.HOME_PAGE_AFTER_LOGIN), "Not expected page " +
                "after clicking on the \"All Items\" button. The expected page should: " + PagesDefinition.HOME_PAGE_AFTER_LOGIN + " but found: " +
                driver.getCurrentUrl());
    }

    @Test
    public void testRedirectAboutPage() throws Exception {
        new InventoryPage(driver).redirectPages("About");

        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(PagesDefinition.OFFICIAL_SAUCE_LABS_PAGE), "Not expected page " +
                "after clicking on the \"About\" button. The expected page should: " + PagesDefinition.OFFICIAL_SAUCE_LABS_PAGE + " but found: " +
                driver.getCurrentUrl());
    }

    @Test
    public void testRedirectCartPage() throws Exception {
        new InventoryPage(driver).redirectPages("Cart");

        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(PagesDefinition.CART_PAGE), "Not expected page " +
                "after clicking on the \"About\" button. The expected page should: " + PagesDefinition.CART_PAGE + " but found: " +
                driver.getCurrentUrl());
    }

    @Test
    public void testRedirectToTwitterPage() throws Exception {
        new InventoryPage(driver).redirectPages("TWITTER");

        List<String> browserTabs = new ArrayList<> (driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(1));
        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(PagesDefinition.TWITTER_PAGE), "Not expected page " +
                "after clicking on the \"TWITTER\" button. The expected page should: " + PagesDefinition.TWITTER_PAGE + " but found: " +
                driver.getCurrentUrl());

        driver.switchTo().window(browserTabs.get(0));
    }

    @Test
    public void testRedirectToFacebookPage() throws Exception {
        new InventoryPage(driver).redirectPages("FACEBOOK");

        List<String> browserTabs = new ArrayList<> (driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(1));
        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(PagesDefinition.FACEBOOK_PAGE), "Not expected page " +
                "after clicking on the \"FACEBOOK\" button. The expected page should: " + PagesDefinition.FACEBOOK_PAGE + " but found: " +
                driver.getCurrentUrl());

        driver.switchTo().window(browserTabs.get(0));
    }

    @Test
    public void testRedirectToLinkedinPage() throws Exception {
        new InventoryPage(driver).redirectPages("LINKEDIN");

        List<String> browserTabs = new ArrayList<> (driver.getWindowHandles());
        driver.switchTo().window(browserTabs.get(1));
        Assert.assertTrue(driver.getCurrentUrl().equalsIgnoreCase(PagesDefinition.LINKEDIN_PAGE), "Not expected page " +
                "after clicking on the \"LINKEDIN\" button. The expected page should: " + PagesDefinition.LINKEDIN_PAGE + " but found: " +
                driver.getCurrentUrl());

        driver.switchTo().window(browserTabs.get(0));
    }

    @Test
    public void testFilterByNameAscendingOrder() throws Exception {
        Assert.assertTrue(new InventoryPage(driver).filteringProducts("ASCENDING NAME"), "Not expected order for " +
                "the products when filtering by Name in ascending order is selected!");
    }

    @Test
    public void testFilterByNameDescendingOrder() throws Exception {
        Assert.assertTrue(new InventoryPage(driver).filteringProducts("DESCENDING NAME"), "Not expected order for " +
                "the products when filtering by Name in descending order is selected!");
    }

    @Test
    public void testFilterByPriceAscendingOrder() throws Exception {
        Assert.assertTrue(new InventoryPage(driver).filteringProducts("ASCENDING PRICE"), "Not expected order for " +
                "the products when filtering by Price in ascending order is selected!");
    }

    @Test
    public void testFilterByPriceDescendingOrder() throws Exception {
        Assert.assertTrue(new InventoryPage(driver).filteringProducts("DESCENDING PRICE"), "Not expected order for " +
                "the products when filtering by Price in descending order is selected!");
    }

    @Test
    public void testFooterText() {
        String actualFooterText = driver.findElement(By.cssSelector(InventoryPageDefinition.FOOTER_TEXT_SELECTOR)).getText();

        Assert.assertEquals(actualFooterText, InventoryPageDefinition.FOOTER_TEXT, "Not expected footer text. The" +
                " expected text should be: " + InventoryPageDefinition.FOOTER_TEXT + ", but found: " + actualFooterText);
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
