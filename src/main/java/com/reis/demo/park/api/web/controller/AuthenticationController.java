package com.reis.demo.park.api.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reis.demo.park.api.config.jwt.JwtToken;
import com.reis.demo.park.api.config.jwt.JwtUserDetailsService;
import com.reis.demo.park.api.web.dto.UsuarioLoginDTO;
import com.reis.demo.park.api.web.dto.UsuarioResponseDTO;
import com.reis.demo.park.api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Autenticação", description = "Recurso para proceder com a autenticação na API")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthenticationController {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    @Operation(summary = "Autenticar na API",
                    description = "Recurso para fazer login na api",
                    responses = {
                        @ApiResponse(responseCode = "200",description = "Autenticação realizada com sucesso e retorno do Bearer token"
                        , content = @Content(mediaType = "application/json",schema = @Schema(implementation = UsuarioResponseDTO.class))),

                        @ApiResponse(responseCode = "400", description = "Credenciais inválidas",
                        content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),

                        @ApiResponse(responseCode = "422", description = "Campos inválidos",
                        content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                        
                    })
    @PostMapping("/auth")
    public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDTO usuarioLoginDTO , HttpServletRequest request){
        log.info("Processo de autenticação com o login '{}'",usuarioLoginDTO.getUsername());
        try {

            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuarioLoginDTO.getUsername(), usuarioLoginDTO.getPassword());

            authenticationManager.authenticate(authenticationToken);
            JwtToken token = jwtUserDetailsService.getTokenAuthenticated(usuarioLoginDTO.getUsername());
            return ResponseEntity.ok().body(token);

        } catch (AuthenticationException exception) {

            log.error("Bad credentials from username'{}'", usuarioLoginDTO.getUsername());

        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
    }
}
