/**
 * 
 */
package tests;

import org.testng.annotations.Test;

import basetest.BaseTest;

/**
 * @author amisharm25
 *
 */
public class ResultPageTest extends BaseTest {
	@Test(priority=0)
	public void verifyProductDetails() {
		result.verifyProductDetailsResultPage();
	}
	
	@Test(priority=1)
	public void verifyExcelData() throws Exception {
		result.publishData();
	}

}
