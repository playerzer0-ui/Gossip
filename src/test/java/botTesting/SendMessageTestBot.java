package botTesting;

import business.Message;
import daos.MessageDao;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SendMessageTestBot {
    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "src/test/java/chromedriver.exe");
        MessageDao messageDao = new MessageDao("gossip");

        // Initialize the WebDriver
        WebDriver driver = new ChromeDriver();

        // Navigate to the login page
        driver.get("http://localhost:8080/Gossip-1.0-SNAPSHOT/login.jsp");

        // Wait for the email input field to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // wait for 10 seconds
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        // Find the password input field
        WebElement passwordInput = driver.findElement(By.id("password"));

        // Enter the username and password
        emailInput.sendKeys("joe@gmail.com");
        passwordInput.sendKeys("rippleMMW1$");

        // Find and click the login button
        WebElement loginButton = driver.findElement(By.id("theButton"));
        loginButton.click();

        // Wait for the page to load completely (you might need to adjust the time depending on your website)
        wait.until(ExpectedConditions.urlContains("chatbox"));

        // Verify login success by checking the current URL or other elements on the page
        if (driver.getCurrentUrl().contains("chatbox")) {
            System.out.println("Login successful!");

            WebElement chatList = driver.findElement(By.className("chatlist"));
            // Find the first block element in the chatlist
            WebElement firstBlock = chatList.findElement(By.className("block"));
            //click it
            firstBlock.click();

            WebElement messageEntered = driver.findElement(By.id("messageEntered"));
            messageEntered.sendKeys("HELLO WORLD");

            WebElement chatboxInput = driver.findElement(By.className("chatbox-input"));

            // Find the last icon with the name "send" within the chatbox input container
            WebElement sendIcon = chatboxInput.findElement(By.cssSelector("ion-icon[name='send']:last-child"));
            sendIcon.click();

            System.out.println("message sent");
        } else {
            System.out.println("Login failed.");
        }

        driver.quit();
    }
}
