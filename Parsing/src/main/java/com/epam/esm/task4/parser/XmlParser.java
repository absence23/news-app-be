package com.epam.esm.task4.parser;

import com.epam.esm.task4.parser.exception.ParsingException;
import com.epam.esm.task4.parser.mapper.NodeMapper;

/**
 * Parser interface that extract news from file one by one.
 * Processing one file while method start isn't called
 */
public interface XmlParser<T> extends AutoCloseable {

    /**
     * Parse next news record
     *
     * @param nodeMapper Mapper for mapping next node
     * @return Object retrieved from xml file
     * @throws ParsingException Throws when file can't be correctly parsed
     */
    T parseNextEntity(NodeMapper<T> nodeMapper) throws ParsingException;

    /**
     * Check is file contains more elements or all content is already parsed
     *
     * @return true - if there are more elements in file, false - if all elements was parsed
     * @throws ParsingException Throws if exception occured during checking
     */
    boolean hasNext() throws ParsingException;
}