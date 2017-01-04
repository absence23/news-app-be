package by.bsu.diplom.newshub.scheduler;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * DirectoryLookupScheduler job that lookups directory
 * and execute task if directory was changed
 */
@DisallowConcurrentExecution
public class DirectoryLookupScheduler extends QuartzJobBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(DirectoryLookupScheduler.class);

    private Path directoryPath;
    private ScheduledTask task;
    private LocalDateTime lastModified;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            LocalDateTime newLastModified = LocalDateTime.ofInstant(Files.getLastModifiedTime(directoryPath).toInstant(), ZoneId.systemDefault());
            if (lastModified == null || lastModified.isBefore(newLastModified)) {
                lastModified = newLastModified;
                task.execute(directoryPath);
            }
        } catch (IOException ex) {
            LOGGER.error("Can't get access to folder", ex);
        }
    }

    public void setDirectoryPath(Path directoryPath) {
        this.directoryPath = directoryPath;
    }

    public void setTask(ScheduledTask task) {
        this.task = task;
    }
}