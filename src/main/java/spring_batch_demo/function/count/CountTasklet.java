package spring_batch_demo.function.count;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.json.GsonJsonObjectReader;
import org.springframework.batch.item.json.JsonItemReader;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import spring_batch_demo.entity.AgedPerson;

@Slf4j
@Getter
@Component
public class CountTasklet implements Tasklet, StepExecutionListener {
  private JsonItemReader<AgedPerson> reader;
  private int count;

  @Override
  public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) {
    try {
      AgedPerson agedPerson = reader.read();

      if (agedPerson == null) {
        return RepeatStatus.FINISHED;
      }

      long requiredAge =
          (long) chunkContext.getStepContext().getJobParameters().getOrDefault("age", 0);

      if (agedPerson.getAge() >= requiredAge) {
        count += 1;
      }

      return RepeatStatus.CONTINUABLE;

    } catch (Exception ex) {
      log.error("execute - Reason:", ex);

      return RepeatStatus.CONTINUABLE;
    }
  }

  @Override
  public void beforeStep(StepExecution stepExecution) {
    log.info("beforeStep");

    count = 0;

    reader =
        new JsonItemReader<>(
            new ClassPathResource("output.json"), new GsonJsonObjectReader<>(AgedPerson.class));

    reader.open(stepExecution.getExecutionContext());
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    log.info("afterStep");

    reader.close();

    return ExitStatus.COMPLETED;
  }
}
