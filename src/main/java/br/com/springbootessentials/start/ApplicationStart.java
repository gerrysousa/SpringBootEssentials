package br.com.springbootessentials.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan(basePackages = "br.com.springbootessentials.endpoint")
public class ApplicationStart {
  public static  void  main(String[] args) {
    SpringApplication.run(ApplicationStart.class, args);
  }
}
