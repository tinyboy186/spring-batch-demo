package spring_batch_demo.function.calculate_age;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import spring_batch_demo.entity.Person;

@Slf4j
@Component
public class CalculateAgeReader implements ItemReader<Person>, StepExecutionListener {
  private FlatFileItemReader<Person> reader;

  @Override
  public Person read() {
    try {
      return reader.read();

    } catch (Exception ex) {
      log.error("read - Reason:", ex);

      return null;
    }
  }

  @Override
  public void beforeStep(StepExecution stepExecution) {
    log.info("beforeStep");

    DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
    lineTokenizer.setNames("name", "dob");

    BeanWrapperFieldSetMapper<Person> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
    fieldSetMapper.setTargetType(Person.class);

    DefaultLineMapper<Person> lineMapper = new DefaultLineMapper<>();
    lineMapper.setLineTokenizer(lineTokenizer);
    lineMapper.setFieldSetMapper(fieldSetMapper);

    reader = new FlatFileItemReader<>();
    reader.setResource(new ClassPathResource("input.csv"));
    reader.setLineMapper(lineMapper);
    reader.setLinesToSkip(1);
    reader.open(stepExecution.getExecutionContext());
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    log.info("afterStep");

    reader.close();

    return ExitStatus.COMPLETED;
  }
}
