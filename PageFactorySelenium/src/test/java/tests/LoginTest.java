/**
 * 
 */
package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import basetest.BaseTest;

/**
 * @author amisharm25
 *
 */
public class LoginTest extends BaseTest {

	@Test
	public void verifyLoginTest() {
		System.out.println("==== "+getDefaultUserName()+getDefaultPassword());
		
		login = login.doLoginAsValidUser(getDefaultUserName(), getDefaultPassword());

		Assert.assertTrue(login.isSuccessLogin());

		result=	login.searchProduct("PRINTED DRESS");
	}

}
