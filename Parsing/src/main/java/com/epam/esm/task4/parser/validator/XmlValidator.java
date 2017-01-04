package com.epam.esm.task4.parser.validator;

import java.nio.file.Path;

/**
 * Check xml files for errors
 */
public interface XmlValidator {

    /**
     * Check file for errors
     *
     * @param filePath Path to file to validate
     * @return true - if file is valid, false - if invalid
     */
    boolean isValid(Path filePath);
}
