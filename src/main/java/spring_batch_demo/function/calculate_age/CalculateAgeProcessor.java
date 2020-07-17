package spring_batch_demo.function.calculate_age;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import spring_batch_demo.entity.AgedPerson;
import spring_batch_demo.entity.Person;
import spring_batch_demo.util.GsonUtils;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Slf4j
@Component
public class CalculateAgeProcessor
    implements ItemProcessor<Person, AgedPerson>, StepExecutionListener {
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("MM/dd/yyyy");

  @Override
  public AgedPerson process(Person person) {
    try {
      LocalDate dob = LocalDate.parse(person.getDob(), DATE_TIME_FORMATTER);
      LocalDate now = LocalDate.now();

      AgedPerson agedPerson = new AgedPerson(person.getName(), Period.between(dob, now).getYears());

      log.info(
          "process - person: {} - agedPerson: {}",
          GsonUtils.toJson(person),
          GsonUtils.toJson(agedPerson));

      return agedPerson;

    } catch (Exception ex) {
      log.error("process - person: {} - Reason:", GsonUtils.toJson(person), ex);

      return null;
    }
  }

  @Override
  public void beforeStep(StepExecution stepExecution) {
    log.info("beforeStep");
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    log.info("afterStep");

    return ExitStatus.COMPLETED;
  }
}
