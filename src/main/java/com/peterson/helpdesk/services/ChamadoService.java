package com.peterson.helpdesk.services;


import com.peterson.helpdesk.domain.Chamado;
import com.peterson.helpdesk.domain.Cliente;
import com.peterson.helpdesk.domain.Product;
import com.peterson.helpdesk.domain.Tecnico;
import com.peterson.helpdesk.domain.dtos.ChamadoDTO;
import com.peterson.helpdesk.domain.dtos.ChamadoExpiredDTO;
import com.peterson.helpdesk.domain.dtos.TopDTO;
import com.peterson.helpdesk.domain.dtos.TopTecnicoDTO;
import com.peterson.helpdesk.domain.enums.Prioridade;
import com.peterson.helpdesk.domain.enums.Status;
import com.peterson.helpdesk.repositories.ChamadoRepository;
import com.peterson.helpdesk.services.exceptions.ObjectnotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.nio.file.LinkOption;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@Slf4j(topic = "Chamado")
public class ChamadoService {

    @Autowired
    private ChamadoRepository repository;
    @Autowired
    private TecnicoService tecnicoService;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private ProductService productService;
    public Chamado findById(Integer id) {
        Optional<Chamado> obj = repository.findById(id);
        return obj.orElseThrow(() -> new ObjectnotFoundException("Objeto não encontrado! ID: " + id));
    }


    public List<Chamado> findAll() {
        return repository.findAll();
    }

    public Chamado create(ChamadoDTO obj) {

        return repository.save(newChamado(obj));
    }

    public Chamado update(Integer id, @Valid ChamadoDTO objDTO) {
        objDTO.setId(id);
        Chamado oldObj = findById(id);
        oldObj = newChamado(objDTO);
        return repository.save(oldObj);
    }

    private Chamado newChamado(ChamadoDTO obj) {
        Tecnico tecnico = tecnicoService.findById(obj.getTecnico());
        Cliente cliente = clienteService.findById(obj.getCliente());
        Product product = productService.findBySku(obj.getProductId()).orElseThrow(() -> new RuntimeException("NO EXISTE PRODUCTO"));
        Chamado chamado = new Chamado();
        if(obj.getId() != null) {
            chamado.setId(obj.getId());
        }

        if(obj.getStatus().equals(2)) {
            chamado.setDataFechamento(LocalDate.now().plusDays(1));
        }else if (obj.getStatus().equals(1)){
            chamado.setDataFechamento(LocalDate.now().plusDays(2));
        }else {
            chamado.setDataFechamento(LocalDate.now().plusDays(3));
        }

        chamado.setTecnico(tecnico);
        chamado.setCliente(cliente);
        chamado.setPrioridade(Prioridade.toEnum(obj.getPrioridade()));
        chamado.setStatus(Status.toEnum(obj.getStatus()));
        chamado.setTitulo(obj.getTitulo());
        chamado.setObservacoes(obj.getObservacoes());
        chamado.setProduct(product);
        return chamado;
    }

    public  List<ChamadoExpiredDTO> chamadoExpired() {
        List<Chamado> chamados = repository.findAll();
        return chamados
                .stream()
                .filter(x -> x.getDataFechamento().isBefore(LocalDate.now()) && x.getStatus().equals(Status.ANDAMENTO) || x.getStatus().equals(Status.ABERTO))
                .map(x -> {
                    LocalDate dataFechamento = x.getDataFechamento();
                    LocalDate now = LocalDate.now();
                    Integer dias = now.compareTo(dataFechamento);
                    Long diasBetween = DAYS.between(dataFechamento, now);
                    String statusName = "";
                    if (diasBetween == 1)
                        statusName = "Por vencer";
                    else if (diasBetween > 1)
                        statusName = "Vencida";
                    else if (diasBetween < 0)
                        statusName = "En curso";
                    else
                        statusName = "En curso";
                    return ChamadoExpiredDTO.builder()
                            .id(x.getId())
                            .nomeTecnico(x.getTecnico().getNome())
                            .titulo(x.getTitulo())
                            .prioridade(x.getPrioridade())
                            .dataFechamento(x.getDataFechamento())
                            .status(x.getStatus())
                            .statusName(statusName)
                            .countDays(diasBetween.intValue())
                            .build();
                }).collect(Collectors.toList());
    }
    public TopDTO topTecnicoByChamados(){
        //Total de ticketResuelto
        List<Chamado> chamados = repository.findAll();
        int total = chamados.size();
        int totalSolved = chamados.stream().filter(ticket -> ticket.getStatus().equals(Status.ENCERRADO)).collect(Collectors.toList()).size();

        //Total de ticketResuelto por tecnico
        Map<Tecnico, Long> ticketsSolvedByTechnician = chamados.stream()
                .filter(ticket -> ticket.getStatus().equals(Status.ENCERRADO))
                .collect(Collectors.groupingBy(Chamado::getTecnico, Collectors.counting()));

        List<TopTecnicoDTO> topTecnicoDTOS = new ArrayList<>();
        for (Map.Entry<Tecnico, Long> entry : ticketsSolvedByTechnician.entrySet()) {
            topTecnicoDTOS.add(TopTecnicoDTO.builder()
                    .quantidade(entry.getValue().intValue())
                    .nome(entry.getKey().getNome())
                    .email(entry.getKey().getEmail())
                    .build());
        }

        return TopDTO.builder()
                .total(total)
                .totalSolved(totalSolved)
                .totalTechnician(ticketsSolvedByTechnician.size())
                .topTechnician(topTecnicoDTOS)
                .build();
    }
    //total tickets create
    public Integer totalTicketsCreate(){
        List<Chamado> chamados = repository.findAll();
        return chamados.size();
    }
    //total close tickets
    public Integer totalTicketsClose(){
        List<Chamado> chamados = repository.findAll();
        return chamados.stream().filter(ticket -> ticket.getStatus().equals(Status.ENCERRADO)).collect(Collectors.toList()).size();
    }
    //totalTicketsAssigned
    public Integer totalTicketsAssigned(){
        List<Chamado> chamados = repository.findAll();
        return chamados.stream().filter(ticket -> ticket.getStatus().equals(Status.ANDAMENTO)).collect(Collectors.toList()).size();
    }
    public Map<String, Integer> getTicketsAssignedByTechnician() {
        // Obtén todos los tickets
        List<Chamado> tickets = repository.findAll();

        // Agrupa los tickets por técnico y cuenta cuántos tickets tiene cada técnico
        Map<Tecnico, Long> ticketsByTechnician = tickets.stream()
            .collect(Collectors.groupingBy(Chamado::getTecnico, Collectors.counting()));

        // Crea un nuevo mapa con los nombres de los técnicos y la cantidad de tickets
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<Tecnico, Long> entry : ticketsByTechnician.entrySet()) {
            result.put(entry.getKey().getNome(), entry.getValue().intValue());
        }

        return result;
    }
}
