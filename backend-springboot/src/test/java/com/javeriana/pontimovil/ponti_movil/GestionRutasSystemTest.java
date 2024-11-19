package com.javeriana.pontimovil.ponti_movil;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

@ActiveProfiles("integration-testing")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GestionRutasSystemTest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext browserContext;
    private Page page;

    private String baseUrl = "http://localhost:4200";

    @BeforeEach
    void init() {


        this.playwright = Playwright.create();
        this.browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        this.browserContext = browser.newContext();
        this.page = browserContext.newPage();
    }

    @AfterEach
    void end() {
        browser.close();
        playwright.close();
    }

    @Test
    void flujoCompletoGestionRuta() throws InterruptedException {
        // Navegar a la página de login
        page.navigate(baseUrl + "/h-login");
        //Thread.sleep(2000);

        // Realizar login
        page.waitForSelector("input#email");
        page.locator("#email").fill("admin1@admin.com");
        //Thread.sleep(2000);
        page.waitForSelector("#password input");
        page.locator("#password input").fill("123456");
        //Thread.sleep(2000);
        page.waitForSelector("button[type='submit']");
        page.locator("button[type='submit']").click();
        //Thread.sleep(2000);

        // Esperar a que cargue el menú de rutas
        page.waitForSelector("app-r-menu");
        page.navigate(baseUrl + "/r-menu");
        //Thread.sleep(2000);

        // Hacer clic en el módulo de agregar ruta
        page.waitForSelector("div.modulo-ruta.agregar-ruta");
        page.locator("div.modulo-ruta.agregar-ruta").click();
        //Thread.sleep(2000);

        // Llenar el formulario de agregar ruta
        page.waitForSelector("#code");
        page.locator("#code").fill("RUTA001");
        //Thread.sleep(2000);
        page.locator("#horaInicioInput").click();
        //Thread.sleep(1000);

        page.waitForSelector("div.p-hour-picker button[aria-label='Next Hour']");

        for (int i = 0; i < 4; i++) {
            page.locator("div.p-hour-picker button[aria-label='Next Hour']").click();
            //Thread.sleep(500);
        }
        page.keyboard().press("Enter");
        //Thread.sleep(2000);


        // Seleccionar días
        page.waitForSelector("p-multiSelect#dias");
        page.locator("p-multiSelect#dias").click();
        //Thread.sleep(2000);
        page.waitForSelector("li:has-text(\"Lunes\")");
        page.locator("li:has-text(\"Lunes\")").click();
        //Thread.sleep(2000);
        page.waitForSelector("li:has-text(\"Martes\")");
        page.locator("li:has-text(\"Martes\")").click();
        //Thread.sleep(2000);
        page.locator("p-multiSelect#dias").click();
        //Thread.sleep(2000);

        page.waitForSelector("li#pn_id_2_source_0");
        page.locator("li#pn_id_2_source_0").click();
        //Thread.sleep(1000);

        page.waitForSelector("button[data-pc-section='sourceMoveDownButton']");
        page.locator("button[data-pc-section='sourceMoveDownButton']").click();
        //Thread.sleep(2000);


        page.locator("p-button[label='Crear']").click();
        //Thread.sleep(2000);

        page.waitForSelector("button.p-button-danger:has-text('Yes')");
        page.locator("button.p-button-danger:has-text('Yes')").click();
        //Thread.sleep(2000);

        // Cerrar la ventana de agregar ruta
        page.locator("p-button[label='Cancelar']").click();
        //Thread.sleep(2000);

        // Recargar la página
        page.reload();
        //Thread.sleep(2000);

        // Verificar que la nueva ruta aparece en la lista
        page.waitForSelector("app-r-modulo-ruta .card h2");
        Locator rutas = page.locator("app-r-modulo-ruta .card h2");
        List<String> rutasCodigos = rutas.allTextContents();
        assertEquals(true, rutasCodigos.contains("RUTA001"));
        //Thread.sleep(2000);

        // Editar la ruta agregada
        page.waitForSelector("app-r-modulo-ruta .card:has(h2:has-text('RUTA001'))");
        page.locator("app-r-modulo-ruta .card:has(h2:has-text('RUTA001'))").click();
        //Thread.sleep(2000);
        page.waitForSelector("app-r-detalles-ruta .edit-btn p-button");
        page.locator("app-r-detalles-ruta .edit-btn p-button").click();
        //Thread.sleep(2000);

        page.waitForSelector("app-r-editar-ruta #horaFinInput");
        page.locator("#horaFinInput").click();

        page.waitForSelector("div.p-hour-picker button[aria-label='Next Hour']");
        //Thread.sleep(1000);

        for (int i = 0; i < 13; i++) {
            page.locator("div.p-hour-picker button[aria-label='Next Hour']").click();
            //Thread.sleep(500);
        }

        page.keyboard().press("Enter");

        //Thread.sleep(1000);

        page.locator("p-button[label='Guardar']").click();
        //Thread.sleep(2000);
        page.waitForSelector("button.p-button-danger:has-text('Yes')");
        page.locator("button.p-button-danger:has-text('Yes')").click();
        //Thread.sleep(2000);



        page.locator("p-button[label='Cancelar']").click();
        //Thread.sleep(2000);

        // Recargar la página
        page.reload();
        //Thread.sleep(2000);
        page.waitForSelector("app-r-modulo-ruta .card h2");
        Thread.sleep(1000);
        page.reload();
        Thread.sleep(2000);
        Locator horaActualizada = page.locator("app-r-modulo-ruta .card-content div:has-text('04:00 - 13:00')");
        if (!horaActualizada.isVisible()) {
            throw new RuntimeException("Error al verificar la edición de la hora.");
        }

        //Thread.sleep(2000);

        // Eliminar la ruta
        page.waitForSelector("app-r-modulo-ruta .card:has(h2:has-text('RUTA001'))");
        page.locator("app-r-modulo-ruta .card:has(h2:has-text('RUTA001'))").click();
        //Thread.sleep(2000);
        page.waitForSelector("app-r-detalles-ruta .edit-btn p-button");
        page.locator("app-r-detalles-ruta .edit-btn p-button").click();
        //Thread.sleep(2000);
        page.locator("app-r-editar-ruta p-button[label='Eliminar']").click();
        //Thread.sleep(2000);
        page.waitForSelector("button.p-button-danger:has-text('Yes')");
        page.locator("button.p-button-danger:has-text('Yes')").click();
        Thread.sleep(2000);

        // Recargar la página
        page.reload();
        //Thread.sleep(2000);

        // Verificar que la ruta ya no está en la lista
        Thread.sleep(2000);
        page.reload();
        page.waitForSelector("app-r-modulo-ruta .card h2");
        rutasCodigos = rutas.allTextContents();
        assertEquals(false, rutasCodigos.contains("RUTA001"));
        //Thread.sleep(2000);
    }

}