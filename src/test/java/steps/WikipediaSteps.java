package steps;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import io.github.bonigarcia.wdm.WebDriverManager;
import static org.junit.Assert.*;
import java.time.Duration;
import java.io.File;

public class WikipediaSteps {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--remote-debugging-port=9222");
        
        // Create a unique user data directory
        String userDataDir = System.getProperty("java.io.tmpdir") + "/chrome-user-data-" + System.currentTimeMillis();
        new File(userDataDir).mkdirs();
        options.addArguments("--user-data-dir=" + userDataDir);
        
        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().maximize();
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Given("I am on the Wikipedia homepage")
    public void iAmOnTheWikipediaHomepage() {
        driver.get("https://www.wikipedia.org");
    }

    @When("I search for {string}")
    public void iSearchFor(String searchTerm) {
        WebElement searchBox = driver.findElement(By.name("search"));
        searchBox.clear();
        searchBox.sendKeys(searchTerm);
    }

    @Then("I should see the search results dropdown")
    public void iShouldSeeTheSearchResultsDropdown() {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("suggestions-dropdown")));
    }

    @And("the first result should contain {string}")
    public void theFirstResultShouldContain(String expectedText) {
         WebElement firstResult = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".suggestions-dropdown .suggestion-link:first-child")
        ));
        assertTrue(firstResult.getText().toLowerCase().contains(expectedText.toLowerCase()));
    }

    @When("I click on the {string} link")
    public void iClickOnTheLink(String linkText) {
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
        
        WebElement link = findClickableElement(linkText);
        link.click();
    }

    @And("I click on the first result")
    public void iClickOnTheFirstResult() {
         WebElement firstResult = wait.until(ExpectedConditions.presenceOfElementLocated(
            By.cssSelector(".suggestions-dropdown .suggestion-link:first-child")
        ));
        firstResult.click();
    }

    private WebElement findClickableElement(String linkText) {
        // Try different strategies to find the element
        try {
            return wait.until(ExpectedConditions.elementToBeClickable(By.linkText(linkText)));
        } catch (Exception e) {
            try {
                return wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(linkText)));
            } catch (Exception e2) {
                String xpath = String.format("//a[contains(text(),'%s') or contains(@title,'%s') or contains(@aria-label,'%s')]",
                    linkText, linkText, linkText);
                return wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
            }
        }
    }

    @Then("I should be taken to the Contents page with title {string}")
    public void iShouldBeTakenToTheContentsPage(String title) {
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState === 'complete'"));
        
        boolean titleFound = false;
        
        try {
            if (driver.getTitle().contains(title)) {
                titleFound = true;
            }
        } catch (Exception e) {
            // Continue checking other titles
        }
        
        assertTrue("Expected page title not found", titleFound);
    }

    @Then("the page title should contain {string}")
    public void thePageTitleShouldContain(String expectedTitle) {
        assertTrue(driver.getTitle().contains(expectedTitle));
    }
} 
