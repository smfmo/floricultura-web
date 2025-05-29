package com.floriculturamonteiro.floricultura.Controllers.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record ClienteDto(
        @NotBlank(message = "Campo obrigatório")
                @Size(max = 255, min = 2, message = "Campo fora do tamanho padrão")
        String nome,
        @Pattern(regexp = "^(\\(?\\d{2}\\)?[\\s-]?)?(\\d{4,5}[\\s-]?\\d{4})$",
                message = "número de celular inválido Use (XX) XXXX-XXXX ou (XX) XXXXX-XXXX")
        String telefone,
        String cpf,
        @Email
        String email,
        EnderecoDto endereco) {
}
