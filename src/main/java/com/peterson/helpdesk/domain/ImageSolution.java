package com.peterson.helpdesk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Entity
@Table(name = "image_solutions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ImageSolution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    @Lob
    @Column(name = "imagedata", length = 1000)
    private byte[] imageData;

    @OneToOne(mappedBy="imageSolution", cascade = CascadeType.ALL)
    @JsonIgnore
    private Solution solution;
}
