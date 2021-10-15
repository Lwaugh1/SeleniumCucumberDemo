package Test;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MyStepdefs {
    WebElement lowestPrice;
    WebDriver driver;

    @Given("^I add four different products to my wish list$")
    public void iAddFourDifferentProductsToMyWishList() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\ChromeDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://testscriptdemo.com");

        //This is needed to make code work as its BROKEN!!!
        driver.manage().window().maximize();
        Thread.sleep(300);
        driver.findElement(By.xpath("//a[@href='?add_to_wishlist=17']")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//a[@href='?add_to_wishlist=14']")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//a[@href='?add_to_wishlist=20']")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//a[@href='?add_to_wishlist=23']")).click();
        Thread.sleep(300);
    }

    @When("^I view my wishlist table$")
    public void iViewMyWishlistTable() throws InterruptedException {
        driver.findElement(By.xpath("//a[@href='https://testscriptdemo.com/?page_id=233&wishlist-action']")).click();
        Thread.sleep(300);
    }

    @Then("^I find total four selected items in my Wishlist$")
    public void iFindTotalFourSelectedItemsInMyWishlist() throws InterruptedException {
        Thread.sleep(300);
        List<WebElement> rows = driver.findElements(By.xpath("//table[@class='shop_table cart wishlist_table wishlist_view traditional responsive   ']/tbody/tr"));
        assertEquals(4, rows.size(), "The numbers in wishlist were not 4");
    }

    @When("^I search for lowest price product$")
    public void iSearchForLowestPriceProduct() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\ChromeDriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://testscriptdemo.com");
        driver.manage().window().maximize();
        Thread.sleep(300);
        driver.findElement(By.xpath("//a[@href='?add_to_wishlist=17']")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//a[@href='?add_to_wishlist=14']")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//a[@href='?add_to_wishlist=20']")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//a[@href='?add_to_wishlist=23']")).click();
        Thread.sleep(300);
        driver.findElement(By.xpath("//a[@href='https://testscriptdemo.com/?page_id=233&wishlist-action']")).click();
        Thread.sleep(300);
        String product_price;
        WebElement product_cart;
        List<WebElement> price = driver.findElements(By.xpath("//td[@class='product-price']"));
        List<WebElement> product = driver.findElements(By.xpath("//td[@class='product-add-to-cart']/a"));
        Map<WebElement, Double> WebsitePrice = new HashMap<WebElement, Double>();
        //System.out.println(product.get(0).getAttribute("data-product_id"));
        //System.out.println(price.get(0).getText());
        for (int i = 0; i < product.size(); i++) {
            product_cart = product.get(i);
            product_price = price.get(i).getText();
            if (product_price.contains(" – ")) {
                product_price = product_price.substring(0, 5);
            } else {
                product_price = product_price.substring(8);
            }
           //int int_product_cart = Integer.parseInt(product_cart);
            product_price = product_price.replaceAll("£", "");
            double double_product_price = Double.parseDouble(product_price);
            WebsitePrice.put(product_cart, double_product_price);
        }
        Map.Entry<WebElement, Double> minEntry = null;
        for (Map.Entry<WebElement, Double> entry : WebsitePrice.entrySet()) {
            if (minEntry == null
                    || entry.getValue().compareTo(minEntry.getValue()) < 0) {
                minEntry = entry;
            }
        }
        //System.out.println(minEntry.getKey());
        lowestPrice = minEntry.getKey();
    }

    @And("^I am able to add the lowest price item to my cart$")
    public void iAmAbleToAddTheLowestPriceItemToMyCart() throws InterruptedException {
        Thread.sleep(300);
        driver.findElement(By.xpath("//a[@href='"+lowestPrice.getAttribute("href").substring(27)+"']")).click();
        Thread.sleep(300);
    }

    @Then("^I am able to verify the item in my cart$")
    public void iAmAbleToVerifyTheItemInMyCart() throws InterruptedException {
        Thread.sleep(300);
        driver.findElement(By.xpath("//a[@href='https://testscriptdemo.com/?page_id=299']")).click();
        Thread.sleep(300);
        List<WebElement> rows = driver.findElements(By.xpath("//table[@class='shop_table shop_table_responsive cart woocommerce-cart-form__contents']"));
        assertEquals(1, rows.size(), "Items were not what was expected");

    }
}
