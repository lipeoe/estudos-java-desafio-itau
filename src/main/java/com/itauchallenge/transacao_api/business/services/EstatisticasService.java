package com.itauchallenge.transacao_api.business.services;

import com.itauchallenge.transacao_api.controller.dtos.EstatisticasResponseDTO;
import com.itauchallenge.transacao_api.controller.dtos.TransacaoRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class EstatisticasService {
    public final TransacaoService transacaoService;

    public EstatisticasResponseDTO calcularEstatisticaDeTrasacoes(Integer searchRange) {
        log.info("Iniciada a busca de estatisticas de trasacoes pelo periodo de tempo" + searchRange);
        List<TransacaoRequestDTO> transacoes = transacaoService.buscarTransacoes(searchRange);

        if(transacoes.isEmpty()) {
            return new EstatisticasResponseDTO(0L, 0.0, 0.0, 0.0, 0.0);
        }

        DoubleSummaryStatistics estatisticasTransacoes = transacoes.stream()
                .mapToDouble(TransacaoRequestDTO::valor).summaryStatistics();
        log.info("Estatisticas retornadas com sucessor");
        return new EstatisticasResponseDTO(estatisticasTransacoes.getCount(),
                estatisticasTransacoes.getSum(),
                estatisticasTransacoes.getAverage(),
                estatisticasTransacoes.getMin(),
                estatisticasTransacoes.getMax());
    }

}
