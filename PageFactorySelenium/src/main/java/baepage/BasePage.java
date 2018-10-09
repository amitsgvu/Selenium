/**
 * 
 */
package baepage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import drivermanager.DriverManager;
import listeners.ExtentListeners;

/**
 * @author amisharm25
 * @param <T>
 *
 */
public abstract class BasePage<T> {
	protected WebDriver driver;

	private long LOAD_TIMEOUT = 30;
	private int AJAX_ELEMENT_TIMEOUT = 10;

	public BasePage() {
		this.driver = DriverManager.getDriver();
	}

	public T openPage(Class<T> clazz) {
		T page = null;
		try {
			driver = DriverManager.getDriver();
			AjaxElementLocatorFactory ajaxElemFactory = new AjaxElementLocatorFactory(driver, AJAX_ELEMENT_TIMEOUT);
			page = PageFactory.initElements(driver, clazz);
			PageFactory.initElements(ajaxElemFactory, page);
			ExpectedCondition pageLoadCondition = ((BasePage) page).getPageLoadCondition();
			waitForPageToLoad(pageLoadCondition);
		} catch (NoSuchElementException e) {
			/*
			 * String error_screenshot = System.getProperty("user.dir") +
			 * "\\target\\screenshots\\" + clazz.getSimpleName() + "_error.png";
			 * this.takeScreenShot(error_screenshot);
			 */ throw new IllegalStateException(String.format("This is not the %s page", clazz.getSimpleName()));
		}
		return page;
	}

	private void waitForPageToLoad(ExpectedCondition pageLoadCondition) {
		WebDriverWait wait = new WebDriverWait(driver, LOAD_TIMEOUT);
		wait.until(pageLoadCondition);
	}

	// implemented in all page class
	protected abstract ExpectedCondition getPageLoadCondition();

	public void click(WebElement element, String elementName) {

		element.click();
		ExtentListeners.testReport.get().info("Clicking on : " + elementName);

	}

	public void type(WebElement element, String value) {
		element.click();
		element.clear();
		element.sendKeys(value);
		ExtentListeners.testReport.get().info("Typing in : " + element + " entered the value as : " + value);

	}

	public static void highLightElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {

			System.out.println(e.getMessage());
		}

		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);

	}

	public void scrollDown() {
		JavascriptExecutor js = ((JavascriptExecutor) DriverManager.getDriver());
		js.executeScript("window.scrollBy(0,400)", "");
	}

	public String getText(WebElement element) {
		ExtentListeners.testReport.get().info("Getting Text value of  : " + element);
		return element.getText().trim();
	}
}
