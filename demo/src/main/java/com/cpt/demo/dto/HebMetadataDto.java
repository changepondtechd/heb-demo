package com.cpt.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HebMetadataDto {
    
    private Long id;
    private String fieldname;
    private String fieldvalue;
    private Boolean active;
    private Integer ordernumber;
}
