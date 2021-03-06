package gradle.cucumber.driver;

import java.util.ArrayList;

public class WebDriverFactory {
//    private static WebDriverWrapper driver;
    private static ArrayList<WebDriverWrapper> drivers = new ArrayList();

    public static WebDriverWrapper getDefaultDriver() {
        WebDriverWrapper driver;
        if(drivers.size()==0){
            driver = new WebDriverWrapper();
            drivers.add(driver);
        }

        else{
            driver = drivers.get(0);
        }

        return driver;
    }

    public static WebDriverWrapper getAdditionalDriver() {
        WebDriverWrapper driver = new WebDriverWrapper();
        drivers.add(driver);
        return driver;
    }

    public static void resetAll() {
        for (WebDriverWrapper driver : drivers) {
            driver.closeAll();
        }
        drivers = new ArrayList();
    }
}
