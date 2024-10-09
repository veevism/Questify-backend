package com.backend.questify.Controller;

import com.backend.questify.Model.ApiResponse;
import com.backend.questify.Service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private ExportService exportService;

    @GetMapping("")
    @PreAuthorize("permitAll()")
    public ResponseEntity<byte[]> exportReport(@RequestParam UUID laboratoryId) throws IOException {
        return exportService.exportReport(laboratoryId);
    }
}
