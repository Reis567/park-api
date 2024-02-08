package com.reis.demo.park.api;



import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reis.demo.park.api.entity.Vaga;
import com.reis.demo.park.api.web.dto.VagaCreateDTO;
import com.reis.demo.park.api.web.dto.VagaResponseDTO;
import com.reis.demo.park.api.web.exception.ErrorMessage;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/vagas/vagas-insert.sql" ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/vagas/vagas-delete.sql" ,executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class VagaIT {
        
    @Autowired
    WebTestClient testClient;

    @Test
    public void createVaga_ComDadosValidos_RetornaVagaCriadaComStatus201() {

        VagaResponseDTO responseBody = testClient
            .post()
            .uri("/api/v1/vagas")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new VagaCreateDTO("A-06",Vaga.StatusVaga.LIVRE))
            .exchange()
            .expectStatus().isCreated()
            .expectBody(VagaResponseDTO.class)
            .returnResult().getResponseBody();

            org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
            org.assertj.core.api.Assertions.assertThat(responseBody.getCodigo()).isEqualTo("A-06");
            org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo("LIVRE");


    }
    @Test
    public void createVaga_ComCodigoExistente_RetornaErro409() {
        ErrorMessage errorMessage = testClient
            .post()
            .uri("/api/v1/vagas")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new VagaCreateDTO("A-01", Vaga.StatusVaga.LIVRE))
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.CONFLICT)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
        org.assertj.core.api.Assertions.assertThat(errorMessage.getStatus()).isEqualTo(409);
    }
}