package spring_batch_demo.function.calculate_age;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculateAgeResponse {
  private String message;

  CalculateAgeResponse(String message) {
    this.message = message;
  }
}
