package com.peterson.helpdesk.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    @NotNull(message = "sku is required")
    private String sku;
    @NotNull(message = "title is required")
    private String title;
    private Integer categoryId;
    private String summary;
}
