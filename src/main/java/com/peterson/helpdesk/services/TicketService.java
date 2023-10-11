package com.peterson.helpdesk.services;

import com.peterson.helpdesk.domain.Chamado;
import com.peterson.helpdesk.domain.Product;
import com.peterson.helpdesk.domain.ProductCategory;
import com.peterson.helpdesk.domain.dtos.ProductRequestDTO;
import com.peterson.helpdesk.repositories.ChamadoRepository;
import com.peterson.helpdesk.services.base.BaseService;

import java.util.List;

public class TicketService extends BaseService<Chamado, Integer, ChamadoRepository> {
    private final ChamadoRepository chamadoRepository;

    public TicketService(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }
    public List<Chamado> findTicketsByProductId(Integer productId){
        return chamadoRepository.findByProduct_Id(productId);
    }

}
