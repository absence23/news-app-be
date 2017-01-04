package com.epam.esm.task4.parser.validator.impl;

import com.epam.esm.task4.parser.validator.XmlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Validate xml files using xsd schema
 */
public class XsdValidator implements XmlValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(XsdValidator.class);

    private final Path schemaPath;

    public XsdValidator(Path schemaPath) {
        this.schemaPath = schemaPath;
    }

    @Override
    public boolean isValid(Path filePath) {
        try (InputStream inputStream = new FileInputStream(filePath.toFile())) {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(schemaPath.toFile());
            javax.xml.validation.Validator validator = schema.newValidator();
            validator.validate(new StreamSource(inputStream));
            return true;
        } catch (SAXException | IOException ex) {
            LOGGER.error("Not valid xml file ", filePath.getFileName(), "\n", ex.getMessage());
            return false;
        }
    }
}