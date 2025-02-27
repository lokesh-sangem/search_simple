package com.tac.search_simple.controller;

import com.tac.search_simple.dto.BulkUpdateRequestConfluence;
import com.tac.search_simple.dto.ConfluencePageDTO;
import com.tac.search_simple.service.ConfluencePageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/confluence")
@Validated
public class ConfluencePageController {

    private final ConfluencePageService confluencePageService;


    @PostMapping("/create")
    public ResponseEntity<Map<String,ConfluencePageDTO>> create(@RequestBody @Valid ConfluencePageDTO confluencePageDTO) {
        return ResponseEntity.ok(confluencePageService.create(confluencePageDTO));
    }
//    @PostMapping("/bulk-create")
//    public ResponseEntity<?>create(@RequestBody List<ConfluencePageDTO>confluencePageDTOList){
//        return ResponseEntity.ok(confluencePageService(confluencePageDTOList));
//    }

    @GetMapping("/get/{id}")
    public ResponseEntity <Map<String,ConfluencePageDTO>> getById(@PathVariable("id") UUID uniqueId) {
        return ResponseEntity.ok(confluencePageService.getById(uniqueId));
    }

    @GetMapping("/findAll")
    public ResponseEntity<Map<String,List<ConfluencePageDTO>>> getAll() {
        return ResponseEntity.ok(confluencePageService.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String,ConfluencePageDTO>> updateById(@PathVariable("id") UUID uniqueId, @RequestBody @Valid ConfluencePageDTO updatedDTO) {
        return ResponseEntity.ok(confluencePageService.updateById(uniqueId, updatedDTO));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String,String>> deleteById(@PathVariable("id") UUID uniqueId) {
        return ResponseEntity.ok(confluencePageService.deleteById(uniqueId));
    }

    @PostMapping("/bulk-create")
    public ResponseEntity<Map<String,List<ConfluencePageDTO>>>create(@RequestBody List<ConfluencePageDTO>confluencePageDTOList){
        return ResponseEntity.ok(confluencePageService.bulkCreate(confluencePageDTOList));
    }
    @DeleteMapping("/bulk-delete")
    public ResponseEntity<Map<String,List<String>>>bulkDelete(@RequestBody List<UUID>uniqueIds){
        return ResponseEntity.ok(confluencePageService.bulkDelete(uniqueIds));
    }
//    @PutMapping("/bulk-update")
//    public ResponseEntity<Map<String,List<ConfluencePageDTO>>>bulkUpdate(@RequestBody List<ConfluencePageDTO>confluencePageDTOList,@RequestParam List<UUID>uniqueIds){
//     return ResponseEntity.ok(confluencePageService.bulkUpdate(confluencePageDTOList,uniqueIds));
//    }

    @PutMapping("/bulk-update")
    public ResponseEntity<Map<String,List<ConfluencePageDTO>>>bulkUpdate(@RequestBody BulkUpdateRequestConfluence bulkUpdateRequestConfluence){
     return ResponseEntity.ok(confluencePageService.bulkUpdate(bulkUpdateRequestConfluence.getConfluencePageDTOList(),bulkUpdateRequestConfluence.getUniqueIds()));
    }
}



