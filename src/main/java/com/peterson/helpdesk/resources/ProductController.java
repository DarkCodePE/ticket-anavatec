package com.peterson.helpdesk.resources;

import com.peterson.helpdesk.domain.Image;
import com.peterson.helpdesk.domain.Product;
import com.peterson.helpdesk.domain.ProductCategory;
import com.peterson.helpdesk.domain.dtos.ProductListDTO;
import com.peterson.helpdesk.domain.dtos.ProductRequestDTO;
import com.peterson.helpdesk.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@Slf4j(topic = "PRODUCT_CONTROLLER")
@RequestMapping(value = "/product")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping
    public ResponseEntity<List<ProductListDTO>>  getAllProduct(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.products());
    }
    @GetMapping("/categories")
    public ResponseEntity<List<ProductCategory>>  getAllCategories(){
        log.info("getAllCategories");
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.getAllCategories());
    }
    @GetMapping("/categories/list")
    public ResponseEntity<List<ProductCategory>>  getAllCategories2(){
        log.info("getAllCategories");
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.getAllCategories());
    }
    @PostMapping("/create")
    public ResponseEntity<List<ProductListDTO>> saveProduct(@RequestPart ProductRequestDTO productRequestDTO, @RequestPart MultipartFile file) throws IOException {
        log.info("image", file);
        log.info("productRequestDTO", productRequestDTO);
        List<ProductListDTO> products = productService.createOrUpdateProduct(productRequestDTO, file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(products);
    }

    @GetMapping("/info/{name}")
    public ResponseEntity<?>  getImageInfoByName(@PathVariable("name") String name){
        Image image = productService.getInfoByImageByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .body(image);
    }
}
