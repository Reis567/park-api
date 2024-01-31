package com.reis.demo.park.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reis.demo.park.api.config.jwt.JwtToken;
import com.reis.demo.park.api.web.dto.UsuarioLoginDTO;
import com.reis.demo.park.api.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql" ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql" ,executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AutenticacaoIT {
    @Autowired
    WebTestClient testClient;

    @Test
    public void autenticar_ComCredenciaisValid_RetornarTokenComStatus200(){
        JwtToken responseBody = testClient
        .post()
        .uri("api/v1/auth")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioLoginDTO("ana@gmail.com","123456"))
        .exchange()
        .expectStatus().isOk()
        .expectBody(JwtToken.class)
        .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void autenticar_ComCredenciaisInvalid_RetornarErrorMessageComStatus400(){
        ErrorMessage responseBody = testClient
        .post()
        .uri("api/v1/auth")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioLoginDTO("invalido@gmail.com","123456"))
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);

        responseBody = testClient
        .post()
        .uri("api/v1/auth")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioLoginDTO("ana@gmail.com","1234567"))
        .exchange()
        .expectStatus().isBadRequest()
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }

    @Test
    public void autenticar_ComDadosEmFormatoInvalid_RetornarErrorMessageComStatus422(){
        ErrorMessage responseBody = testClient
        .post()
        .uri("api/v1/auth")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioLoginDTO("","123456"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }
}
