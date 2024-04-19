package pages.definitions;

public class InventoryPageDefinition {
    public static String INVENTORY_LIST_SELECTOR = ".inventory_list .inventory_item";
    public static String INVENTORY_ITEM_NAME_SELECTOR = ".inventory_item_label .inventory_item_name";
    public static String INVENTORY_ITEM_DESCRIPTION_SELECTOR = ".inventory_item_label .inventory_item_desc";
    public static String INVENTORY_ITEM_PRICE_SELECTOR = ".pricebar .inventory_item_price";
    public static String INVENTORY_ITEM_ADD_TO_CART_XPATH = "//button[@class='btn btn_primary btn_small btn_inventory ']";
    public static String INVENTORY_ITEM_REMOVE_FROM_CART_XPATH = "//button[@class='btn btn_secondary btn_small btn_inventory ']";
    public static String SHOPPING_CART_SELECTOR = "#shopping_cart_container > a > span";
}
