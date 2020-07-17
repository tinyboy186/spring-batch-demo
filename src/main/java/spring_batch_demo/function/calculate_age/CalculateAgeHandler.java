package spring_batch_demo.function.calculate_age;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CalculateAgeHandler {
  @Autowired private JobLauncher jobLauncher;
  @Autowired private Job calculateAgeJob;

  public ResponseEntity<CalculateAgeResponse> handle() {
    try {
      jobLauncher.run(calculateAgeJob, new JobParameters());

      return ResponseEntity.ok(new CalculateAgeResponse("Success"));

    } catch (Exception ex) {
      log.error("handle - Reason:", ex);

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
          .body(new CalculateAgeResponse(ex.getMessage()));
    }
  }
}
