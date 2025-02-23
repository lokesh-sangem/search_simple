package com.tac.search_simple.controller;

import com.tac.search_simple.dto.JiraTicketDTO;
import com.tac.search_simple.entity.JiraTicket;
import com.tac.search_simple.service.JiraTicketService;
import com.tac.search_simple.service.SearchService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
@CrossOrigin(origins="http://localhost:4200")
public class SearchController {

    private final SearchService searchService;



    @GetMapping
    public ResponseEntity<?> search(
            @RequestParam String query,
            @RequestParam String type,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) throws IOException {
        return ResponseEntity.ok(searchService.search(query, type, page, size));
    }
}








