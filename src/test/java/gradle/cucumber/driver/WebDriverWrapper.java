package gradle.cucumber.driver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.SystemClock;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class WebDriverWrapper {
    private WebDriver driver = new FirefoxDriver();

    public void visit(String url) {
        driver.get(url);
    }

    public void closeAll() {
        driver.close();
        driver.quit();
    }

    public void isAtURL(String url) {
        assertTrue(driver.getCurrentUrl().equals(url));
    }

    public UiElement findElementByName(String name) {
        return new SeleniumWebElement(driver.findElement(By.name(name)));
    }

    public void text_field(String field_name, String text) {
        UiElement e = findElementByName(field_name);
        e.sendKeys(text);
    }

    public void click_button(String button_name) {
        UiElement e = findElementById(button_name);
        e.click();
    }

    public void clickXPath(String xpath) {
        driver.findElement(By.xpath(xpath)).click();
    }

    public UiElement findElementById(String id) {
        return new SeleniumWebElement(driver.findElement(By.id(id)));
    }

    public void expectAlert(String msg) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        assertEquals(msg, alert.getText());
        alert.accept();
    }

    public void expectRedirect(String url) {
        WebDriverWait wait = new WebDriverWait(driver, 2);
        wait.until(ExpectedConditions.urlContains(url));
    }

    public void pageShouldContain(String text) {
        String bodyText = driver.findElement(By.tagName("body")).getText();
        assertTrue("Text not found!", bodyText.contains(text));
    }

    public void expectElementWithIdToContainText(String id, String text) {
        assertTrue("Text not found!", findElementById(id).getText().contains(text));
    }

    public void expectElementWithIdToContainValue(String id, int value) {
        assertEquals(Integer.toString(value), findElementById(id).getText());
    }

    public void expectElementWithIdGreaterThanOrEqualsValue(String id, int value) {
        assertTrue(Integer.parseInt(findElementById(id).getText()) >= value);
    }

    public void expectElementWithIdToContainTextInXSeconds(String id, String value, int seconds) {
        WebDriverWait wait = new WebDriverWait(driver, seconds);
        wait.until(ExpectedConditions.attributeToBe(By.id(id), "innerHTML", value));
    }

    public void expectPageToContainExactlyNElements(String text, int count) {
        List<WebElement> elements = driver.findElements(By.xpath("//*[contains(text(),'"+text+"')]"));
        assertEquals(elements.size(), count);
    }

    public int countElementWithClass(String cssClass) {
        return driver.findElements(By.className(cssClass)).size();
    }

    public String getElementMarginWithClass(String cssClass) { return driver.findElement(By.className(cssClass)).getCssValue("margin-left"); }
}

