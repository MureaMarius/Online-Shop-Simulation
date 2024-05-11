package pages.specificPages;

import models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.definitions.InventoryPageDefinition;
import utilities.Functions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage {
    WebDriver webDriver;
    private List<Product> products;

    private final By inventoryList = By.cssSelector(InventoryPageDefinition.INVENTORY_LIST_SELECTOR);
    private final By addToCartButton = By.xpath(InventoryPageDefinition.INVENTORY_ITEM_ADD_TO_CART_XPATH);
    private final By removeFromCartButton = By.xpath(InventoryPageDefinition.INVENTORY_ITEM_REMOVE_FROM_CART_XPATH);
    private final By shoppingCartProductsAdded = By.cssSelector(InventoryPageDefinition.SHOPPING_CART_SELECTOR);

    private final By allItemsButton = By.cssSelector(InventoryPageDefinition.ALL_ITEMS_BUTTON);
    private final By aboutButton = By.cssSelector(InventoryPageDefinition.ABOUT_BUTTON);
    private final By resetAppStateButton = By.cssSelector(InventoryPageDefinition.RESET_APP_STATE_BUTTON);

    private final By filterByNameAscOrder = By.cssSelector(InventoryPageDefinition.FILTER_ASCENDING_ORDER_NAME_SELECTOR);
    private final By filterByNameDescOrder = By.cssSelector(InventoryPageDefinition.FILTER_DESCENDING_ORDER_NAME_SELECTOR);
    private final By filterByPriceAscOrder = By.cssSelector(InventoryPageDefinition.FILTER_ASCENDING_ORDER_PRICE_SELECTOR);
    private final By filterByPriceDescOrder = By.cssSelector(InventoryPageDefinition.FILTER_DESCENDING_ORDER_PRICE_SELECTOR);

    private final By twitterRedirectButton = By.cssSelector(InventoryPageDefinition.TWITTER_REDIRECT_BUTTON);
    private final By facebookRedirectButton = By.cssSelector(InventoryPageDefinition.FACEBOOK_REDIRECT_BUTTON);
    private final By linkedinRedirectButton = By.cssSelector(InventoryPageDefinition.LINKEDIN_REDIRECT_BUTTON);
    private final By textFooter = By.cssSelector(InventoryPageDefinition.FOOTER_TEXT_SELECTOR);

    public InventoryPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        products = new ArrayList<>();
    }

    public void addToCart(boolean oneProduct) {
        List<WebElement> listOfProductsElements = webDriver.findElements(inventoryList);

        if(oneProduct) {
            listOfProductsElements.get(0).findElement(addToCartButton).click();
        }
        else {
            for (WebElement webElement: listOfProductsElements) {
                webElement.findElement(addToCartButton).click();
            }
        }
    }

    public void removeFromCart(boolean oneProduct) {
        List<WebElement> listOfProductsElements = webDriver.findElements(inventoryList);

        if (oneProduct) {
            listOfProductsElements.get(0).findElement(removeFromCartButton).click();
        }
        else {
            for (WebElement webElement: listOfProductsElements) {
                webElement.findElement(removeFromCartButton).click();
            }
        }
    }

    public String getNumberOfProductsAdded() {
        if (!webDriver.findElements(shoppingCartProductsAdded).isEmpty() && !webDriver.findElement(shoppingCartProductsAdded).getText().equalsIgnoreCase("")) {
            return webDriver.findElement(shoppingCartProductsAdded).getText();
        }
        else {
            return "0";
        }
    }

    public int getNumberOfMenuItems() {
        webDriver.findElement(By.cssSelector(InventoryPageDefinition.MENU_INVENTORY_PAGE_SELECTOR));
        return webDriver.findElement(By.cssSelector(InventoryPageDefinition.MENU_INVENTORY_PAGE_LIST_SELECTOR)).
                findElements(By.cssSelector("a")).size();
    }

    public void resetAppStateFunction() throws InterruptedException {
        webDriver.findElements(inventoryList).get(0).findElement(addToCartButton).click();
        Thread.sleep(3000);

        webDriver.findElement(By.cssSelector(InventoryPageDefinition.MENU_INVENTORY_PAGE_SELECTOR)).click();
        webDriver.findElement(resetAppStateButton).click();
        webDriver.navigate().refresh();
    }

    public void redirectPages(String page) throws Exception {
        switch (page) {
            case "All Items":
                webDriver.findElement(By.cssSelector(InventoryPageDefinition.MENU_INVENTORY_PAGE_SELECTOR)).click();
                webDriver.findElement(allItemsButton).click();
                webDriver.findElement(By.cssSelector(InventoryPageDefinition.CLOSE_MENU_BUTTON)).click();
                break;

            case "About":
                webDriver.findElement(By.cssSelector(InventoryPageDefinition.MENU_INVENTORY_PAGE_SELECTOR)).click();
                webDriver.findElement(aboutButton).click();
                break;

            case "Cart":
                webDriver.findElement(shoppingCartProductsAdded).click();
                break;

            case "TWITTER":
                webDriver.findElement(twitterRedirectButton).click();
                break;

            case "FACEBOOK":
                webDriver.findElement(facebookRedirectButton).click();
                break;

            case "LINKEDIN":
                webDriver.findElement(linkedinRedirectButton).click();
                break;

            default:
                throw new Exception("Not expected page!");
        }

        Thread.sleep(5000);
    }

    public boolean filteringProducts(String filterType) throws Exception {
        switch (filterType){
            case "ASCENDING NAME":
                webDriver.findElement(filterByNameAscOrder).click();
                break;

            case "DESCENDING NAME":
                webDriver.findElement(filterByNameDescOrder).click();
                break;

            case "ASCENDING PRICE":
                webDriver.findElement(filterByPriceAscOrder).click();
                break;

            case "DESCENDING PRICE":
                webDriver.findElement(filterByPriceDescOrder).click();
                break;

            default:
                throw new Exception("Invalid filtering option!");
        }

        List<WebElement> listOfProductsElements = webDriver.findElements(inventoryList);

        for (WebElement webElement: listOfProductsElements) {
            String productName = webElement.findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_NAME_SELECTOR)).getText();
            String productDescription = webElement.findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_DESCRIPTION_SELECTOR)).getText();
            String productPrice = webElement.findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_PRICE_SELECTOR)).getText();

            Product product = new Product(productName, productDescription, Float.parseFloat(productPrice.substring(1)));
            products.add(product);
        }

        List<String> names = products.stream().map(Product::getProductName).collect(Collectors.toList());
        List<Float> prices = products.stream().map(Product::getProductPrice).collect(Collectors.toList());

        switch (filterType) {
            case "ASCENDING NAME":
                if (Functions.isSortedNames(names, true)){
                    return true;
                }

            case "DESCENDING NAME":
                if (Functions.isSortedNames(names, false)) {
                    return true;
                }

            case "ASCENDING PRICE":
                if (Functions.isSortedPrices(prices, true)) {
                    return true;
                }

            case "DESCENDING PRICE":
                if (Functions.isSortedPrices(prices, false)) {
                    return true;
                }
        }

        return false;
    }
}