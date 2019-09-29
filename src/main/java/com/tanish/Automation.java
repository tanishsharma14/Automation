package com.tanish;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class Automation {

	public static WebDriver driver;
	@BeforeTest()
	public void browser() throws Exception
	{
		FileInputStream fis=new FileInputStream(new File("C:\\Users\\shiv\\Desktop\\Java Training\\FlipkartAutomation\\Config.properties"));
		Properties props=new Properties();
		props.load(fis);
		
		System.setProperty(props.getProperty("drivername"), props.getProperty("driverpath"));
		
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		
		driver.get(props.getProperty("url"));
		driver.manage().timeouts().implicitlyWait(20,TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(20,TimeUnit.SECONDS);
	}
	
	@Test(priority=0)
	public void testTititle()
	{
		String actual=driver.getTitle();
		String expected="Online Shopping Site for Mobiles, Electronics, Furniture, Grocery, Lifestyle, Books & More. Best Offers!";
		Assert.assertEquals(actual, expected);
	}
	
	@Test(priority=1)
	public void login() throws IOException, InterruptedException
	{
		FileInputStream fis2=new FileInputStream(new File("C:\\Users\\shiv\\Desktop\\Java Training\\FlipkartAutomation\\Config.properties"));
		Properties props=new Properties();
		props.load(fis2);
		
		File file=new File(props.getProperty("sheetpath"));
		FileInputStream dataread=new FileInputStream(file);
		XSSFWorkbook workbook=new XSSFWorkbook(dataread);
		XSSFSheet sheet=workbook.getSheet("Tanish"); 
		
		DataFormatter formatter = new DataFormatter();
		
		String userid = formatter.formatCellValue(sheet.getRow(0).getCell(0));
		String pass = formatter.formatCellValue(sheet.getRow(0).getCell(1));
		
		
		driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[2]/div/form/div[1]/input")).sendKeys(userid);
		driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div/div[2]/div/form/div[2]/input")).sendKeys(pass);
		driver.findElement(By.xpath("(//button[@type='submit'])[2]")).click();
		Thread.sleep(4000);
		driver.findElement(By.xpath("/html/body/div/div/div[1]/div[1]/div[2]/div[2]/form/div/div/input")).sendKeys("sandisk pendrive");	
		
		Thread.sleep(4000);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		
		driver.findElement(By.xpath("/html/body/div/div/div[3]/div[2]/div[1]/div[2]/div[2]/div/div[1]/div/a[2]")).click();
		Thread.sleep(4000);
		ArrayList tabs=new ArrayList(driver.getWindowHandles());
		driver.switchTo().window((String) tabs.get(1));
		
		Thread.sleep(4000);
		
		driver.findElement(By.xpath("/html/body/div[1]/div/div[3]/div[2]/div[1]/div[1]/div[2]/div/ul/li[1]/button")).click();
		Thread.sleep(4000);
	}
	
	@Test(priority=2)
	public void mouseAction()
	{
		Actions act=new Actions(driver);
		By linktext=By.linkText("Contact Us");
		WebElement element=driver.findElement(linktext);
		act.moveToElement(element).build().perform();
	}
	
	@AfterMethod
	public void takePicture() throws IOException
	{
		TakesScreenshot ts=(TakesScreenshot)driver;
		File source=ts.getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(source,new File("C:\\Users\\shiv\\Desktop\\Java Training\\FlipkartAutomation\\Screenshot\\pic.png"));
		
	}
	
	@AfterTest
	public void afterTest()
	{
		driver.close();
		driver.quit();
	}
}
