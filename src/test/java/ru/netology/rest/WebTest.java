package ru.netology.rest;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class WebTest {
    @Test
    void rightInput() {
        open("http://0.0.0.0:9999");

    }
}
