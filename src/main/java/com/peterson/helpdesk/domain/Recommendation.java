package com.peterson.helpdesk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @OneToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false,insertable = false, updatable = false)
    private Timestamp createdAt;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "recommendation")
    private List<Comment> comments = new ArrayList<>();
}
