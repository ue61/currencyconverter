package ru.bober.currencyconverter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.bober.currencyconverter.entity.History;
import ru.bober.currencyconverter.repository.CurrencyRepository;
import ru.bober.currencyconverter.repository.HistoryRepository;

import java.sql.Date;


@Service
public class HistoryService {
    @Autowired
    HistoryRepository historyRepository;
    @Autowired
    CurrencyRepository currencyRepository;

    public void addRecord(String name,String vn1, String vn2){
        History history = new History();
        Date date = new Date(new java.util.Date().getTime());
        history.setInValue(currencyRepository.findByValutename(vn1).getValue());
        history.setOutValue(currencyRepository.findByValutename(vn2).getValue());
        history.setDate(date);
        history.setName(name);
        history.setInValute(currencyRepository.findByValutename(vn1).getChar_code() + " (" + vn1 + ")");
        history.setOutValute(currencyRepository.findByValutename(vn2).getChar_code() + " (" + vn2 + ")");
        history.setCurrency((float) currencyRepository.findByValutename(vn1).getValue() / (float)currencyRepository.findByValutename(vn2).getValue());
        historyRepository.save(history);
    }
}
