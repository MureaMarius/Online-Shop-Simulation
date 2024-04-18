package pages.definitions;

public class LoginPageDefinition {
    public static String USERNAME_FORM_ID = "user-name";
    public static String PASSWORD_FORM_ID = "password";
    public static String LOGIN_BUTTON_ID = "login-button";
    public static String MENU_SELECTOR = "#menu_button_container > div > div:nth-child(1) > div";
    public static String LOGOUT_BUTTON_SELECTOR= "#logout_sidebar_link";
    public static String TITLE_PATH = "#root > div > div.login_logo";
    public static String CREDENTIALS_ERROR_PATH = "//*[@id=\"login_button_container\"]/div/form/div[3]";

    public static String TITLE_VALUE = "Swag Labs";
    public static String NO_CREDENTIALS_ERROR_MESSAGE = "Epic sadface: Username is required";
    public static String WRONG_CREDENTIALS_MESSAGE = "Epic sadface: Username and password do not match any user in this service";
    public static String LOCKED_OUT_USER_MESSAGE = "Epic sadface: Sorry, this user has been locked out.";
}
