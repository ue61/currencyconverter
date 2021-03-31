package ru.bober.currencyconverter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bober.currencyconverter.entity.History;

import java.sql.Date;

@Repository
public interface HistoryRepository extends JpaRepository<History, Integer> {
    Iterable<History> findAllByName(String name);
    Iterable<History> findAllByInValuteAndOutValuteAndName(String in_valute, String out_valute,String name);
    Iterable<History> findAllByInValuteAndName(String in_valute,String name);
    Iterable<History> findAllByOutValuteAndName(String out_valute,String name);
    Iterable<History> findAllByDateAndName(Date date,String name);
    Iterable<History> findAllByDateAndInValuteAndName(Date date, String in_valute,String name);
    Iterable<History> findAllByDateAndOutValuteAndName(Date date, String out_valute,String name);
    Iterable<History> findAllByDateAndInValuteAndOutValuteAndName(Date date,String in_valute, String out_valute,String name);
}
