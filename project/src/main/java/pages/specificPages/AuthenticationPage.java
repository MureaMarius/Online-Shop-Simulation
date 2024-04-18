package pages.specificPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.definitions.LoginPageDefinition;

import java.time.Duration;

public class AuthenticationPage {
    WebDriver webDriver;

    private final By usernameForm = By.id(LoginPageDefinition.USERNAME_FORM_ID);
    private final By passwordForm = By.id(LoginPageDefinition.PASSWORD_FORM_ID);
    private final By loginButton = By.id(LoginPageDefinition.LOGIN_BUTTON_ID);

    private final By menuButton = By.cssSelector(LoginPageDefinition.MENU_SELECTOR);
    private final By logoutButton = By.cssSelector(LoginPageDefinition.LOGOUT_BUTTON_SELECTOR);

    public AuthenticationPage(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void login(String username, String password) {
        this.setUsernameForm(username);
        this.setPasswordForm(password);

        this.clickLogin();
    }

    public void logout() {
        webDriver.findElement(menuButton).click();
        this.clickLogout();
    }

    public void setUsernameForm(String username){
        webDriver.findElement(usernameForm).clear();
        webDriver.findElement(usernameForm).sendKeys(username);
    }

    public void setPasswordForm(String password){
        webDriver.findElement(passwordForm).clear();
        webDriver.findElement(passwordForm).sendKeys(password);
    }

    public void clickLogin() {
        webDriver.findElement(loginButton).click();
    }

    public void clickLogout() {
        webDriver.findElement(logoutButton).click();
    }
}
