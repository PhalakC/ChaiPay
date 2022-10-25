import java.awt.RenderingHints.Key;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class chai_assignment {
	WebDriver driver;

	@BeforeTest // TestNG annotation
	@Parameters("browser")

	public void beforeTest(String browser) { // selecting Edge browser
		if (browser.equalsIgnoreCase("edge")) {

			System.setProperty("webdriver.edge.driver",
					"C:\\Users\\phalakc\\Downloads\\edgedriver_win64\\msedgedriver.exe");
			driver = new EdgeDriver();
		} else if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver",
					"C:\\Users\\phalakc\\Downloads\\chromedriver_win32\\chromedriver.exe");
			driver = new ChromeDriver();
		}
		driver.get("https://www.stage-page.chaiport.io/2AZX3ENuQMlYlNBlXfr7dcI5ngS");
		driver.manage().window().maximize();
	}

	@Test(priority = 1)
	// Verify that a valid drop down is available where the user can select
	// preferred language.

	public void dropdownCheck() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@id='mySelect']")).click();

		String listOption1 = driver.findElement(By.xpath("//body/div[@id='menu-']/div[3]/ul[1]/li[1]")).getText();
		System.out.println(listOption1);
		String listOption2 = driver.findElement(By.xpath("//body/div[@id='menu-']/div[3]/ul[1]/li[4]")).getText();
		Assert.assertEquals(true, listOption1.equalsIgnoreCase("english")); // language should be present
		Assert.assertEquals(false, listOption2.equalsIgnoreCase("Singapore")); // country name should not be present

	}

	@Test(priority = 2)
	// Verify that the language of the page changes when a particular language
	// is selected.

	public void langCheck() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@id='mySelect']")).click();

		driver.findElement(By.xpath("//body/div[@id='menu-']/div[3]/ul[1]/li[2]")).click();

		String text = driver.findElement(By.xpath("//h5[contains(text(),'Liên hệ chúng tôi')]")).getText();

		Assert.assertEquals(false, text.equalsIgnoreCase("Liên")); // validating the data displayed with the expected
																	// language

	}

	@Test(priority = 3)
	// Verify that leaving the "Amount" textbox blank gives an error message
	// and the Pay Now button is deactivated.

	public void buttonCheck() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		driver.findElement(By.id("amount")).sendKeys(Keys.BACK_SPACE, Keys.BACK_SPACE, Keys.BACK_SPACE);

		boolean click = driver.findElement(By.xpath("//button[@type='button']")).isEnabled();

		Assert.assertEquals(false, click); // button should be disabled
	}

	@Test(priority = 4)
	// Verify that clicking on the "Pay Now" button redirects to the final
	// payment page.

	public void urlCheck() throws Exception {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id("amount")).sendKeys("100");
		driver.findElement(By.xpath("//button[@type='button']")).click();
		Thread.sleep(1000);
		String url = driver.getCurrentUrl();

		Assert.assertEquals(true, url.contains("checkout"));
	}

	@Test(priority = 5)
	// Verify that the user is able to retrieve saved cards by clicking on the "Get
	// Saved Cards" button.

	public void savedCardsCheck() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.id("savedPaymentOptionWeb")).click();
		boolean phoneVisible = driver.findElement(By.id("enterphlang")).isDisplayed();

		Assert.assertEquals(true, phoneVisible);
	}

	@Test(priority = 6)
	// Verify that the user is able to select his/her country code from the
	// dropdown.

	public void phoneCheck() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.findElement(By.className("iti__selected-dial-code")).click();

		driver.findElement(By.xpath(
				"//li[@id='iti-1__item-in-preferred']//span[@class='iti__country-name'][contains(text(),'India (भारत)')]"))
				.click();

		String code = driver.findElement(By.className("iti__selected-dial-code")).getText();
		Assert.assertEquals(code, "+91"); // verifying if India code is selected on selecting India option

	}

	@Test(priority = 7)
	// Verify that the user cannot enter invalid phone number.

	public void phoneNoCheck() {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.id("Mobilephoneno")).sendKeys("abc");

		boolean isActive = driver.findElement(By.id("mobileToOtp")).isEnabled();

		Assert.assertEquals(isActive, false); // the "Next" button should not be active when an incorrect number is
												// entered

	}

	@Test(priority = 8)
	// Verifying that the user cannot leave the phone text box empty.

	public void phoneNoCheck2() {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.id("Mobilephoneno")).clear();
		driver.findElement(By.id("Mobilephoneno")).sendKeys("");

		boolean isActive = driver.findElement(By.id("mobileToOtp")).isEnabled();

		Assert.assertEquals(isActive, false); // the "Next" button should not be active when an number is left blank

	}

	@Test(priority = 9)
	// Verify that the user can enter only digits in the phone number text box and
	// not special characters.

	public void phoneNoCheck3() {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.findElement(By.id("Mobilephoneno")).sendKeys("%%%");

		boolean isActive = driver.findElement(By.id("mobileToOtp")).isEnabled();

		Assert.assertEquals(isActive, false); // the "Next" button should not be active when a special char is added

	}

	@Test(priority = 10)
	// Verify that "Guest Checkout" option is available as soon as "Get Saved Cards"
	// button is clicked.

	public void guestCheckout() {

		boolean isDisplayed = driver.findElement(By.id("guestCheckout")).isDisplayed();

		Assert.assertEquals(isDisplayed, true);

	}

	@Test(priority = 11)
	// Verify that the user is able to switch to the Guest Mode while checking out.

	public void guestCheckoutAvailable() {

		driver.findElement(By.id("guestCheckout")).click();
		boolean isDisplayed = driver.findElement(By.id("savedPaymentOptionWeb")).isDisplayed();
		Assert.assertEquals(isDisplayed, true); // "Get saved cards" should be displayed if switched from Guest mode

	}

	@Test(priority = 12)
	// Verify that the "verify" button in inactive and user is not able to retrieve
	// saved cards by entering the
	// incorrect otp.

	public void otpCheck() {
		driver.findElement(By.id("savedPaymentOptionWeb")).click();
		driver.findElement(By.id("Mobilephoneno")).clear();
		driver.findElement(By.id("Mobilephoneno")).sendKeys("97810000");
		driver.findElement(By.id("mobileToOtp")).click();

		driver.findElement(By.id("otp1")).sendKeys("0");
		driver.findElement(By.id("otp2")).sendKeys("0");
		driver.findElement(By.id("otp3")).sendKeys("0");
		driver.findElement(By.id("otp4")).sendKeys("0");
		driver.findElement(By.id("otp5")).sendKeys("0");
		driver.findElement(By.id("otp6")).sendKeys("0");
		boolean isActive = driver.findElement(By.id("otpToPayment")).isEnabled();
		Assert.assertEquals(isActive, false);
	}

	@Test(priority = 13)
	// Verifying that the Wallets option redirects to PayNow's payment page.

	public void walletCheck() throws Exception {
		driver.findElement(By.xpath("//a[@id='walletlang']")).click();
		driver.findElement(By.xpath("//input[@id='OMISE-OMISE_PAYNOW']")).click();
		driver.findElement(By.id("PayNowButtonWeb")).click();
		Thread.sleep(10000);

		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(true, title.contains("QR"));
	}

	@Test(priority = 14)
	// Verifying that the Wallets option redirects to Alipay's payment page.

	public void walletCheck1() throws Exception {
		driver.get("https://www.stage-page.chaiport.io/2AZX3ENuQMlYlNBlXfr7dcI5ngS");
		driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.xpath("//a[@id='walletlang']")).click();
		driver.findElement(By.xpath("//label[@for='ASIAPAY-ASIAPAY_ALIPAY']")).click();
		driver.findElement(By.id("PayNowButtonWeb")).click();
		Thread.sleep(10000);

		String url = driver.getCurrentUrl();
		System.out.println(url);
		Assert.assertEquals(true, url.contains("alipay"));
	}

	@Test(priority = 15)
	// Verifying that the Wallets option redirects to GrabPay's payment page.

	public void walletCheck2() throws Exception {
		driver.get("https://www.stage-page.chaiport.io/2AZX3ENuQMlYlNBlXfr7dcI5ngS");
		driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.xpath("//a[@id='walletlang']")).click();
		driver.findElement(By.xpath("//label[@for='ASIAPAY-ASIAPAY_GRABPAY']")).click();
		driver.findElement(By.id("PayNowButtonWeb")).click();
		Thread.sleep(10000);

		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(true, title.contains("paydollar"));
	}

	@Test(priority = 16)
	// Verifying that the Wallets option redirects to UnionPay's payment page.

	public void walletCheck3() throws Exception {
		driver.get("https://www.stage-page.chaiport.io/2AZX3ENuQMlYlNBlXfr7dcI5ngS");
		driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.xpath("//a[@id='walletlang']")).click();
		driver.findElement(By.xpath("//label[@for='ASIAPAY-ASIAPAY_UNIONPAY']")).click();
		driver.findElement(By.id("PayNowButtonWeb")).click();
		Thread.sleep(10000);

		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(true, title.contains("union"));
	}

	@Test(priority = 17)
	// Verifying that the Wallets option redirects to WeChatPay's payment page.

	public void walletCheck4() throws Exception {
		driver.get("https://www.stage-page.chaiport.io/2AZX3ENuQMlYlNBlXfr7dcI5ngS");
		driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.xpath("//a[@id='walletlang']")).click();
		driver.findElement(By.xpath("//label[@for='ASIAPAY-ASIAPAY_WECHATPAY']")).click();
		driver.findElement(By.id("PayNowButtonWeb")).click();
		Thread.sleep(10000);

		String title = driver.getTitle();
		System.out.println(title);
		Assert.assertEquals(true, title.contains("wechat"));
	}

	@Test(priority = 18)
	// Verifying that the BNPL option redirects to Atome's payment page.

	public void bnplCheck() throws Exception {
		driver.get("https://www.stage-page.chaiport.io/2AZX3ENuQMlYlNBlXfr7dcI5ngS");
		driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.xpath("//a[@id='bnpllang']")).click();
		driver.findElement(By.xpath("//label[@for='ATOME-ATOME_BNPL']")).click();
		driver.findElement(By.id("PayNowButtonWeb")).click();
		Thread.sleep(10000);

		String url = driver.getCurrentUrl();
		System.out.println(url);
		Assert.assertEquals(true, url.contains("atome"));
	}

	@Test(priority = 19)
	// Verifying the COD option

	public void codCheck() throws Exception {
		driver.get("https://www.stage-page.chaiport.io/2AZX3ENuQMlYlNBlXfr7dcI5ngS");
		driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.xpath("//div[@id='CodDirect']//a[@id='atmlangDirect']")).click();

		driver.findElement(By.id("PayNowButtonWeb")).click();
		Thread.sleep(10000);

		String url = driver.getCurrentUrl();
		System.out.println(url);
		Assert.assertEquals(true, url.contains("demo"));
	}

	@Test(priority = 20)
	// Verifying the Credit Card option

	public void creditCardCheck() throws Exception {
		driver.get("https://www.stage-page.chaiport.io/2AZX3ENuQMlYlNBlXfr7dcI5ngS");
		driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.xpath("//a[@id='creditcardlang']")).click();

		driver.findElement(By.xpath("//input[@id='CardNumber']")).sendKeys("1234123412341234"); // adding credit card
		driver.findElement(By.xpath("//input[@id='CardHolderName']")).sendKeys("phalak");
		driver.findElement(By.xpath("//input[@id='validthru']")).sendKeys("10/2022");
		driver.findElement(By.xpath("//input[@id='creditCvv']")).sendKeys("123");

		String errorText = driver.findElement(By.xpath("//span[@id='error12']")).getText();
		Assert.assertEquals(true, errorText.contains("error")); // entering wrong credit card details gives an error
																// message
	}

	@Test(priority = 21)
	// Verifying the Credit Card option

	public void creditCardCheck1() throws Exception {
		driver.get("https://www.stage-page.chaiport.io/2AZX3ENuQMlYlNBlXfr7dcI5ngS");
		driver.findElement(By.xpath("//button[@type='button']")).click();
		driver.findElement(By.xpath("//a[@id='creditcardlang']")).click();

		driver.findElement(By.xpath("//input[@id='CardNumber']")).sendKeys("1234");
		driver.findElement(By.xpath("//input[@id='CardHolderName']")).sendKeys(" ");
		driver.findElement(By.xpath("//input[@id='validthru']")).sendKeys(" ");
		driver.findElement(By.xpath("//input[@id='creditCvv']")).sendKeys(" ");

		String errorText = driver.findElement(By.xpath("//span[@id='error12']")).getText();
		Assert.assertEquals(true, errorText.contains("error")); // leaving credit card details blank gives an error
																// message

	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}
}
