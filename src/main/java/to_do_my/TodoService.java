package to_do_my;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import to_do_my.Todo_Class.Todo_Task;

@Service
public class TodoService {

	@Autowired
	TodoRepo todoRepo;
	@Autowired
	UserRepository userRepo;


	public List<Todo_Class> getTodos()
	{
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Integer userId = userRepo.findByUsername(userDetails.getUsername()).getId();
		return todoRepo.findByUserId(userId).stream().map(t -> {
			return mapToTaskDTO(t);
		}).collect(Collectors.toList());			
	}	

	private Todo_Class mapToTaskDTO(Todo todo) {
		Todo_Class dto = new Todo_Class(todo.getId(), todo.getName());
		for (TodoTask task : todo.getTasks()) {
			dto.getTasks().add(
					new Todo_Task(task.getId(), task.getName(), task
							.isCompleted()));
		}
		return dto;
	}
	
	public Todo returnEntity(Todo_Class dto, String name)
	{
		UserDetails userDetails =
				 (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Todo todo = new Todo();
		todo.setUserId(userRepo.findByUsername(userDetails.getUsername()).getId());
		todo.setName(name);
		return todo;
		
	}

	public Todo_Class getTodoById(int id)
	{
		Optional<Todo> findById = todoRepo.findById(id);
		return findById.isPresent() ? mapToTaskDTO(findById.get()) : null;
	}

	public TodoTask returnEntityById(Todo_Class dto, String name)
	{
		Todo todo = new Todo();
		todo.setName(dto.name);
		todo.setUserId(dto.getUserId());
		todo.setId(dto.getId());
		TodoTask t = new TodoTask();
		t.setCompleted(false);
		t.setName(name);
		todo.getTasks().add(t);
		todo.setTasks(todo.getTasks());
		t.setTodo(todo);
		return t;
		
	}

	

}
