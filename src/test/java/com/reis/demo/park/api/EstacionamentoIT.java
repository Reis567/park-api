package com.reis.demo.park.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reis.demo.park.api.web.dto.EstacionamentoCreateDTO;
import com.reis.demo.park.api.web.dto.EstacionamentoResponseDTO;
import com.reis.demo.park.api.web.exception.ErrorMessage;

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

    testClient
        .post()
        .uri("/api/v1/estacionamentos/check-in")
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new EstacionamentoCreateDTO("ABC1234", "MarcaCarro", "ModeloCarro", "CorCarro", "17526942360"))
        .exchange()
        .expectHeader().exists("Location");
}


@Test
public void checkin_ComPerfilCliente_RetornaStatus403() {

    ErrorMessage errorMessage = testClient.post()
    .uri("/api/v1/estacionamentos/check-in")
    .headers(JwtAuthentication.getHeaderAuthorization(testClient,"JOAO@gmail.com", "123456"))
    .contentType(MediaType.APPLICATION_JSON)
    .bodyValue(new EstacionamentoCreateDTO("ABC1234", "MarcaCarro", "ModeloCarro", "CorCarro", "17526942360"))
    .exchange()
    .expectStatus().isForbidden()
    .expectBody(ErrorMessage.class)
    .returnResult().getResponseBody();
    
    org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
    
}
@Test
public void checkin_ComCpfInvalido_RetornaStatus422() {

    ErrorMessage errorMessage = testClient
            .post()
            .uri("/api/v1/estacionamentos/check-in")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new EstacionamentoCreateDTO("ABC1234", "MarcaCarro", "ModeloCarro", "CorCarro", "12942361"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
}

@Test
public void checkin_Semcpf_RetornaStatus422() {

    ErrorMessage errorMessage = testClient
            .post()
            .uri("/api/v1/estacionamentos/check-in")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new EstacionamentoCreateDTO("ABC1234", "MarcaCarro", "ModeloCarro", "CorCarro", ""))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
}


@Test
public void checkin_Semcor_RetornaStatus422() {

    ErrorMessage errorMessage = testClient
            .post()
            .uri("/api/v1/estacionamentos/check-in")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new EstacionamentoCreateDTO("ABC1234", "MarcaCarro", "ModeloCarro", "", "17526942360"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
}


@Test
public void checkin_Semmodelo_RetornaStatus422() {

    ErrorMessage errorMessage = testClient
            .post()
            .uri("/api/v1/estacionamentos/check-in")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new EstacionamentoCreateDTO("ABC1234", "MarcaCarro", "", "corcarro", "17526942360"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
}
@Test
public void checkin_Semmarca_RetornaStatus422() {

    ErrorMessage errorMessage = testClient
            .post()
            .uri("/api/v1/estacionamentos/check-in")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new EstacionamentoCreateDTO("ABC1234", "", "modelo", "corcarro", "17526942360"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
}


@Test
public void checkin_Semplaca_RetornaStatus422() {

    ErrorMessage errorMessage = testClient
            .post()
            .uri("/api/v1/estacionamentos/check-in")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new EstacionamentoCreateDTO("", "marca", "modelo", "corcarro", "17526942360"))
            .exchange()
            .expectStatus().isEqualTo(422)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
}




@Test
@Sql(scripts = "/sql/estacionamentos/estacionamentos-ocupados-insert.sql" ,executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/estacionamentos/estacionamentos-ocupados-delete.sql" ,executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public void checkin_ComVagasOcupadas_RetornaStatus404() {

    ErrorMessage errorMessage = testClient
            .post()
            .uri("/api/v1/estacionamentos/check-in")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new EstacionamentoCreateDTO("LQL2268", "Peugeot", "306", "preto", "17526942360"))
            .exchange()
            .expectStatus().isEqualTo(404)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
}



}
