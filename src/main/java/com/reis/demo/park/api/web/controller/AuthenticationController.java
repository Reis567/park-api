package com.reis.demo.park.api.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reis.demo.park.api.config.jwt.JwtToken;
import com.reis.demo.park.api.config.jwt.JwtUserDetailsService;
import com.reis.demo.park.api.web.dto.UsuarioLoginDTO;
import com.reis.demo.park.api.web.exception.ErrorMessage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthenticationController {
    private final JwtUserDetailsService jwtUserDetailsService;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<JwtToken> autenticar(@RequestBody @Valid UsuarioLoginDTO usuarioLoginDTO , HttpServletRequest request){
        log.info("Processo de autenticação com o login '{}'",usuarioLoginDTO.getUsername());
        try {
            
        } catch (AuthenticationException exception) {
            log.error("Bad credentials from username'{}'", usuarioLoginDTO.getUsername());
        }
        return ResponseEntity.badRequest().body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credenciais inválidas"));
    }
}
