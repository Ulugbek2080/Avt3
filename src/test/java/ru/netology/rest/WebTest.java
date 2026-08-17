package ru.netology.rest;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WebTest {
    private SelenideElement form;

    @BeforeEach
    void openPage() {
        open("http://localhost:9999");
        form = $(".form");
    }

    private void fillAndSubmit(String name, String phone, boolean agree) {
        form.$("[data-test-id=name] input").setValue(name);
        form.$("[data-test-id=phone] input").setValue(phone);
        if (agree) {
            form.$("[data-test-id=agreement]").click();
        }
        form.$(".button").click();
    }

    private SelenideElement error(String field) {
        return form.$("[data-test-id=" + field + "].input_invalid .input__sub");
    }

    @Test
    void shouldSubmitValidForm() {
        fillAndSubmit("Пётр Иванов-Сидоров", "+79012345678", true);
        $("[data-test-id=order-success]").shouldHave(exactText("  Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));
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
        form.$("[data-test-id=agreement].input_invalid").shouldBe(visible);
        $("[data-test-id=order-success]").shouldNotBe(visible);
    }
}
