package spring_batch_demo.function;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import spring_batch_demo.config.BatchConfig;
import spring_batch_demo.entity.AgedPerson;
import spring_batch_demo.entity.Person;
import spring_batch_demo.function.calculate_age.CalculateAgeProcessor;
import spring_batch_demo.function.calculate_age.CalculateAgeReader;
import spring_batch_demo.function.calculate_age.CalculateAgeWriter;

@Component
public class StepHolder {
  @Autowired private BatchConfig batchConfig;
  @Autowired private StepBuilderFactory stepBuilderFactory;

  @Autowired private CalculateAgeReader calculateAgeReader;
  @Autowired private CalculateAgeProcessor calculateAgeProcessor;
  @Autowired private CalculateAgeWriter calculateAgeWriter;

  @Bean
  public Step calculateAgeStep() {
    return stepBuilderFactory
        .get("calculateAgeStep")
        .<Person, AgedPerson>chunk(batchConfig.getChunkSize())
        .reader(calculateAgeReader)
        .processor(calculateAgeProcessor)
        .writer(calculateAgeWriter)
        .allowStartIfComplete(true)
        .build();
  }
}
