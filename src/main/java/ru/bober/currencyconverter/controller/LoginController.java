package ru.bober.currencyconverter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bober.currencyconverter.service.RegistrationService;

@Controller
public class LoginController {
    @Autowired
    RegistrationService registrationService;

    @GetMapping("/registration")
    public String addUser(@RequestParam String userName, @RequestParam String password){
        if(registrationService.findOneUserByLogin(userName)){
            return "redirect:/errorRegistration";
        }else {
            registrationService.createUser(userName, password);
            return "redirect:/";
        }
    }

    @GetMapping("/errorRegistration")
    public String errorRegistration(Model model){
        model.addAttribute("UserAlreadyExist","Пользователь с таким логином уже существует.");
        return "registration";
    }

    @GetMapping("/login")
    public String authorizationPage(){
        return "login";
    }

    @GetMapping("/register")
    public String addUser(){
        return "registration";
    }
}