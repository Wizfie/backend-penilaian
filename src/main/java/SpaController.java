import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SpaController {

    @RequestMapping({
            "/",
            "/dashboard",
            "/history-lapangan",
            "/penilaian-lapangan",
            "/penilaian-yelyel",
            "/detail/**",
            "/history-lapangan-admin",
            "/history-yelyel-admin",
            "/presentasi",
            "/presentasi/detail-presentasi"
    })
    public String forward() {
        return "forward:/index.html";
    }
}
