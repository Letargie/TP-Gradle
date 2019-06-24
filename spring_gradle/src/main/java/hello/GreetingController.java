package hello;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes("valueSession2")
public class GreetingController {

    @GetMapping("/greeting")

    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model, HttpSession session, @CookieValue(value = "foo", defaultValue = "hello") String fooCookie, HttpServletResponse response  ) {
        model.addAttribute("name", name);
        session.setAttribute("valueSession", 42);
        response.addCookie(new Cookie("foo", "newValue"));
        return "greeting";
    }


    private int value = 42;
    @ModelAttribute("value")
    public int getValue()  {
        return value;
    }

    private int valueSession2=42;
    @ModelAttribute("valueSession2")
    public int getValueSession2()  {
        return valueSession2;
    }

    @RequestMapping("/endsession")
    public String endSession(SessionStatus status){
        status.setComplete();
        return "lastpage";
    }

    @ModelAttribute("someList")
    public List<String> getSomeList(){
        return Arrays.asList("Element 1", "Element 2", "Element 3");
    }
}

