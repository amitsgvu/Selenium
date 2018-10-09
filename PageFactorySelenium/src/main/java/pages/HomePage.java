/**
 * 
 */
package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import baepage.BasePage;
import drivermanager.DriverManager;

/**
 * @author amisharm25
 * @param <T>
 * 
 *
 */
@SuppressWarnings("rawtypes")
public class HomePage extends BasePage {

	@FindBy(css = "a.login") // changing
	public WebElement signInButton;

	public HomePage open(String url) {

		System.out.println("Page Opened");
		DriverManager.getDriver().navigate().to(url);
		return (HomePage) openPage(HomePage.class);
	}

	@Override
	protected ExpectedCondition getPageLoadCondition() {
		// TODO Auto-generated method stub
		return ExpectedConditions.visibilityOf(signInButton);
	}

	public LoginPage gotoLogin() {
		System.out.println("inside go to login");
		highLightElement(signInButton);
		click(signInButton, "Login Link");
		scrollDown();
		return (LoginPage) openPage(LoginPage.class);

	}
}
