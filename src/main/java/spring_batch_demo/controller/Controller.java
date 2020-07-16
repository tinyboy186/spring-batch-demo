package spring_batch_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring_batch_demo.function.calculate_age.CalculateAgeHandler;
import spring_batch_demo.function.calculate_age.CalculateAgeResponse;
import spring_batch_demo.function.count.CountHandler;
import spring_batch_demo.function.count.CountResponse;

@RestController
public class Controller {
  @Autowired private CalculateAgeHandler calculateAgeHandler;
  @Autowired private CountHandler countHandler;

  @GetMapping("/calculate-age")
  public ResponseEntity<CalculateAgeResponse> calculateAge() {
    return calculateAgeHandler.handle();
  }

  @GetMapping("/count/{age}")
  public ResponseEntity<CountResponse> count(@PathVariable long age) {
    return countHandler.handle(age);
  }
}
