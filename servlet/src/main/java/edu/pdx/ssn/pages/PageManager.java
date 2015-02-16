package edu.pdx.ssn.pages;

import java.util.HashMap;

public class PageManager {
    private static HashMap<String, String> pages;
    private static String defaultPage;

    static {
        pages = new HashMap<>();
        defaultPage = "idx";
        registerPages();
    }

    // Do all registration of valid applications here
    private static void registerPages() {
        pages.put("idx", "Main Page");
        pages.put("catalog", "Catalog");
        pages.put("checkout", "Book Checkout");
        pages.put("admin", "Administration Panel");
        pages.put("logout", "Logout");
    }

    public static String getPage(String key) {
        return pages.containsKey(key) ? key : (key == null ? defaultPage : getNotFound(key));
    }

    public static String getTitle(String key) {
        return pages.get(getPage(key));
    }

    public static String getNotFound(String key) {
        return ErrPageManager.getErrorPage(404, key);
    }
}
