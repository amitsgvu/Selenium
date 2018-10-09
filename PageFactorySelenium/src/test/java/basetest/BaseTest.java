package basetest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;

import driverfactory.DriverFactory;
import drivermanager.DriverManager;
import listeners.ExtentListeners;
import pages.HomePage;
import pages.LoginPage;
import pages.ResultPage;

public class BaseTest {

	private WebDriver driver;
	private static String testsiteurl = "";
	private static String chromeDriverPath;

	public static String getChromeDriverPath() {
		
		return chromeDriverPath;
	}

	public static void setChromeDriverPath(String chromeDriverPath) {
		BaseTest.chromeDriverPath = System.getProperty("user.dir")+chromeDriverPath;
	}

	public static String getGeckoDriverPath() {
		return geckoDriverPath;
	}

	public static void setGeckoDriverPath(String geckoDriverPath) {
		BaseTest.geckoDriverPath = geckoDriverPath;
	}

	public static String getIeDriverPath() {
		return ieDriverPath;
	}

	public static void setIeDriverPath(String ieDriverPath) {
		BaseTest.ieDriverPath = ieDriverPath;
	}

	private static String geckoDriverPath;
	private static String ieDriverPath;

	public String getTestsiteurl() {
		return testsiteurl;
	}

	public void setTestsiteurl(String testsiteurl) {
		this.testsiteurl = testsiteurl;
	}

	private Properties Config = new Properties();
	private FileInputStream fis;
	static protected HomePage home;
	static protected LoginPage login;
	static protected ResultPage result;

	public boolean grid = false;
	private static String defaultUserName = "";
	private static String defaultPassword = "";

	public String getDefaultUserName() {
		return defaultUserName;
	}

	public void setDefaultUserName(String defaultUserName) {

		this.defaultUserName = defaultUserName;
	}

	public String getDefaultPassword() {
		return defaultPassword;
	}

	public void setDefaultPassword(String defaultPassword) {
		this.defaultPassword = defaultPassword;
	}

	@BeforeSuite
	public void setUpFramework() {

		// configureLogging();
		DriverFactory.setGridPath("http://localhost:4444/wd/hub");
		DriverFactory.setConfigPropertyFilePath(
				System.getProperty("user.dir") + "//src//test//resources//properties//Config.properties");

		if (System.getProperty("os.name").equalsIgnoreCase("mac")) {

			DriverFactory.setChromeDriverExePath(
					System.getProperty("user.dir") + "//src//test//resources//executables//chromedriver");
			DriverFactory.setGeckoDriverExePath(
					System.getProperty("user.dir") + "//src//test//resources//executables//geckodriver");

		} else {

			DriverFactory.setChromeDriverExePath(System.getProperty("user.dir") + getChromeDriverPath());
			DriverFactory.setGeckoDriverExePath(System.getProperty("user.dir") + getGeckoDriverPath());
			DriverFactory.setIeDriverExePath(System.getProperty("user.dir") + getIeDriverPath());

		}
		/*
		 * Initialize properties Initialize logs load executables
		 * 
		 */
		try {
			fis = new FileInputStream(DriverFactory.getConfigPropertyFilePath());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Config.load(fis);
			// log.info("Config properties file loaded");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setUpTestdata();
		openBrowser("chrome");
		

	}

	public void logInfo(String message) {

		ExtentListeners.testReport.get().info(message);
	}

	public void destroyFramework() {

	}

	public void openBrowser(String browser) {

		if (System.getenv("ExecutionType") != null && System.getenv("ExecutionType").equals("Grid")) {

			grid = true;
		}

		DriverFactory.setRemote(grid);

		if (DriverFactory.isRemote()) {
			DesiredCapabilities cap = null;

			if (browser.equals("firefox")) {

				cap = DesiredCapabilities.firefox();
				cap.setBrowserName("firefox");
				cap.setPlatform(Platform.ANY);

			} else if (browser.equals("chrome")) {

				cap = DesiredCapabilities.chrome();
				cap.setBrowserName("chrome");
				cap.setPlatform(Platform.ANY);
			} else if (browser.equals("ie")) {

				cap = DesiredCapabilities.internetExplorer();
				cap.setBrowserName("iexplore");
				cap.setPlatform(Platform.WIN10);
			}

			try {
				driver = new RemoteWebDriver(new URL(DriverFactory.getGridPath()), cap);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else

		if (browser.equals("chrome")) {
			System.out.println("Launching : " + browser);
			System.setProperty("webdriver.chrome.driver",getChromeDriverPath());
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable--extensions");
			options.addArguments("--disable--infobars");
			driver = new ChromeDriver(options);
		} else if (browser.equals("firefox")) {
			System.out.println("Launching : " + browser);
			System.setProperty("webdriver.gecko.driver", DriverFactory.getGeckoDriverExePath());
			driver = new FirefoxDriver();

		}

		DriverManager.setWebDriver(driver);
		// log.info("Driver Initialized !!!");
		DriverManager.getDriver().manage().window().maximize();
		DriverManager.getDriver().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	public void setUpTestdata() {
		// TODO Auto-generated method stub
		setDefaultUserName(Config.getProperty("defaultUserName"));
		setDefaultPassword(Config.getProperty("defaultPassword"));
		setTestsiteurl(Config.getProperty("testsiteurl"));
		setChromeDriverPath(Config.getProperty("chromeDriverPath"));
		setGeckoDriverPath(Config.getProperty("geckoDriverPath"));
		setIeDriverPath(Config.getProperty("ieDriverPath"));
	}

	public void quit() {

		DriverManager.getDriver().quit();
		// log.info("Test Execution Completed !!!");
	}
}
