package spring_batch_demo.function.count;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountResponse {
  private String message;
  private int count;

  CountResponse(int count) {
    this.message = "Success";
    this.count = count;
  }

  CountResponse(String message) {
    this.message = message;
  }
}
