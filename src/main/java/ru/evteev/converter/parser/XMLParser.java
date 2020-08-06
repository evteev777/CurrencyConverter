package ru.evteev.converter.parser;

import org.xml.sax.SAXException;
import ru.evteev.converter.entity.ExchangeRate;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.List;

public interface XMLParser {

    List<ExchangeRate> parse(String url)
            throws ParserConfigurationException, SAXException, IOException;
}
