package com.peterson.helpdesk.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "solution_files")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Files {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;

    @Lob
    @Column(name = "solution_file", length = 1000)
    private byte[] solution_file;
}
