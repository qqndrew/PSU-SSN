package edu.pdx.ssn.pages;

import edu.pdx.ssn.pages.types.AdminPage;
import edu.pdx.ssn.pages.types.CatalogPage;
import edu.pdx.ssn.pages.types.CheckoutPage;
import edu.pdx.ssn.pages.types.DetailsPage;
import edu.pdx.ssn.pages.types.IndexPage;
import edu.pdx.ssn.pages.types.LoginPage;
import edu.pdx.ssn.pages.types.LogoutPage;
import edu.pdx.ssn.pages.types.MemberPage;
import edu.pdx.ssn.pages.types.RegisterPage;

import java.util.HashMap;

public class PageManager {
    public static final String DEFAULT_KEY = "idx";
    private static HashMap<String, ServerPage> pages;

    static {
        pages = new HashMap<>();
        registerPages();
    }

    // Do all registration of valid applications here
    private static void registerPages() {
        pages.put(DEFAULT_KEY, new IndexPage());
        pages.put(CatalogPage.PAGE_KEY, new CatalogPage());
        pages.put(CheckoutPage.PAGE_KEY, new CheckoutPage());
        pages.put(AdminPage.PAGE_KEY, new AdminPage());
        pages.put(LogoutPage.PAGE_KEY, new LogoutPage());
        pages.put(DetailsPage.PAGE_KEY, new DetailsPage());
        pages.put(LoginPage.PAGE_KEY, new LoginPage());
        pages.put(RegisterPage.PAGE_KEY, new RegisterPage());
        pages.put(MemberPage.PAGE_KEY, new MemberPage());
    }

    public static ServerPage getPage(String key) {
        return pages.containsKey(key) ? pages.get(key) : (key == null ? pages.get(DEFAULT_KEY) : ErrPageManager.getErrorPage(404));
    }

}
