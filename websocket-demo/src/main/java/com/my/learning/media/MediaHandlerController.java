package com.my.learning.media;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/demo3")
public class MediaHandlerController {

    /**
     * 跳转到websocketDemo.html页面，携带自定义的cid信息。
     * http://localhost:8081/demo3/handler/user-1
     *
     * @param cid
     * @param model
     * @return
     */
    @GetMapping("/handler/{cid}")
    public String toWebSocketDemo(@PathVariable String cid, Model model) {
        model.addAttribute("cid", cid);
        return "websocketMediaHandlerDemo";
    }

    @GetMapping("/RecordAndSaveDemo")
    public String RecordAndSaveDemo(Model model) {
        return "RecordAndSaveDemo";
    }
}
