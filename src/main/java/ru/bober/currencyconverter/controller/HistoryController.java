package ru.bober.currencyconverter.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bober.currencyconverter.repository.HistoryRepository;
import ru.bober.currencyconverter.service.HistoryService;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Controller
public class HistoryController {
    @Autowired
    HistoryService historyService;
    @Autowired
    HistoryRepository historyRepository;

    @GetMapping("/history")
    public String toHistory(Model model){
        model.addAttribute("filtered", historyRepository.findAllByName(SecurityContextHolder.getContext().getAuthentication().getName()));
        model.addAttribute("filters", historyRepository.findAllByName(SecurityContextHolder.getContext().getAuthentication().getName()));
        return "history";
    }
    @GetMapping("/filteredHistory")
    public String filteredHistory(@RequestParam String in_valute, @RequestParam String out_valute, @RequestParam String dateStr, Model model) throws ParseException {
        String buf = SecurityContextHolder.getContext().getAuthentication().getName();
        SimpleDateFormat sdfo = new SimpleDateFormat("yyyy-MM-dd");
        /*int daybuf = Integer.parseInt(dateStr.substring(5,7))-1;
        dateStr = dateStr.substring(0,5) + daybuf + dateStr.substring(7);*/
        java.util.Date date1;
        Date date;
        if (dateStr == ""){
            date = null;
        }
        else {
            date1 = sdfo.parse(dateStr);
            date = new Date(date1.getTime());
        }
        model.addAttribute("filters", historyRepository.findAll());
        if (date == null && in_valute == "" && out_valute == ""){
            model.addAttribute("filtered", historyRepository.findAllByName(buf));
        }
        else if (date != null && in_valute == "" && out_valute == ""){
            model.addAttribute("filtered", historyRepository.findAllByDateAndName(date,buf));
        }
        else if (date == null && in_valute != "" && out_valute == ""){
            model.addAttribute("filtered", historyRepository.findAllByInValuteAndName(in_valute,buf));
        }
        else if (date == null && in_valute == "" && out_valute != ""){
            model.addAttribute("filtered", historyRepository.findAllByOutValuteAndName(out_valute,buf));
        }
        else if (date != null && in_valute != "" && out_valute == ""){
            model.addAttribute("filtered", historyRepository.findAllByDateAndInValuteAndName(date, in_valute,buf));
        }
        else if (date != null && in_valute == "" && out_valute != ""){
            model.addAttribute("filtered", historyRepository.findAllByDateAndOutValuteAndName(date, out_valute,buf));
        }
        else if (date == null && in_valute != "" && out_valute != ""){
            model.addAttribute("filtered", historyRepository.findAllByInValuteAndOutValuteAndName(in_valute, out_valute,buf));
        }
        else if (date != null && in_valute != "" && out_valute != ""){
            model.addAttribute("filtered", historyRepository.findAllByDateAndInValuteAndOutValuteAndName(date, in_valute, out_valute,buf));
        }
        return "history";
    }
}
