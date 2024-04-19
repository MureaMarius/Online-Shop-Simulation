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

    public InventoryPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        products = new ArrayList<>();
    }

    public void addToCart(boolean oneProduct) {
        List<WebElement> listOfProductsElements = webDriver.findElements(inventoryList);

        if(oneProduct) {
            String productName = listOfProductsElements.get(0).findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_NAME_SELECTOR)).getText();
            String productDescription = listOfProductsElements.get(0).findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_DESCRIPTION_SELECTOR)).getText();
            String productPrice = listOfProductsElements.get(0).findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_PRICE_SELECTOR)).getText();

            Product product = new Product(productName, productDescription, productPrice);
            products.add(product);

            listOfProductsElements.get(0).findElement(addToCartButton).click();
        }
        else {
            for (WebElement webElement: listOfProductsElements) {
                String productName = webElement.findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_NAME_SELECTOR)).getText();
                String productDescription = webElement.findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_DESCRIPTION_SELECTOR)).getText();
                String productPrice = webElement.findElement(By.cssSelector(InventoryPageDefinition.INVENTORY_ITEM_PRICE_SELECTOR)).getText();

                Product product = new Product(productName, productDescription, productPrice);
                products.add(product);

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
        return  webDriver.findElement(shoppingCartProductsAdded).getText();
    }

}
