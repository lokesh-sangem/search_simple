package com.tac.search_simple.mappers;

import com.tac.search_simple.dto.JiraTicketDTO;
import com.tac.search_simple.entity.JiraTicket;

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
         }
         return jiraTicketDTO;

    }

    public static JiraTicket toEntity(JiraTicketDTO jiraTicketDTO){
        JiraTicket jiraTicket = new JiraTicket();
        if(jiraTicketDTO!=null){
            jiraTicket.setId(jiraTicket.getId());
            jiraTicket.setDescription(jiraTicket.getDescription());
            jiraTicket.setSummary(jiraTicket.getSummary());
            jiraTicket.setReporter(jiraTicket.getReporter());
            jiraTicket.setCreatedAt(jiraTicket.getCreatedAt());
            jiraTicket.setUpdatedAt(jiraTicket.getUpdatedAt());
        }
        return jiraTicket;
    }

}

