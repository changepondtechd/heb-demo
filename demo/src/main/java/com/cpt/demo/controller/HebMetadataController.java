package com.cpt.demo.controller;

import com.cpt.demo.dto.HebMetadataDto;
import com.cpt.demo.service.HebMetadataService;
import com.cpt.demo.aspect.EnablePerfLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/metadata")
public class HebMetadataController {
    
    @Autowired
    private HebMetadataService hebMetadataService;
    
    @GetMapping
    @EnablePerfLog
    public ResponseEntity<List<HebMetadataDto>> getAllMetadata() {
        List<HebMetadataDto> metadata = hebMetadataService.getAllMetadata();
        return new ResponseEntity<>(metadata, HttpStatus.OK);
    }
}
