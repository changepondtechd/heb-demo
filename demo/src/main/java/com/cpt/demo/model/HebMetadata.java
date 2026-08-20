package com.cpt.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "heb_metadata")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HebMetadata {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "fieldname", nullable = false)
    private String fieldname;
    
    @Column(name = "fieldvalue", nullable = false, length = 4000)
    private String fieldvalue;
    
    @Column(name = "active", nullable = false)
    private Boolean active;
    
    @Column(name = "ordernumber", nullable = false)
    private Integer ordernumber;
}
