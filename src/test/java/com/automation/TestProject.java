package com.automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class TestProject {

    private  WebDriver driver;
    private WebDriverWait wait;

    @BeforeTest
    public void initialize(){
        System.setProperty("webdriver.chrome.driver","C:/Users/Public/Assignment-4 Selenium WebDriver/drivers/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 30);
    }
    @Test
    public void testAmazonProducts() throws InterruptedException {
        driver.get("https://www.amazon.in/");

        //code for script

        // click on hamburger menu
        WebElement clickHamburger = driver.findElement(By.id("nav-hamburger-menu")); //by id
        clickHamburger.click();

        // scroll and click on the TV, Appliances and Electronics section
        WebElement scrollAndClick = driver.findElement(By.xpath("//a[@data-ref-tag='nav_em_1_1_1_14']")); //by Xpath
        scrollAndClick.click();

        // click on Televisions
        WebElement clickTelevision = driver.findElement(By.linkText("Televisions"));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.linkText("Televisions")));
        clickTelevision.click();

        // Filter the results by brand samsung
        WebElement clickBrand = driver.findElement(By.linkText("Samsung"));
        clickBrand.click();

        // Sort the data from high to low
        Select select = new Select(driver.findElement(By.name("s")));
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.name("s"))));
        select.selectByVisibleText("Price: High to Low");

        // click on the second highest item
        WebElement clickSecondHighest = driver.findElement(By.xpath("//img[@alt='Samsung 189 cm (75 inches) 4K Ultra HD Smart Neo QLED TV QA75QN90BAKXXL (Titan Black)']"));
        String mainWindow = driver.getWindowHandle();
        clickSecondHighest.click();

        // switch window
        Set<String> windows = driver.getWindowHandles();
        Iterator<String> itr = windows.iterator();

        while((itr.hasNext())){
            String newWindow = itr.next();
            if(!mainWindow.equalsIgnoreCase(newWindow)){
                driver.switchTo().window(newWindow);
                wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//h1[@class='a-size-base-plus a-text-bold']")));
                WebElement aboutThis = driver.findElement(By.xpath("//h1[@class='a-size-base-plus a-text-bold']"));
                String actualContext = aboutThis.getText();

                //assertion
                Assert.assertEquals(actualContext, "About this item", "Incorrect details");
            }
        }

        //Iterate over all items of About this
        List<WebElement> productDescription = driver.findElements(By.xpath("//ul[@class='a-unordered-list a-vertical a-spacing-mini']"));
        for(WebElement product: productDescription){
            System.out.println(product.getText());
        }
        driver.quit();
    }
}