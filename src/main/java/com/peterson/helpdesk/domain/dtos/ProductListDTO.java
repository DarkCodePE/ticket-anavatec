package com.peterson.helpdesk.domain.dtos;

import com.peterson.helpdesk.domain.Chamado;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Builder
@Getter
public class ProductListDTO {
    private Integer id;
    @NotBlank(message = "Product SKU cannot be null")
    private String sku;
    @NotNull(message = "Ticket ID cannot be null")
    private List<Chamado> tickets;
    @NotBlank(message = "Title can not be blank")
    private String title;
    private String categoryName;
    @NotBlank(message = "Sort summary can not be blank")
    private String summary;

    private String imageUrl;
}
