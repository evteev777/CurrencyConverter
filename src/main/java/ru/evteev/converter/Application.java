package ru.evteev.converter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.xml.sax.SAXException;
import ru.evteev.converter.entities.ExchangeRate;
import ru.evteev.converter.models.XMLParser;
import ru.evteev.converter.models.XMLParserDOM;
import ru.evteev.converter.models.XMLParserSAX;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        try {
            final String URL = "http://www.cbr.ru/scripts/XML_daily.asp";

            XMLParser parserDOM = new XMLParserDOM();
            parserDOM.parse(URL);
//            List<ExchangeRate> exchangeRatesDOM = parserDOM.parse(URL);
//            exchangeRatesDOM.forEach(System.out::println);
//            System.out.println(exchangeRatesDOM.size());
//
//            XMLParser parserSAX = new XMLParserSAX();
//            List<ExchangeRate> exchangeRatesSAX = parserSAX.parse(URL);
//            exchangeRatesSAX.forEach(System.out::println);
//            System.out.println(exchangeRatesSAX.size());
//        } catch (ParserConfigurationException | SAXException | IOException e) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
