/**
 * 
 */
package pages;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import baepage.BasePage;
import productdetailsresult.ProductPrice;
import utilities.WriteExecl;

/**
 * @author amisharm25
 *
 */
public class ResultPage extends BasePage {
	@FindBys({ @FindBy(xpath = "//div[@class='right-block']//div[@class='content_price']") })
	List<WebElement> resultcountProduct;
	@FindBy(css = "div.center_column.col-xs-12.col-sm-9")
	WebElement resultPageDiv;
	public static ArrayList<ProductPrice> list = new ArrayList<ProductPrice>();
	public static ArrayList<ProductPrice> maplist;

	public static Map<Integer, List<String>> map = new TreeMap<Integer, List<String>>();

	public void verifyProductDetailsResultPage() {
		// TODO Auto-generated method stub

		// System.out.println("total count is" +
		// Integer.parseInt(Character.toString(resultPageCount.getText().charAt(0))));

		// SeleniumHelper.waitForElement(resultcountProduct);
		System.out.println("total image of product is" + resultcountProduct.size());
		// productPrice;
		Integer serialNumber = 1;
		for (WebElement elediv : resultcountProduct) {

			ProductPrice productPrice = new ProductPrice();
			ArrayList<String> maplist = new ArrayList<String>();

			// System.out.println("total span for" +
			// elediv.findElements(By.tagName("span")).size());
			List<WebElement> elespan = elediv.findElements(By.tagName("span"));

			for (WebElement ele : elespan) {

				if (ele.getAttribute("class").equals("price product-price")) {
					productPrice.setNewPrice(ele.getText());
					maplist.add(ele.getText());
				}

				if (ele.getAttribute("class").contains("old")) {
					productPrice.setOldPrice(ele.getText());
					maplist.add(ele.getText());

				}
				if (ele.getAttribute("class").contains("reduction")) {
					productPrice.setDiscount(ele.getText());

					maplist.add(ele.getText());
				}

			}
			list.add(productPrice);
			
			map.put(serialNumber, maplist);
			serialNumber++;
		}

	}

	public void publishData() throws Exception {
		String excelPath=System.getProperty("user.dir")+"\\src\\test\\resources\\data.xlsx";
		WriteExecl.writetoexcel(excelPath, map);
	}
	

	@Override
	protected ExpectedCondition getPageLoadCondition() {
		// TODO Auto-generated method stub
		return ExpectedConditions.visibilityOf(resultPageDiv);
	}

}
