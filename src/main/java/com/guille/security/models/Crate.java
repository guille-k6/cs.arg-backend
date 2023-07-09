package com.guille.security.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "crate")
public class Crate {
    @jakarta.persistence.Id
    @Id
    @Column(length = 60)
    private String id;

    @Column(length = 128)
    private String name;

    @Column(length = 16)
    private String first_sale_date;

    @Column(length = 256)
    private String image;
}