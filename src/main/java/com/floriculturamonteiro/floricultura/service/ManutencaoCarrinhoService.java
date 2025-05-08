package com.floriculturamonteiro.floricultura.service;

import com.floriculturamonteiro.floricultura.model.Carrinho;
import com.floriculturamonteiro.floricultura.repositories.CarrinhoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ManutencaoCarrinhoService {

    private final CarrinhoRepository carrinhoRepository;

    private static final long TEMPO_MAXIMO_INATIVIDADE_MINUTOS = 2;

    @Scheduled(fixedRate = 60000) //1 min
    @Transactional
    public void removerCarrinhosAbandonados(){
        LocalDateTime limite = LocalDateTime.now()
                .minusMinutes(TEMPO_MAXIMO_INATIVIDADE_MINUTOS);

        List<Carrinho> carrinhosAbandonados = carrinhoRepository
                .findByUltimaAtualizacaoBeforeAndFinalizadoFalse(limite);

        carrinhoRepository.deleteAll(carrinhosAbandonados);
    }
}
