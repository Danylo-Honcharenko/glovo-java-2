package org.coursesjava.glovojava;

import org.coursesjava.glovojava.model.OrderEntity;
import org.coursesjava.glovojava.model.ProductEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class OrderJavaApplicationTests {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void contextLoads() {
        assertThat(webTestClient).isNotNull();
    }

    @Test
    public void create() {
        OrderEntity expected = new OrderEntity();
        expected.setId(5);
        expected.setDate(LocalDate.now());
        expected.setCost(0);

        OrderEntity actual = new OrderEntity();
        actual.setDate(LocalDate.now());
        actual.setCost(0);

        webTestClient.post()
                .uri("/api/orders/create")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(actual))
                .exchange()
                .expectStatus()
                .isCreated()
                .expectBody()
                .jsonPath("$.id").isEqualTo(5)
                .jsonPath("$.date").isEqualTo(LocalDate.now().toString())
                .jsonPath("$.cost").isEqualTo(0)
                .jsonPath("$.products").isEmpty();
    }

    @Test
    public void getById() {
        webTestClient.get()
                .uri("/api/orders/{id}", 3)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isFound()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(3)
                .jsonPath("$.data.date").isEqualTo("2021-10-10")
                .jsonPath("$.data.cost").isEqualTo(200);
    }

    @Test
    public void getByIdNotFound() {
        webTestClient.get()
                .uri("/api/orders/{id}", 100)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isNotFound()
                .expectBody()
                .jsonPath("$.message").isEqualTo("Order not found!")
                .jsonPath("$.status").isEqualTo("NOT_FOUND");
    }

    @Test
    public void updateOrder() {
        OrderEntity order = new OrderEntity();
        order.setDate(LocalDate.now());
        order.setCost(150);

        webTestClient.put()
                .uri("/api/orders/{id}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(order))
                .exchange()
                .expectStatus()
                .isFound()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(1)
                .jsonPath("$.data.date").isEqualTo(LocalDate.now().toString())
                .jsonPath("$.data.cost").isEqualTo(150)
                .jsonPath("$.data.products").isNotEmpty();
    }

    @Test
    public void addProductToOrder() {
        OrderEntity order = new OrderEntity();
        order.setId(3);
        order.setDate(LocalDate.of(2021, 10, 10));
        order.setCost(200);

        ProductEntity product = new ProductEntity();
        product.setName("Jem");
        product.setCost(14);
        product.setOrder(order);

        webTestClient.patch()
                .uri("/api/orders/{id}", 3)
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(product))
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.data.id").isEqualTo(3)
                .jsonPath("$.data.date").isEqualTo("2021-10-10")
                .jsonPath("$.data.cost").isEqualTo(200)
                .jsonPath("$.data.products[0].id").isEqualTo(4)
                .jsonPath("$.data.products[0].name").isEqualTo("Jem")
                .jsonPath("$.data.products[0].cost").isEqualTo(14);
    }

    @Test
    public void deleteProduct() {
        webTestClient.delete()
                .uri("/api/orders/{id}/product/{name}", 1, "Apple")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void delete() {
        webTestClient.delete()
                .uri("/api/orders/{id}", 2)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
