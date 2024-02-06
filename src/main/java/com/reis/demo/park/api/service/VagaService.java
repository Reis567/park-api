package com.reis.demo.park.api.service;

import org.springframework.stereotype.Service;

import com.reis.demo.park.api.repository.VagaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VagaService {
    private final VagaRepository vagaRepository;
}
