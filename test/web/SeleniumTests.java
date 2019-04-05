/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web;

import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 *
 * @author Melnikov
 */
public class SeleniumTests {
     private static WebDriver driver;
    public SeleniumTests() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        System.setProperty("webdriver.chrome.driver", "lib/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost:8080/Ptvr18WebLibraryTest/");
    }
    
    @AfterClass
    public static void tearDownClass() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void seleniumTest() {
        enterAdminTest();
        logoutTest();
        //registrationTest();
     }
     public void enterAdminTest(){
        System.out.println("Вход");
        WebElement el = driver.findElement(By.id("showLogin"));
        el.click();
        el = driver.findElement(By.name("login"));
        el.sendKeys("admin");
        el = driver.findElement(By.name("password"));
        el.sendKeys("admin");
        el = driver.findElement(By.id("enter"));
        el.click();
        el = driver.findElement(By.id("info"));
        System.out.println("Ожидается: Вы вошли как admin");
        System.out.println("Выводится: "+el.getText());
        assertEquals("Вы вошли как admin", el.getText());
     }
     public void registrationTest(){
        System.out.println("Регистрация");
        WebElement el = driver.findElement(By.id("showRegistration"));
        el.click(); 
        el = driver.findElement(By.name("name"));
        el.sendKeys("test");
     }

    private void logoutTest() {
        System.out.println("Выход");
        WebElement el = driver.findElement(By.id("logout"));
        el.click();
        el = driver.findElement(By.id("info"));
        System.out.println("Ожидается: Вы вышли!");
        System.out.println("Выводится: "+el.getText());
        assertEquals("Вы вышли!", el.getText());
    }
}
