package ba.pehli.facebook.ui;

import static org.junit.Assert.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.core.io.Resource;

public class UiTest {
	static final String PATH = "http://localhost:8080/facebook";
	WebDriver driver;
	
	
	
	@Before
	public void setup(){
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10,  TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	public boolean registerUser(){
		driver.navigate().to(PATH);
		driver.findElement(By.id("firstName")).sendKeys("almir");
		driver.findElement(By.id("lastName")).sendKeys("pehratovic");
		driver.findElement(By.id("user")).findElement(By.id("username")).sendKeys("almir.pehratovic");
		driver.findElement(By.id("user")).findElement(By.name("password2")).sendKeys("almir");
		driver.findElement(By.id("user")).findElement(By.name("password")).sendKeys("almir");
		driver.findElement(By.id("gender1")).click();
		driver.findElement(By.id("signup")).click();
		
		try {
			WebElement el = driver.findElement(By.className("alert-success"));
		} catch (NoSuchElementException e) {
			return false;
		}
		
		return true;
	}
	
	@Test
	public void setProfilePictureForNewUser(){
		boolean success = registerUser();
		assertTrue(success);
		
		driver.findElement(By.id("loginForm")).findElement(By.id("username")).sendKeys("almir.pehratovic");
		driver.findElement(By.id("loginForm")).findElement(By.name("password")).sendKeys("almir");
		driver.findElement(By.id("login")).click();
		driver.findElement(By.linkText("Edit profile")).click();
		
		driver.findElement(By.name("picture")).click();
		
	}
}
