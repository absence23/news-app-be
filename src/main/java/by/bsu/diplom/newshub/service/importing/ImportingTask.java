package by.bsu.diplom.newshub.service.importing;

import com.epam.esm.task4.parser.mapper.NodeMapper;
import com.epam.esm.task4.parser.validator.XmlValidator;
import by.bsu.diplom.newshub.scheduler.ScheduledTask;
import by.bsu.diplom.newshub.service.BatchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.stream.Stream;

@Component
public class ImportingTask<T> implements ScheduledTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(ImportingTask.class);
    private static final String ERROR_FOLDER_NAME = "/error";

    private ExecutorService executorService;
    private XmlValidator xmlValidator;
    private BatchService<T> batchService;
    private NodeMapper<T> nodeMapper;

    public ImportingTask(ExecutorService executorService, XmlValidator xmlValidator, BatchService<T> batchService, NodeMapper<T> nodeMapper) {
        this.executorService = executorService;
        this.xmlValidator = xmlValidator;
        this.batchService = batchService;
        this.nodeMapper = nodeMapper;
    }

    @Override
    public void execute(Path folder) {
        try {
            Path errorFolder = Paths.get(folder + ERROR_FOLDER_NAME);
            if (Files.notExists(errorFolder)) {
                Files.createDirectory(errorFolder);
            }
            try (Stream<Path> paths = Files.walk(folder).filter(path -> !path.startsWith(errorFolder))) {
                paths.forEach(filePath -> {
                    if (Files.isRegularFile(filePath)) {
                        executorService.execute(new Loader<>(errorFolder, filePath, batchService, xmlValidator, nodeMapper));
                    }
                });
            }
        } catch (IOException ex) {
            LOGGER.error("Can't get access to file or folder", ex);
        }
    }
}
