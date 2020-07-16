package spring_batch_demo.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgedPerson {
  private String name;
  private int age;

  public AgedPerson(String name, int age) {
    this.name = name;
    this.age = age;
  }
}
