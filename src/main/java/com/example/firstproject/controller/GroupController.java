package com.example.firstproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

//public class GroupController {
//    @Autowired
//    private GroupService groupService;
//    @Autowired
//    private TeamService teamService;
//
//    @GetMapping("/groups/{groupId}")
//    public String show(@PathVariable Long groupId, Model model){
//        //해당 메서드 작성
//        GroupDto groupDto = groupService.getGroup(groupId);
//        List<TeamDto> teamDtos = teamService.getTeamOnGroup(groupId);
//
//        model.addAttribute("group", groupDto);
//        model.addAttribute("teamDtos", teamDtos);
//        return "groups/show";
//    }
//}
