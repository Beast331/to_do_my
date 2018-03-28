package to_do_my;


import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TodoApiController {

	@Autowired
	TodoService todoService;

	@GetMapping("/users/{userId}/todos")
	public List<Todo_Class> getTodosForUser(
			@PathParam(value = "userId") Integer userId) {
		// TODO: make it user specific
		return todoService.getTodos();
	}

	@GetMapping("/users/{userId}/todos/{todoId}")
	public Todo_Class getTodoByIdForUser(@PathVariable(value = "userId") Integer userId,
			@PathVariable(value = "todoId") Integer todoId) {
		// TODO: make it user specific
		return todoService.getTodoById(todoId);
	}

	
}
