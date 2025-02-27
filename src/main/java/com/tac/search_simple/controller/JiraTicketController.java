package com.tac.search_simple.controller;

import com.tac.search_simple.dto.BulkUpdateRequestJira;
import com.tac.search_simple.dto.JiraTicketDTO;
import com.tac.search_simple.service.JiraTicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/jira")
@RequiredArgsConstructor
public class JiraTicketController {
    private final JiraTicketService jiraTicketService;

    @PostMapping("/create")
    public ResponseEntity<Map<String,JiraTicketDTO>> create(@RequestBody JiraTicketDTO jiraTicketDTO) {
        return ResponseEntity.ok(jiraTicketService.create(jiraTicketDTO));
    }

    @PostMapping("/bulk-create")
    public ResponseEntity<Map<String,List<JiraTicketDTO>>>create(@RequestBody List<JiraTicketDTO>jiraTicketDTOList){
        return ResponseEntity.ok(jiraTicketService.bulkCreate(jiraTicketDTOList));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity <Map<String,JiraTicketDTO>>getById(@PathVariable("id") UUID uniqueId) {
        return ResponseEntity.ok(jiraTicketService.getById(uniqueId));
    }

    @GetMapping("/findAll")
    public ResponseEntity<Map<String,List<JiraTicketDTO>>> getAll() {
        return ResponseEntity.ok(jiraTicketService.getAll());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String,JiraTicketDTO>> updateById(@PathVariable("id") UUID uniqueId, @RequestBody JiraTicketDTO updatedDTO) {
        return ResponseEntity.ok(jiraTicketService.updateById(uniqueId, updatedDTO));
    }


@PutMapping("/bulk-update")
public ResponseEntity<Map<String,List<JiraTicketDTO>>> bulkUpdate(@RequestBody BulkUpdateRequestJira bulkUpdateRequest){
    return ResponseEntity.ok(jiraTicketService.bulkUpdate(bulkUpdateRequest.getJiraTicketDTOList(),bulkUpdateRequest.getUniqueIds()));
}

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Map<String,String>>deleteById(@PathVariable("id") UUID uniqueId) {
        return ResponseEntity.ok(jiraTicketService.deleteById(uniqueId));
    }

    @DeleteMapping("/bulk-delete")
    public ResponseEntity<Map<String,List<String>>>bulkDelete(@RequestBody List<UUID>uniqueIds){
        return ResponseEntity.ok(jiraTicketService.bulkDelete(uniqueIds));
    }
}
