package by.bsu.diplom.newshub.service.importing;

import by.bsu.diplom.newshub.exception.BatchServiceException;
import by.bsu.diplom.newshub.exception.ValidationException;
import by.bsu.diplom.newshub.exception.XsdValidationException;
import com.epam.esm.task4.parser.exception.ParsingException;
import com.epam.esm.task4.parser.impl.StAXParser;
import com.epam.esm.task4.parser.mapper.NodeMapper;
import com.epam.esm.task4.parser.validator.XmlValidator;
import by.bsu.diplom.newshub.service.BatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Class for processing one of the files with content updates in
 * separate thread. Validate filePath with xsd and saving all in db
 * If filePath contains errors or error was occured while filePath processing
 * or news insert filePath will be moved to error folder
 */
public class Loader<T> implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Loader.class);

    private final Path errorFolderPath;
    private final Path filePath;
    private final BatchService<T> batchService;
    private final XmlValidator xmlValidator;
    private final NodeMapper<T> nodeMapper;

    /**
     * @param errorFolderPath Folder for files with errors
     * @param filePath        File that need to be processed
     * @param batchService    Service for inserting objects in db
     * @param xmlValidator    Object for file validator before parsing
     */
    public Loader(Path errorFolderPath, Path filePath, BatchService<T> batchService, XmlValidator xmlValidator, NodeMapper<T> nodeMapper) {
        this.errorFolderPath = errorFolderPath;
        this.filePath = filePath;
        this.batchService = batchService;
        this.xmlValidator = xmlValidator;
        this.nodeMapper = nodeMapper;
    }

    @Override
    public void run() {
        try {
            parseFile();
            Files.delete(filePath);
        } catch (ParsingException | ValidationException | BatchServiceException | XsdValidationException ex) {
            moveFile();
        } catch (IOException ex) {
            LOGGER.error("Can't get access to file", ex);
        }
    }

    private void moveFile() {
        try {
            Path target = Paths.get(errorFolderPath.toString(), "\\",
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy_hh.mm.ss_")) + filePath.getFileName());
            Files.move(filePath, target);
        } catch (IOException ex) {
            LOGGER.error("Can't get access to file", ex);
        }
    }

    private void parseFile() {
        try (StAXParser<T> parser = new StAXParser<>(filePath)) {
            if (xmlValidator.isValid(filePath)) {
                List<T> news = new ArrayList<>();
                while (parser.hasNext()) {
                    news.add(parser.parseNextEntity(nodeMapper));
                }
                batchService.saveBatch(news);
            } else {
                throw new XsdValidationException("File isn't valid");
            }
        }
    }
}