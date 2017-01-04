package by.bsu.diplom.newshub.service;

import by.bsu.diplom.newshub.domain.dto.NewsDto;
import com.epam.esm.task4.parser.validator.XmlValidator;
import com.epam.esm.task4.parser.validator.impl.XsdValidator;
import by.bsu.diplom.newshub.scheduler.DirectoryLookupScheduler;
import by.bsu.diplom.newshub.service.importing.ImportingTask;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class ApplicationContext {
    @Bean(destroyMethod = "shutdown")
    public ExecutorService executorService(@Value("${thread.pool.size}") int threadPoolSize) {
        return Executors.newFixedThreadPool(threadPoolSize);
    }

    @Bean
    public XmlValidator xmlValidator() {
        return new XsdValidator(Paths.get(getClass().getResource("/schema.xsd").getPath().replaceFirst("/", "")));
    }

    @Bean
    public JobDetailFactoryBean jobDetail(@Value("${content.directory}") String directoryPath,
                                          ImportingTask<NewsDto> importingTask) {
        JobDetailFactoryBean jobDetailFactoryBean = new JobDetailFactoryBean();
        jobDetailFactoryBean.setJobClass(DirectoryLookupScheduler.class);
        jobDetailFactoryBean.setDurability(true);
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("directoryPath", Paths.get(directoryPath));
        jobDataMap.put("task", importingTask);
        jobDetailFactoryBean.setJobDataAsMap(jobDataMap);
        return jobDetailFactoryBean;
    }

    @Bean
    public SimpleTriggerFactoryBean trigger(@Value("${timeout}") int timeout,
                                            @Value("${start.delay}") int startDelay,
                                            JobDetailFactoryBean jobDetail) {
        SimpleTriggerFactoryBean trigger = new SimpleTriggerFactoryBean();
        trigger.setJobDetail(jobDetail.getObject());
        trigger.setRepeatInterval(timeout);
        trigger.setStartDelay(startDelay);
        return trigger;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobDetailFactoryBean jobDetail, SimpleTriggerFactoryBean trigger) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setJobDetails(new JobDetail[]{jobDetail.getObject()});
        schedulerFactoryBean.setTriggers(new Trigger[]{trigger.getObject()});
        return schedulerFactoryBean;
    }
}