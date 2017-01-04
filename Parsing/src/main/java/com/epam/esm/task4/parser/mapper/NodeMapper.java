package com.epam.esm.task4.parser.mapper;

import com.epam.esm.task4.parser.exception.ParsingException;

import javax.xml.stream.XMLStreamReader;

/**
 * Used for binding tags from xml file to entity attributes
 *
 * @param <T> Class of entity to which we map attributes
 */
@FunctionalInterface
public interface NodeMapper<T> {

    /**
     * Bind tags from current position of reader to entity
     *
     * @param reader Stream reader to get tags for binding
     * @return Complete entity
     * @throws ParsingException Throws when XmlStreamException is thrown
     */
    T mapNode(XMLStreamReader reader) throws ParsingException;
}
