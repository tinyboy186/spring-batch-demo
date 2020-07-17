package spring_batch_demo.function.count;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CountHandler {
  @Autowired private JobLauncher jobLauncher;
  @Autowired private Job countJob;
  @Autowired private CountTasklet countTasklet;

  public ResponseEntity<CountResponse> handle(long age) {
    try {
      JobParameters jobParameters =
          new JobParametersBuilder().addLong("age", age).toJobParameters();

      jobLauncher.run(countJob, jobParameters);

      return ResponseEntity.ok(new CountResponse(countTasklet.getCount()));

    } catch (Exception ex) {
      log.error("handle - Reason:", ex);

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new CountResponse(ex.getMessage()));
    }
  }
}
