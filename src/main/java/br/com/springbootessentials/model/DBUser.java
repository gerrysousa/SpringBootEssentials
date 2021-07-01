package br.com.springbootessentials.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

@Entity
public class DBUser extends AbstractEntity{
  @NotEmpty
  @Column(unique = true)
  private String username;
  @NotEmpty
  private String password;
  @NotEmpty
  private String name;
  @NotEmpty
  private boolean admin;

  public DBUser (@NotEmpty String username, @NotEmpty String password, @NotEmpty String name, @NotEmpty boolean admin) {
    this.username = username;
    this.password = password;
    this.name = name;
    this.admin = admin;
  }

  public DBUser () {
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isAdmin() {
    return admin;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }
}
