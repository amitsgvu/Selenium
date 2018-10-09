/**
 * 
 */
package pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import baepage.BasePage;

/**
 * @author amisharm25
 *
 */
public class LoginPage extends BasePage {

	@FindBy(linkText = "Sign in") // changing
	public WebElement signInButton;

	public @FindBy(id = "email") WebElement email;

	public @FindBy(id = "passwd") WebElement password;

	public @FindBy(id = "SubmitLogin") WebElement loginButton;
	public @FindBy(css = "input.search_query.form-control.ac_input") WebElement searchBox;
	public @FindBy(css = "button[name='submit_search']") WebElement searchIcon;

	@FindBy(css = "a.account>span")
	WebElement userNameText;

	public LoginPage doLoginAsValidUser(String username, String passwordvalue) {
		highLightElement(email);
		type(email, username);
		highLightElement(password);
		type(password, passwordvalue);
		highLightElement(loginButton);
		click(loginButton, "Sign in Button");

		return this;

	}

	public boolean isSuccessLogin() {
		if (getText(userNameText).contains("Cucumber Selenium blog"))
			return true;
		else
			return false;

	}

	public ResultPage searchProduct(String productName) {
		// TODO Auto-generated method stub
		highLightElement(searchBox);
		type(searchBox, productName);
		click(searchIcon, "Product Search Icon");

		return (ResultPage) openPage(ResultPage.class);
	}

	@Override
	protected ExpectedCondition getPageLoadCondition() {
		// TODO Auto-generated method stub
		return ExpectedConditions.elementToBeClickable(loginButton);
	}

}
