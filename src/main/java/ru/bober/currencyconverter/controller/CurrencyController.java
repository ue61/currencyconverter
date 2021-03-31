package ru.bober.currencyconverter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bober.currencyconverter.entity.ValutesCurrency;
import ru.bober.currencyconverter.repository.CurrencyRepository;
import ru.bober.currencyconverter.service.CurrencyService;
import ru.bober.currencyconverter.service.HistoryService;

import java.util.Currency;

@Controller
public class CurrencyController {
    @Autowired
    CurrencyService currencyService;
    @Autowired
    CurrencyRepository currencyRepository;
    @Autowired
    HistoryService historyService;

    @GetMapping("/converter")
    public String calc(Model model){
        currencyService.getValutes();
        model.addAttribute("first",currencyRepository.findByValutename("Австралийский доллар"));
        model.addAttribute("second",currencyRepository.findByValutename("Австралийский доллар"));
        model.addAttribute("names", currencyService.getValutesNames());
        return "converter";
    }
    @GetMapping("/toconvert")
    public String convert(@RequestParam String vn1, @RequestParam String vn2, @RequestParam String num, Model model){
        if (vn1==null && currencyService.getMementoValuteNameOne()==null){
            vn1 = currencyRepository.findByValutename("Австралийский доллар").getValutename();
            currencyService.setMementoValuteNameOne(vn1);
        }
        else if (vn1==null && currencyService.getMementoValuteNameOne()!=null) {
            vn1 = currencyService.getMementoValuteNameOne();
        }
        else {
            currencyService.setMementoValuteNameOne(vn1);
        }
        if (vn2==null && currencyService.getMementoValuteNameTwo()==null){
            vn2 = currencyRepository.findByValutename("Австралийский доллар").getValutename();
            currencyService.setMementoValuteNameTwo(vn2);
        }
        else if (vn2==null && currencyService.getMementoValuteNameTwo()!=null) {
            vn2 = currencyService.getMementoValuteNameTwo();
        }
        else {
            currencyService.setMementoValuteNameTwo(vn2);
        }
        model.addAttribute("first",currencyRepository.findByValutename(vn1));
        model.addAttribute("second",currencyRepository.findByValutename(vn2));
        if (num == "" && currencyService.getMementoNum()==null){
            model.addAttribute("answer", "Вы не ввели, сколько нужно перевести");
        }
        else if(num == "" && currencyService.getMementoNum()!=null){
                float buf = Float.parseFloat(currencyService.getMementoNum());
                model.addAttribute("number", currencyService.getMementoNum());
                model.addAttribute("answer", currencyService.toConvert(vn1, vn2, buf));
        }
        else {
            try {
                Float.parseFloat(num);
            } catch (NumberFormatException e){
                num= "error";
                model.addAttribute("answer", "Вы ввели не число");
            }
            if(num != "error") {
                currencyService.setMementoNum(num);
                float buf = Float.parseFloat(num);
                model.addAttribute("number", num);
                model.addAttribute("answer", currencyService.toConvert(vn1, vn2, buf));
                historyService.addRecord(SecurityContextHolder.getContext().getAuthentication().getName(),vn1,vn2);
            }
        }
        model.addAttribute("names", currencyService.getValutesNames());
        return "converter";
    }
}
