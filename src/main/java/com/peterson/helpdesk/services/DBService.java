package com.peterson.helpdesk.services;

import com.peterson.helpdesk.domain.Chamado;
import com.peterson.helpdesk.domain.Cliente;
import com.peterson.helpdesk.domain.ProductCategory;
import com.peterson.helpdesk.domain.Tecnico;
import com.peterson.helpdesk.domain.enums.Perfil;
import com.peterson.helpdesk.domain.enums.Prioridade;
import com.peterson.helpdesk.domain.enums.Status;
import com.peterson.helpdesk.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class DBService {

    @Autowired
    private ChamadoRepository chamadoRepository;
    @Autowired
    private PessoaRepository pessoaRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private BCryptPasswordEncoder encoder;

    public void instanciaDB() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setId(1);
        productCategory.setName("Micrófonos y sonido");
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setId(2);
        productCategory2.setName("Adaptadores Hub");

        Tecnico tec1 = new Tecnico(null, "Valdir Cezar", "550.482.150-95", "valdir@gmail.com", encoder.encode("123"));
        tec1.addPerfil(Perfil.ADMIN);
        Tecnico tec2 = new Tecnico(null, "Richard Stallman", "903.347.070-56", "stallman@gmail.com", encoder.encode("123"));
        Tecnico tec3 = new Tecnico(null, "Claude Elwood Shannon", "271.068.470-54", "shannon@gmail.com", encoder.encode("123"));
        Tecnico tec4 = new Tecnico(null, "Tim Berners-Lee", "162.720.120-39", "lee@gmail.com", encoder.encode("123"));
        Tecnico tec5 = new Tecnico(null, "Linus Torvalds", "778.556.170-27", "linus@gmail.com", encoder.encode("123"));

        Cliente cli1 = new Cliente(null, "Albert Einstein", "111.661.890-74", "einstein@gmail.com", encoder.encode("123"));
        Cliente cli2 = new Cliente(null, "Marie Curie", "322.429.140-06", "curie@gmail.com", encoder.encode("123"));
        Cliente cli3 = new Cliente(null, "Charles Darwin", "792.043.830-62", "darwin@gmail.com", encoder.encode("123"));
        Cliente cli4 = new Cliente(null, "Stephen Hawking", "177.409.680-30", "hawking@gmail.com", encoder.encode("123"));
        Cliente cli5 = new Cliente(null, "Max Planck", "081.399.300-83", "planck@gmail.com", encoder.encode("123"));

        Chamado c1 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Error al instalar drivers en camara web", "Teste chamado 1", tec1, cli1);
        Chamado c2 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Problemas con el DPI mouse", "Teste chamado 2", tec1, cli2);
        Chamado c3 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Problema con la luz led de silla gamer", "Teste chamado 3", tec2, cli3);
        Chamado c4 = new Chamado(null, Prioridade.ALTA, Status.ABERTO, "Problema con la bateria de notebook", "Teste chamado 4", tec3, cli3);
        Chamado c5 = new Chamado(null, Prioridade.MEDIA, Status.ANDAMENTO, "Le falta un componente al microfono", "Teste chamado 5", tec2, cli1);
        Chamado c6 = new Chamado(null, Prioridade.BAIXA, Status.ENCERRADO, "Error al ajustar volumen en audifono" +
                "", "problemas con ajuste del cabezal de soporte de pantalla", tec1, cli5);

        pessoaRepository.saveAll(Arrays.asList(tec1, tec2, tec3, tec4, tec5, cli1, cli2, cli3, cli4, cli5));
        chamadoRepository.saveAll(Arrays.asList(c1, c2, c3, c4, c5, c6));
        productCategoryRepository.saveAll(Arrays.asList(productCategory, productCategory2));
    }
}
