package com.reis.demo.park.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.reis.demo.park.api.web.dto.EstacionamentoCreateDTO;
import com.reis.demo.park.api.web.dto.EstacionamentoResponseDTO;
import com.reis.demo.park.api.web.dto.PageableDTO;
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


@Test
public void checkin_ComCpfDesconhecido_RetornaStatus404() {

    ErrorMessage errorMessage = testClient
            .post()
            .uri("/api/v1/estacionamentos/check-in")
            .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(new EstacionamentoCreateDTO("LQL2268", "Peugeot", "306", "preto", "06127587530"))
            .exchange()
            .expectStatus().isEqualTo(404)
            .expectBody(ErrorMessage.class)
            .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(errorMessage).isNotNull();
}

@Test
public void getByRecibo_ComReciboExistente_RetornaCheckinComStatus200() {

    String reciboExistente = "20230313-101301";

    EstacionamentoResponseDTO responseBody = testClient
        .get()
        .uri("/api/v1/estacionamentos/check-in/{recibo}", reciboExistente)
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
        .exchange()
        .expectStatus().isOk()
        .expectBody(EstacionamentoResponseDTO.class)
        .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getRecibo()).isEqualTo("20230313-101301");
        org.assertj.core.api.Assertions.assertThat(responseBody.getPlaca()).isEqualTo("GOL1234");
        org.assertj.core.api.Assertions.assertThat(responseBody.getMarca()).isEqualTo("VW");
        org.assertj.core.api.Assertions.assertThat(responseBody.getModelo()).isEqualTo("Gol");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCor()).isEqualTo("BRANCO");

}


@Test
public void getByRecibo_ComReciboExistenteEPerfilDeCliente_RetornaCheckinComStatus200() {

    String reciboExistente = "20230313-101301";

    EstacionamentoResponseDTO responseBody = testClient
        .get()
        .uri("/api/v1/estacionamentos/check-in/{recibo}", reciboExistente)
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "JOAO@gmail.com", "123456"))
        .exchange()
        .expectStatus().isOk()
        .expectBody(EstacionamentoResponseDTO.class)
        .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getRecibo()).isEqualTo("20230313-101301");
        org.assertj.core.api.Assertions.assertThat(responseBody.getPlaca()).isEqualTo("GOL1234");
        org.assertj.core.api.Assertions.assertThat(responseBody.getMarca()).isEqualTo("VW");
        org.assertj.core.api.Assertions.assertThat(responseBody.getModelo()).isEqualTo("Gol");
        org.assertj.core.api.Assertions.assertThat(responseBody.getCor()).isEqualTo("BRANCO");

}

@Test
public void getByRecibo_ComReciboInexistente_RetornaErrorComStatus404() {

    String recibo = "20210313-101301";

    ErrorMessage responseBody = testClient
        .get()
        .uri("/api/v1/estacionamentos/check-in/{recibo}", recibo)
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
        .exchange()
        .expectStatus().isNotFound()
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();

}

@Test
public void checkout_ComReciboExistente_RetornaCheckoutComStatus200() {

    EstacionamentoResponseDTO checkoutResponse = testClient
        .put()
        .uri("/api/v1/estacionamentos/check-out/20230313-101301")
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
        .exchange()
        .expectStatus().isOk()
        .expectBody(EstacionamentoResponseDTO.class)
        .returnResult().getResponseBody();

    // Verifique se a resposta do check-out está correta
    org.assertj.core.api.Assertions.assertThat(checkoutResponse).isNotNull();
    org.assertj.core.api.Assertions.assertThat(checkoutResponse.getRecibo()).isEqualTo("20230313-101301");
    org.assertj.core.api.Assertions.assertThat(checkoutResponse.getValor()).isNotNull();
}

@Test
public void checkout_ComReciboInexistente_RetornaErrorComStatus404() {
    ErrorMessage responseBody = testClient
        .put()
        .uri("/api/v1/estacionamentos/check-out/ReciboInexistente")
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
        .exchange()
        .expectStatus().isNotFound()
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();


    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
}




@Test
public void checkoutPorCliente_ComReciboExistente_RetornaForbidden() {
    ErrorMessage responseBody = testClient
        .put()
        .uri("/api/v1/estacionamentos/check-out/20230313-101301")
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "JOAO@gmail.com", "123456"))
        .exchange()
        .expectStatus().isForbidden()
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();

}



@Test
public void getEstacionamentosByCPF_ComCPFExistente_RetornaUsosDeEstacionamentoComStatus200() {

    String clienteCPF = "17526942360";

    PageableDTO responseBody = testClient
        .get()
        .uri("/api/v1/estacionamentos/cpf/{clienteCPF}", clienteCPF)
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
        .exchange()
        .expectStatus().isOk()
        .expectBody(PageableDTO.class)
        .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    org.assertj.core.api.Assertions.assertThat(responseBody.getContent()).isNotEmpty();

}

@Test
public void getEstacionamentosByCPF_ComCPFInexistente_RetornaNoContent() {

    String clienteCPF = "23794879520";

    testClient
        .get()
        .uri("/api/v1/estacionamentos/cpf/{clienteCPF}", clienteCPF)
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "reis@gmail.com", "123456"))
        .exchange()
        .expectStatus().isNoContent();
}


@Test
public void getEstacionamentosByCPF_ComPerfilClienteECPFvazio_RetornaForbidden() {

    String clienteCPF = "17526942360";

    testClient
        .get()
        .uri("/api/v1/estacionamentos/cpf/{clienteCPF}", clienteCPF)
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "JOAO@gmail.com", "123456"))
        .exchange()
        .expectStatus().isForbidden();
}


@Test
public void getEstacionamentosByCPF_ComCPFCliente_RetornaUsosDeEstacionamentoComStatus200() {

    PageableDTO responseBody = testClient
        .get()
        .uri("/api/v1/estacionamentos")
        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "JOAO@gmail.com", "123456"))
        .exchange()
        .expectStatus().isOk()
        .expectBody(PageableDTO.class)
        .returnResult().getResponseBody();

    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    org.assertj.core.api.Assertions.assertThat(responseBody.getContent()).isNotEmpty();
}

}
