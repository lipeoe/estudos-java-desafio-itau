package com.itauchallenge.transacao_api.business.services;

import com.itauchallenge.transacao_api.controller.dtos.TransacaoRequestDTO;
import com.itauchallenge.transacao_api.infrastructure.exceptions.UnprocessableEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransacaoService {
    //Recebe uma lista de transações em um array list
    private final List<TransacaoRequestDTO> listaTransacoes = new ArrayList<>();

    public void adicionarTransacoes(TransacaoRequestDTO dto){
        log.info("Starting the process to record transactions" + dto);
        //exception for transactions after current date
        if(dto.dataHora().isAfter(OffsetDateTime.now())){
            log.error("Date and time is after now");
            throw new UnprocessableEntity("Data e hora maiores que a data e hora atuais");
        }
        if(dto.valor() < 0){
            log.error("The value is cannot less than zero");
            //exception for transactions less than zero
            throw new UnprocessableEntity("The value must be higher than zero");
        }
        //to store the transactions in memory
        listaTransacoes.add(dto);
        log.info("Transactions added successfully");

    }

    public void limparTransacoes(){
        //to clear all transactions in memory
        log.info("Starting the process to clear transactions");
        listaTransacoes.clear();
        log.info("Transactions deleted successfully");

    }

    //returns a list of transactions between choosen range
    public List<TransacaoRequestDTO> buscarTransacoes(Integer searchRange){
        log.info("Started searching for transactions with range " + searchRange);

        OffsetDateTime dataHoraIntervalo = OffsetDateTime.now().minusSeconds(searchRange);

        log.info("Return of transactions successfully");
        return listaTransacoes.stream().filter(transacao -> transacao.dataHora().isAfter(dataHoraIntervalo)).toList();
    }
}
