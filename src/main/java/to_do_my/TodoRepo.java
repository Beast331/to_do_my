package to_do_my;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TodoRepo extends JpaRepository<Todo, Integer>,
		JpaSpecificationExecutor<Todo> {
	List<Todo> findByUserId(int userId);
}
