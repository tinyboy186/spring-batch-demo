package spring_batch_demo.function.calculate_age_and_count;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CalculateAgeAndCountResponse {
  private String message;
  private int count;

  CalculateAgeAndCountResponse(int count) {
    this.message = "Success";
    this.count = count;
  }

  CalculateAgeAndCountResponse(String message) {
    this.message = message;
  }
}
