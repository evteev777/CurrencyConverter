package ru.evteev.converter.parser;

import java.io.IOException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import ru.evteev.converter.entity.ExchangeRate;

public interface XMLParser {

    List<ExchangeRate> parse(String url)
        throws ParserConfigurationException, SAXException, IOException;
}
