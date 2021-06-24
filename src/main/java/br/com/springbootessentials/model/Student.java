package br.com.springbootessentials.model;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Entity
public class Student extends AbstractEntity{
  @NotEmpty(message = "Field 'name' is mandatory!")
  private  String name;
  @NotEmpty(message = "Field 'email' is mandatory!")
  @Email(message = "Email must be valid!")
  private String email;

  public Student(String name, @NotEmpty @Email String email) {
    this.name = name;
    this.email = email;
  }

  public Student() {
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

}
