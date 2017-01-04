package com.epam.esm.task4.parser.impl;


import com.epam.esm.task4.parser.XmlParser;
import com.epam.esm.task4.parser.exception.ParsingException;
import com.epam.esm.task4.parser.mapper.NodeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Implementation of {@link XmlParser} using StAX parser
 * Root element of xml file can be specified through constructor,
 * by default name of root element is "content"
 *
 * @param <T> Returning entity type
 */
public class StAXParser<T> implements XmlParser<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StAXParser.class);

    private String rootElementName = "content";
    private XMLStreamReader reader;
    private InputStream inputStream;

    /**
     * @param filePath Path to xml file
     */
    public StAXParser(Path filePath) {
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            inputStream = new FileInputStream(filePath.toFile());
            reader = inputFactory.createXMLStreamReader(inputStream);
        } catch (FileNotFoundException | XMLStreamException ex) {
            LOGGER.error("Exception during stream opening", ex);
        }
    }

    /**
     * @param filePath        Path to xml file
     * @param rootElementName name of root element in xml file
     *                        default 'content'
     */
    public StAXParser(Path filePath, String rootElementName) {
        try {
            this.rootElementName = rootElementName;
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            inputStream = new FileInputStream(filePath.toFile());
            reader = inputFactory.createXMLStreamReader(inputStream);
        } catch (FileNotFoundException | XMLStreamException ex) {
            LOGGER.error("Exception during stream opening", ex);
        }
    }

    @Override
    public void close() {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (reader != null) {
                reader.close();
            }
        } catch (XMLStreamException | IOException ex) {
            LOGGER.error("Can't close parsing", ex);
        }
    }

    @Override
    public T parseNextEntity(NodeMapper<T> nodeMapper) throws ParsingException {
        try {
            while (reader.hasNext() && (!reader.isStartElement() || reader.getLocalName().equals(rootElementName))) {
                reader.next();
            }
            return reader.hasNext() ? nodeMapper.mapNode(reader) : null;
        } catch (XMLStreamException ex) {
            throw new ParsingException("Exception during file processing", ex);
        }
    }

    @Override
    public boolean hasNext() throws ParsingException {
        try {
            return reader != null && reader.hasNext() && hasNextElement();
        } catch (XMLStreamException ex) {
            throw new ParsingException("Exception during method hasNext() execution", ex);
        }
    }

    private boolean hasNextElement() throws XMLStreamException {
        return (reader.isStartElement() || reader.nextTag() != XMLStreamConstants.END_ELEMENT || !reader.getLocalName().equals(rootElementName));
    }
}
