package com.reis.demo.park.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.http.MediaType;

import com.reis.demo.park.api.web.dto.ClienteCreateDTO;
import com.reis.demo.park.api.web.dto.ClienteResponseDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/clientes/clientes-insert.sql" ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/clientes/clientes-delete.sql" ,executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ClientesIT {
    
    @Autowired
    WebTestClient testClient;

    @Test
    public void createCliente_ComDadosValidos_RetornaClienteCriadoComStatus201() {

        ClienteResponseDTO responseBody = testClient
            .post()
            .uri("/api/v1/clientes")
            .contentType(MediaType.APPLICATION_JSON)
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "TONY@gmail.com", "123456"))
            .bodyValue(new ClienteCreateDTO("Serginho blaublau","54006491492"))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(ClienteResponseDTO.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Serginho blaublau");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("54006491492");
    }
}
