package pages.specificPages;

import models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.definitions.CartPageDefinition;
import pages.definitions.InventoryPageDefinition;
import pages.definitions.PagesDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class CartPage {
    WebDriver webDriver;
    private List<Product> products;

    private final By cartItemsList = By.cssSelector(CartPageDefinition.cartItemListSelector);
    private final By inventoryList = By.cssSelector(InventoryPageDefinition.INVENTORY_LIST_SELECTOR);
    private final By removeFromCartButton = By.xpath(InventoryPageDefinition.INVENTORY_ITEM_REMOVE_FROM_CART_XPATH);

    public CartPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        products = new ArrayList<>();
    }

    public int getNumberOfProductsFromCart() {
        return webDriver.findElements(cartItemsList).size();
    }

    public void removeAllProductsFromCart() {
        webDriver.get(PagesDefinition.HOME_PAGE_AFTER_LOGIN);

        List<WebElement> listOfProductsElements = webDriver.findElements(inventoryList);
        for (WebElement webElement: listOfProductsElements) {
            if(!webElement.findElements(removeFromCartButton).isEmpty()) {
                webElement.findElement(removeFromCartButton).click();
            }
        }
    }
}