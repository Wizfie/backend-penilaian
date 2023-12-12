package example.penilaian.controller.penilaianYelyel;

import example.penilaian.entity.penilaianYelyel.SubscriteriaYelyel;
import example.penilaian.service.penilaianYelyel.SubscriteriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class SubscriteriaController {

    @Autowired
    private SubscriteriaService subscriteriaService;

    @GetMapping("/question")
    public List<SubscriteriaYelyel>getAllSubs(){
        return subscriteriaService.getAllSubs();
    }

}
