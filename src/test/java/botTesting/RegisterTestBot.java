package botTesting;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
public class RegisterTestBot {
    public static void main(String[] args) {
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");

        // Initialize the WebDriver
        WebDriver driver = new ChromeDriver();

        // Navigate to the register page
        driver.get("http://localhost:8080/Gossip-1.0-SNAPSHOT/register.jsp");

        // Find the username, email, password, and date of birth input fields
        WebElement usernameInput = driver.findElement(By.name("username"));
        WebElement emailInput = driver.findElement(By.name("email"));
        WebElement passwordInput = driver.findElement(By.name("password"));
        WebElement confirmPasswordInput = driver.findElement(By.xpath("//input[@type='password'][last()]"));
        WebElement dateOfBirthInput = driver.findElement(By.name("dateOfBirth"));

        // Enter values into the input fields
        usernameInput.sendKeys("test_user");
        emailInput.sendKeys("test@example.com");
        passwordInput.sendKeys("password123");
        confirmPasswordInput.sendKeys("password123");
        dateOfBirthInput.sendKeys("2000-01-01"); // or any desired date

        // Find and click the register button
        WebElement registerButton = driver.findElement(By.id("theButton"));
        registerButton.click();

        // Wait for the registration process to complete
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("registration-success")); // or any URL pattern indicating successful registration

        // Verify registration success by checking the current URL or other elements on the page
        if (driver.getCurrentUrl().contains("registration-success")) {
            System.out.println("Registration successful!");
        } else {
            System.out.println("Registration failed.");
        }

        // Close the browser
        driver.quit();
    }
}
