package com.peterson.helpdesk.domain;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="products")
public class Product {
    @Id
    @Column(name = "product_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @NaturalId
    @Column(name = "SKU", unique = true)
    @NotBlank(message = "Product SKU cannot be null")
    private String sku;

    @Column(name = "TITLE", unique = true)
    @NotBlank(message = "Title can not be blank")
    private String title;

    @ManyToOne
    @JoinColumn(name="category_id")
    private ProductCategory category;

    @Column(name = "SUMMARY")
    @NotBlank(message = "Sort summary can not be blank")
    private String summary;

    @Column(name = "PRICE")
    //@NotBlank(message = "Price summary can not be blank")
    private float price;

    @Column(name = "STATUS")
    private boolean status;

    /*@Column(name = "IMAGE")
    private String image;*/
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="image_id")
    private Image image;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Chamado> tickets = new ArrayList<>();

    public Product(){
    }

    public Product(Integer id, String sku, String title,
                   ProductCategory category, String summary,
                   float price,
                   boolean status, Image image) {
        this.id = id;
        this.sku = sku;
        this.title = title;
        this.category = category;
        this.summary = summary;
        this.price = price;
        this.status = status;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public List<Chamado> getTickets() {
        return tickets;
    }

    public void setTickets(List<Chamado> tickets) {
        this.tickets = tickets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Product product = (Product) o;
        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}