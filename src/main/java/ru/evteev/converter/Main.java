package ru.evteev.converter;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        final String URL = "http://www.cbr.ru/scripts/XML_daily.asp";

        try {
            List<ExchangeRate> exchangeRatesDOM = XMLParserDOM.parse(URL);
            exchangeRatesDOM.forEach(System.out::println);
            System.out.println(exchangeRatesDOM.size());

            List<ExchangeRate> exchangeRatesSAX = XMLParserSAX.parse(URL);
            exchangeRatesSAX.forEach(System.out::println);
            System.out.println(exchangeRatesSAX.size());
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }
}
