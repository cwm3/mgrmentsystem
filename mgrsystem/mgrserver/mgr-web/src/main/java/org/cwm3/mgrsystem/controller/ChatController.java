package org.cwm3.mgrsystem.controller;

import org.cwm3.mgrsystem.model.Hr;
import org.cwm3.mgrsystem.service.IHrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {
    @Autowired
    IHrService hrService;
    @GetMapping("/hrs")
    public List<Hr> getAllHrs() {
        return hrService.getAllHrsExceptCurrentHr();
    }
}
