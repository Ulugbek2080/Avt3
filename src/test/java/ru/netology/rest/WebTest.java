package ru.netology.rest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTest {

    private static WebDriver driver;

    @BeforeAll
    static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        WebDriverManager.chromedriver().setup();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @BeforeEach
    void openPage() {
        driver.get("http://localhost:9999");
    }

    private void submit(String name, String phone, boolean agree) {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys(name);
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys(phone);
        if (agree) driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.xpath("//button[normalize-space()='Продолжить']")).click();
    }

    private String error(String field) {
        return driver.findElement(
                By.cssSelector("[data-test-id=" + field + "].input_invalid .input__sub")).getText().trim();
    }

    @Test
    void shouldSubmitValidForm() {
        submit("Русское Имя", "+79012345678", true);
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.",
                driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim());
    }

    @Test
    void shouldRejectInvalidName() {
        submit("Ivan Petrov", "+79012345678", true);
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.",
                error("name"));
    }

    @Test
    void shouldRejectEmptyName() {
        submit("", "+79012345678", true);
        assertEquals("Поле обязательно для заполнения", error("name"));
    }

    @Test
    void shouldRejectInvalidPhone() {
        submit("Иван Петров", "+7901234567", true);
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", error("phone"));
    }

    @Test
    void shouldRejectEmptyPhone() {
        submit("Иван Петров", "", true);
        assertEquals("Поле обязательно для заполнения", error("phone"));
    }

    @Test
    void shouldRejectUncheckedAgreement() {
        submit("Иван Петров", "+79012345678", false);
        driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid"));
    }
}
