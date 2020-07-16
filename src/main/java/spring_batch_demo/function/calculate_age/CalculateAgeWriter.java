package spring_batch_demo.function.calculate_age;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.GsonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Component;
import spring_batch_demo.entity.AgedPerson;
import spring_batch_demo.util.GsonUtils;

import java.util.List;

@Slf4j
@Component
public class CalculateAgeWriter implements ItemWriter<AgedPerson>, StepExecutionListener {
  private JsonFileItemWriter<AgedPerson> writer;

  @Override
  public void write(List<? extends AgedPerson> agedPersons) {
    try {
      writer.write(agedPersons);

      log.info("write - agedPersons: {}", GsonUtils.toJson(agedPersons));

    } catch (Exception ex) {
      log.error("write - agedPersons: {} - Reason:", GsonUtils.toJson(agedPersons), ex);
    }
  }

  @Override
  public void beforeStep(StepExecution stepExecution) {
    log.info("beforeStep");

    writer =
        new JsonFileItemWriter<>(
            new FileSystemResource("src/main/resources/output.json"),
            new GsonJsonObjectMarshaller<>());

    writer.setForceSync(true);
    writer.open(stepExecution.getExecutionContext());
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    log.info("afterStep");

    writer.close();

    return ExitStatus.COMPLETED;
  }
}
