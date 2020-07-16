package spring_batch_demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "batch")
@EnableBatchProcessing
public class BatchConfig {
  private int chunkSize;
}
