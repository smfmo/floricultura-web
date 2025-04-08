package com.floriculturamonteiro.floricultura.Controllers.mappers;

import com.floriculturamonteiro.floricultura.Controllers.dtos.ClienteDto;
import com.floriculturamonteiro.floricultura.model.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteDto dto);

    ClienteDto toDto(Cliente cliente);
}
