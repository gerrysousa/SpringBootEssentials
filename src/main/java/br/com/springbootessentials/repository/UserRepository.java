package br.com.springbootessentials.repository;

import br.com.springbootessentials.model.DBUser;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserRepository extends PagingAndSortingRepository<DBUser, Long> {
  DBUser findByUsername(String username);
}
