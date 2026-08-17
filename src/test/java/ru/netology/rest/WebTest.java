package ru.netology.rest;
import com.codeborne.selenide.SelenideElement;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WebTest {

    @Test
    void rightInput() {
        open("http://0.0.0.0:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Владимил Невладимироваич");
        form.$("[data-test-id=phone] input").setValue("+76767676767");
        form.$("[data-test-id=agreement]").shouldBe(visible).click();
        form.$(".button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
    }
}
