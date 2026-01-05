package com.store.msshoppingcart.category;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "src/test/resources/features",
    glue = {
        "com.store.msshoppingcart.category.steps",
        "com.store.msshoppingcart.category.steps.config"
    },
    plugin = {
        "pretty",
        "html:target/cucumber-reports/categoria-criacao.html"
    }
)
public class CucumberCategoriaIT {
}
