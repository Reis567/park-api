package com.reis.demo.park.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reis.demo.park.api.web.dto.EstacionamentoCreateDTO;
import com.reis.demo.park.api.web.dto.EstacionamentoResponseDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-insert.sql" ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-delete.sql" ,executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class EstacionamentoIT {
    @Autowired
    WebTestClient testClient;


    @Test
public void checkin_ComDadosValidos_RetornaCheckinComStatus201ELocation() {

    EstacionamentoResponseDTO responseBody = testClient
        .post()
        .uri("/api/v1/estacionamentos/check-in")
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new EstacionamentoCreateDTO("ABC1234", "MarcaCarro", "ModeloCarro", "CorCarro", "17526942360"))
        .exchange()
        .expectStatus().isCreated()
        .expectBody(EstacionamentoResponseDTO.class)
        .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(responseBody.getRecibo()).isNotNull();
    org.assertj.core.api.Assertions.assertThat(responseBody.getPlaca()).isEqualTo("ABC1234");

}
}
