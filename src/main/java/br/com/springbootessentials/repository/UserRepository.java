package br.com.springbootessentials.repository;

import br.com.springbootessentials.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
  User findByUsername(String username);
}
