package com.peterson.helpdesk.services;

import com.peterson.helpdesk.domain.Image;
import com.peterson.helpdesk.domain.Product;
import com.peterson.helpdesk.domain.ProductCategory;
import com.peterson.helpdesk.domain.dtos.ProductListDTO;
import com.peterson.helpdesk.domain.dtos.ProductRequestDTO;
import com.peterson.helpdesk.repositories.ImageRepository;
import com.peterson.helpdesk.repositories.ProductCategoryRepository;
import com.peterson.helpdesk.repositories.ProductRepository;
import com.peterson.helpdesk.resources.exceptions.NoSuchElementFoundException;
import com.peterson.helpdesk.services.base.BaseService;
import com.peterson.helpdesk.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "PRODUCT_SERVICE")
public class ProductService extends BaseService<Product, Integer, ProductRepository> {
    private final ProductCategoryRepository productCategoryRepository;
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;
    @Autowired
    public ProductService(ProductCategoryRepository productCategoryRepository, ProductRepository productRepository, ImageRepository imageRepository) {
        this.productCategoryRepository = productCategoryRepository;
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
    }
    public List<ProductListDTO> products() {
        return productRepository.findAll().stream().map(productDB -> {
           return ProductListDTO.builder()
                   .id(productDB.getId())
                   .tickets(productDB.getTickets())
                   .categoryName(productDB.getCategory().getName())
                   .title(productDB.getTitle())
                   .sku(productDB.getSku())
                   .summary(productDB.getSummary())
                   .imageUrl(ImageUtil.compressImageBase64(ImageUtil.decompressImage(productDB.getImage().getImageData()))).build();
        }).toList();
    }
    /**
     * Finds a user in the database by sku
     */
    public Optional<Product> findBySku(String sku) {
        return repository.findBySku(sku);
    }

    public List<ProductListDTO> createOrUpdateProduct(ProductRequestDTO productDTO, MultipartFile file) throws IOException {
        log.info("productDTO.getCategoryId(), {}", productDTO.getCategoryId());
        ProductCategory productCategory =
                productCategoryRepository.findById(productDTO.getCategoryId()).orElseThrow(() -> new NoSuchElementFoundException("item.absent", productDTO.getCategoryId()));
        Product product = productRepository.findBySku(productDTO.getSku()).orElse(new Product());
        Image imageDB = null;
        if (file != null){
            Image image;
            if (product.getId()!=null){
                image = imageRepository.findByProduct_Id(product.getId());
            }else {
                image = new Image();
            }
            image.setName(file.getOriginalFilename());
            image.setType(file.getContentType());
            image.setImageData(ImageUtil.compressImage(file.getBytes()));
            imageDB = imageRepository.save(image);
        }
        product.setCategory(productCategory);
        product.setImage(imageDB);
        product.setSku(productDTO.getSku());
        product.setTitle(productDTO.getTitle());
        product.setSummary(productDTO.getSummary());
        product.setStatus(true);
        productRepository.save(product);
        List<ProductListDTO> response = new ArrayList<>();
        productRepository.findAll().forEach(productDB -> {
           response.add(ProductListDTO.builder()
                   .categoryName(productDB.getCategory().getName())
                   .title(productDB.getTitle())
                   .sku(productDB.getSku())
                   .summary(productDB.getSummary())
                   .imageUrl(ImageUtil.compressImageBase64(ImageUtil.decompressImage(productDB.getImage().getImageData())))
                   .build());
        });
        return response;
    }

    @Transactional
    public Image getInfoByImageByName(String name) {
        Image dbImage = imageRepository.findByName(name).orElseThrow(() -> new RuntimeException("No exite imagen"));
        Image image = new Image();
        image.setName(dbImage.getName());
        image.setType(dbImage.getType());
        image.setImageData(ImageUtil.decompressImage(dbImage.getImageData()));
        return image;

    }

}
