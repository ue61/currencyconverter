package ru.bober.currencyconverter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.bober.currencyconverter.entity.ValutesCurrency;
import ru.bober.currencyconverter.repository.CurrencyRepository;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CurrencyService {
    @Autowired
    CurrencyRepository currencyRepository;

    private String mementoValuteNameOne;
    private String mementoValuteNameTwo;
    private String mementoNum;

    public void setMementoValuteNameOne(String memento){
        this.mementoValuteNameOne = memento;
    }
    public String getMementoValuteNameOne(){
        return mementoValuteNameOne;
    }

    public void setMementoValuteNameTwo(String memento){
        this.mementoValuteNameTwo= memento;
    }
    public String getMementoValuteNameTwo(){
        return mementoValuteNameTwo;
    }
    public void setMementoNum(String mementoNum){
        this.mementoNum = mementoNum;
    }
    public String getMementoNum(){
        return mementoNum;
    }
    public void getValutes()
    {
        try{
            Date date1 = new Date();
            if(checkRelevance(date1)){
                currencyRepository.deleteAll();
                String pre_apiURL = "http://www.cbr.ru/scripts/XML_daily.asp";
                URL url = new URL(pre_apiURL);
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(url.openStream());
                NodeList nodeValutes = doc.getElementsByTagName("Valute");
                NodeList nodeDate = doc.getElementsByTagName("ValCurs");
                String date = nodeDate.item(0).getAttributes().getNamedItem("Date").getNodeValue();
                for (int i = 0; i < nodeValutes.getLength(); i++) {
                    Node nameValute = nodeValutes.item(i);
                    Element element = (Element) nameValute;
                    ValutesCurrency valutesCurrency = new ValutesCurrency();
                    valutesCurrency.setValute_id(Integer.parseInt(getTagValue("NumCode", element)));
                    valutesCurrency.setChar_code(getTagValue("CharCode", element));
                    valutesCurrency.setValutename(getTagValue("Name", element));
                    valutesCurrency.setValue(getTagValueInt("Value", element));
                    valutesCurrency.setDate(date);
                    currencyRepository.save(valutesCurrency);
                }
                ValutesCurrency valutesCurrency = new ValutesCurrency();
                valutesCurrency.setValute_id(643);
                valutesCurrency.setChar_code("RUB");
                valutesCurrency.setValutename("Российский Рубль");
                valutesCurrency.setValue(10000);
                valutesCurrency.setDate(date);
                currencyRepository.save(valutesCurrency);
            }
        }catch(Exception e){}
    }

    public String getTagValue(String tag, Element element)
    {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }
    public int getTagValueInt(String tag, Element element)
    {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        int i = Integer.parseInt(node.getNodeValue().replaceAll(",", ""));
        return i;
    }
    public boolean checkRelevance(Date date){
        Iterable<ValutesCurrency> currencies = currencyRepository.findAll();
        Date sqlDate = new Date();
        for (ValutesCurrency valutesCurrency :currencies) {
            sqlDate = valutesCurrency.getDate();
            break;
        }
        if (sqlDate.getDate() == date.getDate() && sqlDate.getMonth() == date.getMonth() && sqlDate.getYear() == date.getYear()) {
            return true;
        }
        return false;
    }
    public float toConvert(String vn1, String vn2, float num){
        num = currencyRepository.findByValutename(vn1).getValue() * num / currencyRepository.findByValutename(vn2).getValue();
        return num;
    }
    public Iterable<ValutesCurrency> getValutesNames(){
        return currencyRepository.findAll();
    }
}
