package by.bsu.diplom.newshub.scheduler;

import java.nio.file.Path;

/**
 * Task for execution in {@link DirectoryLookupScheduler}
 */
@FunctionalInterface
public interface ScheduledTask {

    /**
     * Execute task
     *
     * @param directory Directory for which task will be executed
     */
    void execute(Path directory);
}