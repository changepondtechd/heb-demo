package com.cpt.demo.controller;

import com.cpt.demo.dto.HebMetadataDto;
import com.cpt.demo.service.HebMetadataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HebMetadataControllerTest {
    
    @Mock
    private HebMetadataService hebMetadataService;
    
    @InjectMocks
    private HebMetadataController hebMetadataController;
    
    private List<HebMetadataDto> sampleMetadata;
    
    @BeforeEach
    public void setUp() {
        HebMetadataDto dto1 = new HebMetadataDto(1L, "tenant_id", "HEB001", true, 1);
        HebMetadataDto dto2 = new HebMetadataDto(2L, "environment", "production", true, 2);
        HebMetadataDto dto3 = new HebMetadataDto(3L, "version", "1.0.0", true, 3);
        sampleMetadata = Arrays.asList(dto1, dto2, dto3);
    }
    
    @Test
    public void testGetAllMetadata_Success() {
        when(hebMetadataService.getAllMetadata()).thenReturn(sampleMetadata);
        
        ResponseEntity<List<HebMetadataDto>> response = hebMetadataController.getAllMetadata();
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(3, response.getBody().size());
        assertEquals("tenant_id", response.getBody().get(0).getFieldname());
    }
    
    @Test
    public void testGetAllMetadata_EmptyList() {
        when(hebMetadataService.getAllMetadata()).thenReturn(Arrays.asList());
        
        ResponseEntity<List<HebMetadataDto>> response = hebMetadataController.getAllMetadata();
        
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().size());
    }
    
    @Test
    public void testGetAllMetadata_ResponseHasRequiredFields() {
        when(hebMetadataService.getAllMetadata()).thenReturn(sampleMetadata);
        
        ResponseEntity<List<HebMetadataDto>> response = hebMetadataController.getAllMetadata();
        
        assertNotNull(response.getBody());
        HebMetadataDto firstRecord = response.getBody().get(0);
        assertNotNull(firstRecord.getId());
        assertNotNull(firstRecord.getFieldname());
        assertNotNull(firstRecord.getFieldvalue());
        assertNotNull(firstRecord.getActive());
        assertNotNull(firstRecord.getOrdernumber());
    }
}

