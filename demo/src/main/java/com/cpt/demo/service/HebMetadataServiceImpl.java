package com.cpt.demo.service;

import com.cpt.demo.dto.HebMetadataDto;
import com.cpt.demo.model.HebMetadata;
import com.cpt.demo.repository.HebMetadataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HebMetadataServiceImpl implements HebMetadataService {
    
    @Autowired
    private HebMetadataRepository hebMetadataRepository;
    
    @Override
    public List<HebMetadataDto> getAllMetadata() {
        return hebMetadataRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    private HebMetadataDto convertToDto(HebMetadata entity) {
        HebMetadataDto dto = new HebMetadataDto();
        dto.setId(entity.getId());
        dto.setFieldname(entity.getFieldname());
        dto.setFieldvalue(entity.getFieldvalue());
        dto.setActive(entity.getActive());
        dto.setOrdernumber(entity.getOrdernumber());
        return dto;
    }
}
