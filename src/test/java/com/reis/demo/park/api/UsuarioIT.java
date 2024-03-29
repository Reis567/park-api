package com.reis.demo.park.api;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reis.demo.park.api.web.dto.UsuarioCreateDTO;
import com.reis.demo.park.api.web.dto.UsuarioResponseDTO;
import com.reis.demo.park.api.web.dto.UsuarioSenhaDTO;
import com.reis.demo.park.api.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/usuarios/usuarios-insert.sql" ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/usuarios/usuarios-delete.sql" ,executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {
    
    @Autowired
    WebTestClient testClient;

    @Test
    public void createUsuario_ComUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201(){
        UsuarioResponseDTO responseBody = testClient
            .post()
            .uri("/api/v1/usuarios/registro")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioCreateDTO("toby@email.com","123456"))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(UsuarioResponseDTO.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("toby@email.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
    }
    
    @Test
    public void createUsuario_ComUsernameInvalido_RetornarErrorMessageComStatus422(){
        ErrorMessage responseBody = testClient
            .post()
            .uri("/api/v1/usuarios/registro")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioCreateDTO("aaamail.com","123456"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
            .post()
            .uri("/api/v1/usuarios/registro")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioCreateDTO("aaa@mailcom","123456"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
            .post()
            .uri("/api/v1/usuarios/registro")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioCreateDTO("aa@.com","123456"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void createUsuario_ComPasswordInvalido_RetornarErrorMessageComStatus422(){
        ErrorMessage responseBody = testClient
            .post()
            .uri("/api/v1/usuarios/registro")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioCreateDTO("toby@email.com",""))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
            .post()
            .uri("/api/v1/usuarios/registro")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioCreateDTO("toby@email.com","12"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

    }

    
    @Test
    public void createUsuario_ComUsernameRepetido_RetornarErrorMessageComStatus409(){
        ErrorMessage responseBody = testClient
            .post()
            .uri("/api/v1/usuarios/registro")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioCreateDTO("ana@gmail.com","123456"))
            .exchange()
            .expectStatus().isEqualTo(409)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
    }
    @Test
    public void BuscarUsuario_ComIdExistente_RetornarUsuarioComStatus200(){
        UsuarioResponseDTO responseBody = testClient
            .get()
            .uri("/api/v1/usuarios/100")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
            .exchange()
            .expectStatus().isOk()
            .expectBody(UsuarioResponseDTO.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("ana@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");

        responseBody = testClient
            .get()
            .uri("/api/v1/usuarios/101")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
            .exchange()
            .expectStatus().isOk()
            .expectBody(UsuarioResponseDTO.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(101);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("JOAO@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");

        responseBody = testClient
            .get()
            .uri("/api/v1/usuarios/101")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "JOAO@gmail.com", "123456"))
            .exchange()
            .expectStatus().isOk()
            .expectBody(UsuarioResponseDTO.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(101);
        org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("JOAO@gmail.com");
        org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
    }

    @Test
    public void BuscarUsuario_SemAutorizacao_RetornarErroMessageComStatus401(){
        ErrorMessage responseBody = testClient
            .get()
            .uri("/api/v1/usuarios/100")
            .exchange()
            .expectStatus().isUnauthorized()
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    }

    @Test
    public void BuscarUsuario_ComIdInexistente_RetornarErroMessageComStatus404(){
        ErrorMessage responseBody = testClient
            .get()
            .uri("/api/v1/usuarios/00")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
            .exchange()
            .expectStatus().isNotFound()
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
    }
    
    @Test
    public void BuscarUsuario_ComUsuarioClienteBuscandoOutroCliente_RetornarErroMessageComStatus403(){
        ErrorMessage responseBody = testClient
            .get()
            .uri("/api/v1/usuarios/102")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "JOAO@gmail.com", "123456"))
            .exchange()
            .expectStatus().isForbidden()
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }
    @Test
    public void updatePassword_ComDadosValidos_RetornaRStatus200(){
        testClient
            .patch()
            .uri("/api/v1/usuarios/100")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioSenhaDTO("123456","1234567","1234567"))
            .exchange()
            .expectStatus().isOk();

            testClient
            .patch()
            .uri("/api/v1/usuarios/101")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "JOAO@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioSenhaDTO("123456","1234567","1234567"))
            .exchange()
            .expectStatus().isOk();
    }

    @Test
    public void updatePassword_SemAutorizacao_RetornarErroMessageComStatus401(){
        testClient
            .patch()
            .uri("/api/v1/usuarios/100")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioSenhaDTO("123456","1234567","1234567"))
            .exchange()
            .expectStatus().isUnauthorized()
            .expectBody(ErrorMessage.class);
    }
    @Test
    public void updatePassword_ComIdDiferente_RetornarErroMessageComStatus403(){
        ErrorMessage responseBody = testClient
            .patch()
            .uri("/api/v1/usuarios/100")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "JOAO@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioSenhaDTO("123456","1234567","1234567"))
            .exchange()
            .expectStatus().isForbidden()
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
    }

    @Test
    public void updatePassword_ComCamposInvalidos_RetornarErroMessageComStatus422(){
        ErrorMessage responseBody = testClient
            .patch()
            .uri("/api/v1/usuarios/100")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioSenhaDTO("","",""))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);

        responseBody = testClient
            .patch()
            .uri("/api/v1/usuarios/100")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioSenhaDTO("1","1","1"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
    }

    @Test
    public void updatePassword_ComSenhasNaoConf_RetornarErroMessageComStatus400(){
        ErrorMessage responseBody = testClient
            .patch()
            .uri("/api/v1/usuarios/100")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new UsuarioSenhaDTO("123456","1234567","12345678"))
            .exchange()
            .expectStatus().isEqualTo(400)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
    }

    @Test
    public void getAllUsers_ReturnsListOfUsersWith200Status() {
        List<UsuarioResponseDTO> responseBody = testClient
            .get()
            .uri("/api/v1/usuarios")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@gmail.com", "123456"))
            .exchange()
            .expectStatus().isOk()
            .expectBodyList(UsuarioResponseDTO.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody).hasSizeGreaterThan(0);

    }
    

}
