package to_do_my;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TodoTaskRepo extends 
JpaRepository<TodoTask, Integer>, 
JpaSpecificationExecutor<TodoTask>
{
	TodoTask findByTodo(Todo t);
	void deleteByTodo_id(int id);
}
