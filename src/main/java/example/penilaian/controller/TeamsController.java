package example.penilaian.controller;


import example.penilaian.entity.Teams;
import example.penilaian.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TeamsController {

    @Autowired
    private TeamsService teamsService;

    @GetMapping("/teams-all")
    public List<Teams> getAllTeam(){
        return teamsService.getAllTeam();
    }
}
