package pages.specificPages;

import models.Product;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.definitions.CartPageDefinition;

import java.util.ArrayList;
import java.util.List;

public class CartPage {
    WebDriver webDriver;
    private List<Product> products;

    private final By cartItemsList = By.cssSelector(CartPageDefinition.CART_ITEM_SELECTOR);
    private final By continueShoppingButton = By.cssSelector(CartPageDefinition.CONTINUE_SHOPPING_SELECTOR);
    private final By removeProductButton = By.cssSelector(CartPageDefinition.REMOVE_SELECTOR);
    private final By itemName = By.cssSelector(CartPageDefinition.ITEM_NAME);
    private final By itemPrice = By.cssSelector(CartPageDefinition.ITEM_PRICE);
    private final By itemDescription = By.cssSelector(CartPageDefinition.ITEM_DESCRIPTION);

    public CartPage(WebDriver webDriver) {
        this.webDriver = webDriver;
        products = new ArrayList<>();
    }

    public int getNumberOfProductsFromCart() {
        return webDriver.findElements(cartItemsList).size();
    }

    public void removeAllProductsFromCart() {
        List<WebElement> listOfProducts = webDriver.findElements(cartItemsList);
        for (WebElement productElement: listOfProducts) {
            productElement.findElement(removeProductButton).click();
        }
    }

    public void extractProductsDetails() {
        List<WebElement> listOfProducts = webDriver.findElements(cartItemsList);
        for (WebElement productElement: listOfProducts) {
            String name = productElement.findElement(itemName).getText();
            String price = productElement.findElement(itemPrice).getText();
            String description = productElement.findElement(itemDescription).getText();

            products.add(new Product(name, description, Float.parseFloat(price.substring(1))));
        }
    }

    public void goBackToShopping() {
        webDriver.findElement(continueShoppingButton).click();
    }

    public List<Product> getProducts() {
        return products;
    }
}