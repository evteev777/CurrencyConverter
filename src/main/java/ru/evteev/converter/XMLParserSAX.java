package ru.evteev.converter;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XMLParserSAX {

    private static final List<Currency> currenciesSAX = new ArrayList<>();
    private static final List<ExchangeRate> exchangeRatesSAX = new ArrayList<>();

    private XMLParserSAX() {
    }

    public static List<ExchangeRate> parse(String url)
            throws ParserConfigurationException, SAXException, IOException {

        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        XMLHandler handler = new XMLHandler();
        parser.parse(url, handler);

        return exchangeRatesSAX;
    }

    private static class XMLHandler extends DefaultHandler {

        private String id;
        private String numCode;
        private String charCode;
        private Integer nominal;
        private String name;
        private Double value;
        private String lastElementName;

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            lastElementName = qName;
            if (qName.equals("Valute")) {
                id = attributes.getValue("ID");
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) {
            String information = new String(ch, start, length);
            information = information.replace("\n", "").trim();

            switch (lastElementName) {
                case "NumCode" -> numCode = information;
                case "CharCode" -> charCode = information;
                case "Nominal" -> nominal = Integer.parseInt(information);
                case "Name" -> name = information;
                case "Value" -> value = Double.parseDouble(information
                        .replace(",", "."));
                default -> throw new IllegalArgumentException(
                        "Illegal element: " + lastElementName);
            }
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if (id != null && !id.isEmpty() &&
                    numCode != null && !numCode.isEmpty() &&
                    charCode != null && !charCode.isEmpty() &&
                    nominal != null &&
                    name != null && !name.isEmpty() &&
                    value != null
            ) {
                Currency currency = new Currency(id, numCode, charCode, nominal, name);
                currenciesSAX.add(currency);
                exchangeRatesSAX.add(new ExchangeRate(currency, value));

                id = null;
                numCode = null;
                charCode = null;
                nominal = null;
                name = null;
                value = null;
            }
        }
    }
}
