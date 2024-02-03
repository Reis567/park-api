package com.reis.demo.park.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.reis.demo.park.api.web.dto.ClienteCreateDTO;
import com.reis.demo.park.api.web.dto.ClienteResponseDTO;
import com.reis.demo.park.api.web.exception.ErrorMessage;

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


    @Test
    public void createCliente_ComPermissaoAdmin_RetornaErro403() {

        ErrorMessage responseBody = testClient
            .post()
            .uri("/api/v1/clientes")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new ClienteCreateDTO("Serginho blaublau","54006491492"))
            .exchange()
            .expectStatus().isForbidden()
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();;
            org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void createCliente_ComCPFDuplicado_RetornaErro409() {
        ErrorMessage responseBody = testClient
            .post()
            .uri("/api/v1/clientes")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "TONY@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new ClienteCreateDTO("Fernandinho Beiramar", "59575966392"))
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.CONFLICT)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }

    @Test
    public void createCliente_ComCPFInvalidoLongo_RetornaErro422() {
        ErrorMessage responseBody = testClient
            .post()
            .uri("/api/v1/clientes")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "TONY@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new ClienteCreateDTO("Fernandinho Beiramar", "595759626392"))
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createCliente_ComCPFInvalidoCurto_RetornaErro422() {
        ErrorMessage responseBody = testClient
            .post()
            .uri("/api/v1/clientes")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "TONY@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new ClienteCreateDTO("Fernandinho Beiramar", "595756392"))
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }


}
