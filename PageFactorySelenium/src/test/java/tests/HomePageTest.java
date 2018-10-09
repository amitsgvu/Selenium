/**
 * 
 */
package tests;

import org.testng.annotations.Test;

import basetest.BaseTest;
import pages.HomePage;

/**
 * @author amisharm25
 *
 */
public class HomePageTest extends BaseTest {

	@Test
	public void verifyHomePageTest() {

		home = new HomePage().open(getTestsiteurl());
		login = home.gotoLogin();

	}
}
