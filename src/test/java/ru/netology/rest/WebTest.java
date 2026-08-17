package ru.netology.rest;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WebTest {
    @Test
    void rightInput() {
        open("http://localhost:9999");
        SelenideElement form = $(".form");
        form.$("[data-test-id=name] input").setValue("Владимил Невладимироваич");
        form.$("[data-test-id=phone] input").setValue("+76767676767");
        form.$("[data-test-id=agreement]").shouldBe(visible).click();
        form.$(".button").click();
        $("[data-test-id=order-success]")
                .shouldBe(visible)
                .shouldHave(text("Ваша заявка успешно отправлена!"));    }
}
