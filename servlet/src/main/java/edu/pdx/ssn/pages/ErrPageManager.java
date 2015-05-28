package edu.pdx.ssn.pages;

import edu.pdx.ssn.pages.types.FourOhFourPage;

import java.util.HashMap;

public class ErrPageManager {

    private static HashMap<Integer, ServerPage> map;

    static {
        map = new HashMap<>();
        map.put(404, new FourOhFourPage());
    }

    public static ServerPage getErrorPage(int code) {
        return map.get(code);
    }
}
