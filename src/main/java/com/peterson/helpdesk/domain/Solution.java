package com.peterson.helpdesk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="solutions")
public class Solution {
    @Id
    @Column(name = "solution_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "TITLE", unique = true)
    @NotBlank(message = "Title can not be blank")
    private String title;

    @Column(name = "SUMMARY")
    @NotBlank(message = "Sort summary can not be blank")
    private String summary;

    @Column(name = "STATUS")
    private boolean status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="image_solution_id")
    private ImageSolution imageSolution;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "chamado_id")
    private Chamado chamado;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solution")
    private List<Recommendation> recommendations = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "solution")
    private List<Files> files = new ArrayList<>();

    public Solution(Integer id, String title, String summary, boolean status, Chamado chamado) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.status = status;
        this.chamado = chamado;
    }

    public Solution() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Chamado getChamado() {
        return chamado;
    }

    public void setChamado(Chamado chamado) {
        this.chamado = chamado;
    }

    public ImageSolution getImageSolution() {
        return imageSolution;
    }

    public void setImageSolution(ImageSolution imageSolution) {
        this.imageSolution = imageSolution;
    }

    public List<Recommendation> getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(List<Recommendation> recommendations) {
        this.recommendations = recommendations;
    }

    public List<Files> getFiles() {
        return files;
    }

    public void setFiles(List<Files> files) {
        this.files = files;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Solution solution = (Solution) o;
        return Objects.equals(id, solution.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
