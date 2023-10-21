package com.peterson.helpdesk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity(name = "recommendation")
@Table(name="recommendations")
public class Recommendation {
    @Id
    @Column(name = "recommendation_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "description", unique = true)
    @NotBlank(message = "description can not be blank")
    private String description;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "solution_id")
    private Solution solution;

    public Recommendation() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Solution getSolution() {
        return solution;
    }

    public void setSolution(Solution solution) {
        this.solution = solution;
    }
}
