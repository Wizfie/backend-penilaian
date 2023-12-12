package example.penilaian.controller.penilaianYelyel;


import example.penilaian.service.penilaianYelyel.TeamsYelyelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/teams")
public class TeamsYelyelController {

    @Autowired
    private TeamsYelyelService teamsYelyelService;

    @GetMapping("/all")
    public List<example.penilaian.entity.penilaianYelyel.TeamsYelyel> getAllTeam(){
        return teamsYelyelService.getAll();
    }
}
