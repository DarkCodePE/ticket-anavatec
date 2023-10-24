package com.peterson.helpdesk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity(name = "comment")
@Table(name="comments")
public class Comment {
    @Id
    @Column(name = "comment_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    @Column(name = "description", unique = true)
    private String description;

    @OneToOne
    @JoinColumn(name = "tecnico_id")
    private Tecnico tecnico;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false,insertable = false, updatable = false)
    private Timestamp createdAt;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "recommendation_id")
    private Recommendation recommendation;
}
