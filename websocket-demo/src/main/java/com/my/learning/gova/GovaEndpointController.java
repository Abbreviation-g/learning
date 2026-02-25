package com.my.learning.gova;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/gova")
public class GovaEndpointController {

    /**
     * 跳转到websocketDemo.html页面，携带自定义的cid信息。
     * http://localhost:8081/demo/endpoint/user-1
     * http://localhost:8081/demo/endpoint/user-2
     *
     * @param requestId
     * @param model
     * @return
     */
    @GetMapping("/{requestId}")
    public String toWebSocketDemo(@PathVariable String requestId, Model model) {
        model.addAttribute("requestId", requestId);
        return "govaWebsocketMediaHandlerDemo";
    }
}
