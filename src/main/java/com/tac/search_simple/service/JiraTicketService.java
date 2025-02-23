package com.tac.search_simple.service;

import com.tac.search_simple.dto.JiraTicketDTO;
import com.tac.search_simple.entity.JiraTicket;
import com.tac.search_simple.mappers.JiraTicketMapper;
import com.tac.search_simple.repo.JiraTicketRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JiraTicketService {

    private final JiraTicketRepo jiraTicketRepo;

    public List<JiraTicket> saveJiraTickets(List<JiraTicket> tickets) {
        return jiraTicketRepo.saveAll(tickets);
    }
    @Transactional
    public Map<String,JiraTicketDTO> create(JiraTicketDTO jiraTicketDTO) {

        Map<String,JiraTicketDTO > map = new HashMap<>();
        JiraTicket result=null;
        if(jiraTicketDTO!=null) {
            JiraTicket jiraTicket = new JiraTicket();
            jiraTicket.setUniqueId(UUID.randomUUID());
            jiraTicket.setId(jiraTicketDTO.getId());
            jiraTicket.setSummary(jiraTicketDTO.getSummary());
            jiraTicket.setDescription(jiraTicketDTO.getDescription());
            jiraTicket.setReporter(jiraTicketDTO.getReporter());
            jiraTicket.setCreatedAt(ZonedDateTime.now());

            result= jiraTicketRepo.save(jiraTicket);
        }
         map.put("Jira Ticket Created Successfully",JiraTicketMapper.toDTO(result));
        return map;
    }

   public Map<String,JiraTicketDTO> getById(UUID uniqueId){
        Map<String,JiraTicketDTO>map=new HashMap<>();
       JiraTicket result =jiraTicketRepo.findByUniqueId(uniqueId).orElseThrow(()-> new RuntimeException("Jira Ticket Not Found  with given :"+uniqueId));
       if(result!=null) {
           map.put("Jira Ticket Found Sucessfully", JiraTicketMapper.toDTO(result));
       }
  return map;
   }

   @Transactional
   public Map<String,String> deleteById(UUID uniqueId){
        Map<String ,String>map=new HashMap<>();
        JiraTicket jiraTicket=jiraTicketRepo.findByUniqueId(uniqueId).orElseThrow(()->new RuntimeException("Jira Ticket Not Found with given:"+uniqueId));
            jiraTicketRepo.deleteByUniqueId(uniqueId);
            map.put("Jira Ticket Deleted Sucessfully with Id:",jiraTicket.getId());
            return map;
   }
   @Transactional
   public Map<String,JiraTicketDTO> updateById(UUID uniqueId,JiraTicketDTO jiraTicketDTO){
        Map<String,JiraTicketDTO> map= new HashMap<>();
      JiraTicket existingJiraTicket =jiraTicketRepo.findByUniqueId(uniqueId).orElseThrow(()->new RuntimeException("Jira Ticket Not Found with given:"+uniqueId));
       if(existingJiraTicket!=null){

           if(jiraTicketDTO.getSummary()!=null&& !jiraTicketDTO.getSummary().isEmpty()){
               existingJiraTicket.setSummary(jiraTicketDTO.getSummary());
           }
           if(jiraTicketDTO.getDescription()!=null&& !jiraTicketDTO.getDescription().isEmpty()){
               existingJiraTicket.setDescription(jiraTicketDTO.getDescription());
           }
           if(jiraTicketDTO.getReporter()!=null&&!jiraTicketDTO.getReporter().isEmpty()){
               existingJiraTicket.setReporter(jiraTicketDTO.getReporter());
           }
           if(jiraTicketDTO.getId()!=null&&!jiraTicketDTO.getId().isEmpty()){
               existingJiraTicket.setId(jiraTicketDTO.getId());
           }
           existingJiraTicket.setUpdatedAt(ZonedDateTime.now());
       }
       map.put("Jira Ticket Updated Successfully",JiraTicketMapper.toDTO(jiraTicketRepo.save(existingJiraTicket)));
       return map;

   }

   public Map<String,List<JiraTicketDTO>>getAll(){
        Map<String,List<JiraTicketDTO>>map = new HashMap<>();
         List<JiraTicketDTO>jiraTicketDTOList=jiraTicketRepo.findAll().stream().map(JiraTicketMapper::toDTO).toList();
         map.put("All Jira Tickets ",jiraTicketDTOList);
         return map;
   }
//    @Transactional
//    public String bulkDelete(List<UUID> uniqueIds) {
//        for(UUID uniqueId:uniqueIds){
//            if(jiraTicketRepo.findByUniqueId(uniqueId).isPresent()){
//                jiraTicketRepo.deleteByUniqueId(uniqueId);
//            }else{
//                throw new RuntimeException("Jira Ticket not found for given:"+uniqueId);
//            }
//        }
//       return "All Jira Tickets Deleted Successfully";
//    }

    @Transactional
    public Map<String,List<String>> bulkDelete(List<UUID> uniqueIds) {
        Map<String,List<String>>map=new HashMap<>();
        List<JiraTicket>ticketsToDelete=uniqueIds.stream().map(uniqueId->jiraTicketRepo.findByUniqueId(uniqueId).orElseThrow(()->new RuntimeException("Jira Ticket not found for given id:"+uniqueId)))
                .collect(Collectors.toList());
        List<String>deletedIds=ticketsToDelete.stream().map((jiraTicket)->jiraTicket.getId()).collect(Collectors.toList());
        jiraTicketRepo.deleteAll(ticketsToDelete);
         map.put("All Jira Tickets Deleted Successfully with ids:",deletedIds);
        return map;
    }



    @Transactional
    public Map<String,List<JiraTicketDTO>> bulkCreate(List<JiraTicketDTO> jiraTicketDTOList) {
//        for(JiraTicketDTO jiraTicketDTO:jiraTicketDTOList){
//            create(jiraTicketDTO);
//        }
//        return "All right tickets created sucessfully";
//        ====================
        Map<String,List<JiraTicketDTO>>map = new HashMap<>();
        List<JiraTicket>jiraTicketList=jiraTicketDTOList.stream().map(jiraTicketDTO -> {
            JiraTicket jiraTicket = new JiraTicket();
            jiraTicket.setId(jiraTicketDTO.getId());
            jiraTicket.setSummary(jiraTicketDTO.getSummary());
            jiraTicket.setDescription(jiraTicketDTO.getDescription());
            jiraTicket.setReporter(jiraTicketDTO.getReporter());
            jiraTicket.setCreatedAt(ZonedDateTime.now());
            jiraTicket.setUniqueId(UUID.randomUUID());
            return jiraTicket;
        }).collect(Collectors.toList());
        jiraTicketRepo.saveAll(jiraTicketList);
         map.put("All Jira Tickets Created Successfully",jiraTicketList.stream().map((jiraTicket)->{ return JiraTicketMapper.toDTO(jiraTicket);}).collect(Collectors.toList()));
         return map;
    }
     @Transactional
    public Map<String,List<JiraTicketDTO>> bulkUpdate(List<JiraTicketDTO> jiraTicketDTOList, List<UUID> uniqueIds) {
//        for(int i=0;i<uniqueIds.size();i++){
//            updateById(uniqueIds.get(i),jiraTicketDTOList.get(i));
//        }
//        return jiraTicketDTOList;
//         ========
         Map<String,List<JiraTicketDTO>> map=new HashMap<>();
         List<JiraTicket> existingTickets = jiraTicketRepo.findByUniqueIds(uniqueIds);
         if(existingTickets.size()!=jiraTicketDTOList.size()){
             throw new IllegalArgumentException("Mismatch between IDs and DTO list sizes.");
         }
         for(int i=0;i<existingTickets.size();i++){
             JiraTicket existingJiraTicket=existingTickets.get(i);
             JiraTicketDTO jiraTicketDTO =jiraTicketDTOList.get(i);
             if(jiraTicketDTO.getId()!=null && !jiraTicketDTO.getId().isEmpty()){
                 existingJiraTicket.setId(jiraTicketDTO.getId());
             }
             if(jiraTicketDTO.getSummary()!=null&& !jiraTicketDTO.getSummary().isEmpty()){
                 existingJiraTicket.setSummary(jiraTicketDTO.getSummary());
             }
             if(jiraTicketDTO.getDescription()!=null&& !jiraTicketDTO.getDescription().isEmpty()){
                 existingJiraTicket.setDescription(jiraTicketDTO.getDescription());
             }
             if(jiraTicketDTO.getReporter()!=null&&!jiraTicketDTO.getReporter().isEmpty()){
                 existingJiraTicket.setReporter(jiraTicketDTO.getReporter());
             }
                 existingJiraTicket.setUpdatedAt(ZonedDateTime.now());
         }
         jiraTicketRepo.saveAll(existingTickets);
         map.put("All Jira Tickets Updated Successfully",existingTickets.stream().map(JiraTicketMapper::toDTO).toList());
       return map;
    }

}
