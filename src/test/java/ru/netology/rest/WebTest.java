package ru.netology.rest;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static com.codeborne.selenide.Selenide.open;

public class WebTest {

    @BeforeEach
    void openPage() {
        open("http://localhost:9999");
    }

    private void fillAndSubmit(String name, String phone, boolean agree) {
        $("[data-test-id=name] input").setValue(name);
        $("[data-test-id=phone] input").setValue(phone);
        if (agree) {
            $("[data-test-id=agreement]").click();
        }
        $$("button").findBy(exactText("Продолжить")).click();
    }

    private SelenideElement error(String field) {
        return $("[data-test-id=" + field + "].input_invalid .input__sub");
    }

    @Test
    void shouldSubmitValidForm() {
        fillAndSubmit("Русское Имя", "+79012345678", true);
        $("[data-test-id=order-success]")
                .shouldBe(visible)
                .shouldHave(text("Ваша заявка успешно отправлена!"));
    }

    @Test
    void shouldRejectInvalidName() {
        fillAndSubmit("Ivan Petrov", "+79012345678", true);
        error("name").shouldHave(text("Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    void shouldRejectEmptyName() {
        fillAndSubmit("", "+79012345678", true);
        error("name").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldRejectInvalidPhone() {
        fillAndSubmit("Иван Петров", "+7901234567", true);
        error("phone").shouldHave(text("Должно быть 11 цифр"));
    }

    @Test
    void shouldRejectEmptyPhone() {
        fillAndSubmit("Иван Петров", "", true);
        error("phone").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldRejectUncheckedAgreement() {
        fillAndSubmit("Иван Петров", "+79012345678", false);
        $("[data-test-id=agreement].input_invalid").shouldBe(visible);
        $("[data-test-id=order-success]").shouldNotBe(visible);
    }
}
