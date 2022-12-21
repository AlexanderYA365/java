package Repository;

import com.getjavajob.training.yakovleva.common.Group;
import com.getjavajob.training.yakovleva.common.GroupMembers;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMembersRepository extends CrudRepository<GroupMembers, Integer> {

    List<GroupMembers> getGroupByMember_Id(int member);

    List<GroupMembers> getMembersByGroup(Group group);

}
