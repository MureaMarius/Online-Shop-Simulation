package pages.specificPages;

import models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.definitions.InventoryPageDefinition;

import java.util.ArrayList;
import java.util.List;

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

    public InventoryPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        products = new ArrayList<>();
    }

    public void addToCart(boolean oneProduct) {
        List<WebElement> listOfProductsElements = webDriver.findElements(inventoryList);

        if(oneProduct) {
/*            String productName = listOfProductsElements.get(0).findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_NAME_SELECTOR)).getText();
            String productDescription = listOfProductsElements.get(0).findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_DESCRIPTION_SELECTOR)).getText();
            String productPrice = listOfProductsElements.get(0).findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_PRICE_SELECTOR)).getText();

            Product product = new Product(productName, productDescription, productPrice);
            products.add(product);*/

            listOfProductsElements.get(0).findElement(addToCartButton).click();
        }
        else {
            for (WebElement webElement: listOfProductsElements) {
/*                String productName = webElement.findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_NAME_SELECTOR)).getText();
                String productDescription = webElement.findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_DESCRIPTION_SELECTOR)).getText();
                String productPrice = webElement.findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_PRICE_SELECTOR)).getText();

                Product product = new Product(productName, productDescription, productPrice);
                products.add(product);*/

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
        if (!webDriver.findElements(shoppingCartProductsAdded).isEmpty()) {
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

            default:
                throw new Exception("Not expected page!");
        }

        Thread.sleep(5000);
    }
}