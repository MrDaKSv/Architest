package chnu._3.myproject.repository;/*
  @author Bogdan
  @project myproject
  @class DuckRepository
  @version 1.0.0
  @since 19.04.2025 - 19.54
*/

import chnu._3.myproject.model.Duck;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DuckRepository extends MongoRepository<Duck, String> {
}
