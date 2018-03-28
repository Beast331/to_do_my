package to_do_my;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserRepository extends JpaRepository<User, Integer>,
		JpaSpecificationExecutor<User> {

	User findByUsername(String username);
}
