package spring_batch_demo.function;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class JobHolder {
  @Autowired private JobBuilderFactory jobBuilderFactory;
  @Autowired private Step calculateAgeStep;
  @Autowired private Step countStep;

  @Bean
  public Job calculateAgeJob() {
    return jobBuilderFactory.get("calculateAgeJob").start(calculateAgeStep).build();
  }

  @Bean
  public Job countJob() {
    return jobBuilderFactory.get("countJob").start(countStep).build();
  }

  @Bean
  public Job calculateAgeAndCountJob() {
    return jobBuilderFactory
        .get("calculateAgeAndCountJob")
        .start(calculateAgeStep)
        .next(countStep)
        .build();
  }
}
