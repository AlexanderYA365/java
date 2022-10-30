package Repository;

import com.getjavajob.training.yakovleva.common.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends CrudRepository<Group, Integer> {
}
