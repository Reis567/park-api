package com.reis.demo.park.api.web.dto.mapper;
import com.reis.demo.park.api.entity.Vaga;
import com.reis.demo.park.api.web.dto.VagaCreateDTO;
import com.reis.demo.park.api.web.dto.VagaResponseDTO;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VagaMapper {

    public static Vaga toVaga(VagaCreateDTO vagaCreateDTO) {
        return new ModelMapper().map(vagaCreateDTO, Vaga.class);
    }

    public static VagaResponseDTO toDTO(Vaga vaga) {
        return new ModelMapper().map(vaga, VagaResponseDTO.class);
    }

    public static List<VagaResponseDTO> toDTOList(List<Vaga> vagas) {
        return vagas.stream()
                .map(VagaMapper::toDTO)
                .collect(Collectors.toList());
    }
}