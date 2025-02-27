package com.tac.search_simple.mappers;

import com.tac.search_simple.dto.JiraTicketDTO;
import com.tac.search_simple.entity.JiraTicket;

import java.time.ZonedDateTime;

public class JiraTicketMapper {

    public static JiraTicketDTO toDTO(JiraTicket jiraTicket){

        JiraTicketDTO jiraTicketDTO = new JiraTicketDTO();
         if(jiraTicket!=null){
             jiraTicketDTO.setId(jiraTicket.getId());
             jiraTicketDTO.setDescription(jiraTicket.getDescription());
             jiraTicketDTO.setSummary(jiraTicket.getSummary());
             jiraTicketDTO.setReporter(jiraTicket.getReporter());
             jiraTicketDTO.setCreatedAt(jiraTicket.getCreatedAt());
             jiraTicketDTO.setUpdatedAt(jiraTicket.getUpdatedAt());
             jiraTicketDTO.setLabels(jiraTicket.getLabels());
         }
         return jiraTicketDTO;

    }

    public static JiraTicket toEntity(JiraTicketDTO jiraTicketDTO){
        JiraTicket jiraTicket = new JiraTicket();
        if(jiraTicketDTO!=null){
            jiraTicket.setId(jiraTicketDTO.getId());
            jiraTicket.setDescription(jiraTicketDTO.getDescription());
            jiraTicket.setSummary(jiraTicketDTO.getSummary());
            jiraTicket.setReporter(jiraTicketDTO.getReporter());
            jiraTicket.setCreatedAt(ZonedDateTime.now());
            jiraTicket.setUpdatedAt(jiraTicketDTO.getUpdatedAt());
            jiraTicket.setLabels(jiraTicketDTO.getLabels());
        }
        return jiraTicket;
    }

}

