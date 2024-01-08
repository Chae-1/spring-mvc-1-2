package hello.springmvc.basic.response;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("hello");
        mav.addObject("data", "hello");

        return mav;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "hello1");
        return "hello";
    }

    @RequestMapping("/hello")
    public String responseViewV3(Model model) {
        model.addAttribute("data", "hello1");
        return "hello";
    }
}
