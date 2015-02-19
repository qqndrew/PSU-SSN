package edu.pdx.ssn.pages;

import edu.pdx.ssn.pages.types.AdminPage;
import edu.pdx.ssn.pages.types.CatalogPage;
import edu.pdx.ssn.pages.types.CheckoutPage;
import edu.pdx.ssn.pages.types.IndexPage;
import edu.pdx.ssn.pages.types.LogoutPage;

import java.util.HashMap;

public class PageManager {
    private static HashMap<String, ServerPage> pages;
    private static String defaultPage;

    static {
        pages = new HashMap<>();
        defaultPage = "idx";
        registerPages();
    }

    // Do all registration of valid applications here
    private static void registerPages() {
        pages.put("idx", new IndexPage());
        pages.put("catalog", new CatalogPage());
        pages.put("checkout", new CheckoutPage());
        pages.put("admin", new AdminPage());
        pages.put("logout", new LogoutPage());
    }

    public static ServerPage getPage(String key) {
        return pages.containsKey(key) ? pages.get(key) : (key == null ? pages.get(defaultPage) : getNotFound(key));
    }

    public static ServerPage getNotFound(String key) {
        return ErrPageManager.getErrorPage(404, key);
    }
}
