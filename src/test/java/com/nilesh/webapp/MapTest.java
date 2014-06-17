package com.nilesh.webapp;

import static org.junit.Assert.assertEquals;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class MapTest {

	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		driver.get("http://54.244.227.178:8080/map1234/register.jsp");
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testSuccessfulRegistration() {
		// Find the text input element by its name
		driver.findElement(By.name("fname")).sendKeys("Neo");
		driver.findElement(By.name("lname")).sendKeys("Singh");
		driver.findElement(By.name("email")).sendKeys("example@exp.com");
		driver.findElement(By.name("password")).sendKeys("test1234");
		driver.findElement(By.name("repassword")).sendKeys("test1234");
		driver.findElement(By.name("submit")).click();
		assertEquals("Registration Form", driver.getTitle());
	}

	@Test
	public void testInvalidFirstName() {
		driver.findElement(By.name("fname")).sendKeys("Po");
		driver.findElement(By.name("lname")).sendKeys("Singh");
		driver.findElement(By.name("email")).sendKeys("example@exp.com");
		driver.findElement(By.name("password")).sendKeys("test1234");
		driver.findElement(By.name("repassword")).sendKeys("test1234");
		driver.findElement(By.name("submit")).click();
		assertEquals("Please enter your name to continue",
				driver.findElement(By.name("fname"))
						.getAttribute("placeholder"));
	}

	@Test
	public void testClearButton(){
		driver.findElement(By.name("fname")).sendKeys("Po");
		driver.findElement(By.name("lname")).sendKeys("Singh");
		driver.findElement(By.name("email")).sendKeys("example@exp.com");
		driver.findElement(By.name("password")).sendKeys("test1234");
		driver.findElement(By.name("repassword")).sendKeys("test1234");
		driver.findElement(By.name("clear")).click();
		assertEquals(driver.findElement(By.name("fname"))
						.getAttribute("value"), "");
	}
	
	@Test
	public void testDefaultSubmitButton() {
		assertEquals(
				driver.findElement(By.name("submit")).getAttribute("disabled"),
				"true");
	}

	@Test
	public void testDefaultPasswordConfirmation() {
		assertEquals(
				driver.findElement(By.name("repassword")).getAttribute(
						"disabled"), "true");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
	}

}
