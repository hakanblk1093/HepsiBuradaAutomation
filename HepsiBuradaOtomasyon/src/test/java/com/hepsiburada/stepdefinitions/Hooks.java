package com.hepsiburada.stepdefinitions;

import com.hepsiburada.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Hooks {

    @Before
    public void setUp() {
        DriverManager.getDriver();
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            TakesScreenshot ts = (TakesScreenshot) DriverManager.getDriver();

            // Raporа göm
            byte[] screenshot = ts.getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", scenario.getName());

            // Dosyaya kaydet
            try {
                File source = ts.getScreenshotAs(OutputType.FILE);
                String timestamp = LocalDateTime.now()
                        .format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                String fileName = scenario.getName().replaceAll("[^a-zA-Z0-9]", "_")
                        + "_" + timestamp + ".png";
                Path target = Path.of("target", "screenshots", fileName);
                Files.createDirectories(target.getParent());
                Files.copy(source.toPath(), target);
            } catch (IOException e) {
                System.out.println("Ekran görüntüsü kaydedilemedi: " + e.getMessage());
            }
        }
        DriverManager.quitDriver();
    }
}