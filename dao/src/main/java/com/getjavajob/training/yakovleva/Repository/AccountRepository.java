package com.getjavajob.training.yakovleva.Repository;

import com.getjavajob.training.yakovleva.common.Group;
import org.springframework.data.repository.CrudRepository;

//@Repository
public interface AccountRepository extends CrudRepository<Group, Integer> {
//    public Account getByUsername(String username);

}
