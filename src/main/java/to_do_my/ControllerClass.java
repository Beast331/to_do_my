package to_do_my;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import to_do_my.Todo_Class.Todo_Task;

@Controller
public class ControllerClass {
	
	@Autowired
	TodoRepo todoRepo;
	
	@Autowired
	TodoTaskRepo ttRepo;
	
	@Autowired
	UserRepository userRepo;
	
	@GetMapping("/")
	public String home(Model m) {
		m.addAttribute("user", new UserDTO());
		return "redirect:/login";
	}
	

	@GetMapping("/greeting")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "Kailash")
	String name, @RequestParam(name = "age", required = false, defaultValue = "16") String age, Model m)
	{
		m.addAttribute("name", name);
		m.addAttribute("age", age);
		return "greeting";
	}
	
	@GetMapping("/todos")
	public String todos(Model m)
	{
		m.addAttribute("todos", getTodos());
		m.addAttribute("taskDTOObject", new Todo_Class());
		return "todos";
	}
	
	private List<Todo_Class> getTodos()
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
	
	@PostMapping("/createtodos")
	public String createtodos( Todo_Class dto) 
	{
		todoRepo.save(returnEntity(dto, dto.name));
		return "redirect:/todos";
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
	
	
	@GetMapping("/todos/{id}")
	public String todo(@PathVariable int id, Model m)
	{
		Todo_Class todo = getTodoById(id);
		m.addAttribute("dto", new Todo_Task());
		m.addAttribute("todo", todo);
		return "todo";
	}
	
	private Todo_Class getTodoById(int id)
	{
		Optional<Todo> findById = todoRepo.findById(id);
		return findById.isPresent() ? mapToTaskDTO(findById.get()) : null;
	}
	
	@PostMapping("/createtodotask/{id}")
	public String createTodos(@PathVariable int id, @ModelAttribute Todo_Task dto, Model m)
	{
		
		m.addAttribute(id);
		ttRepo.save(returnEntityById(getTodoById(id), dto.getName()));
		return "redirect:/todos/" + Integer.toString(id);
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
	
	@ModelAttribute("userId")
	public Integer getUserId(Authentication authentication) {
		CustomUserDetails customUser;
		customUser = (CustomUserDetails) authentication
				.getPrincipal();
		System.out.println("current logged in user id: "
				+ customUser.getUserId());
		
		return customUser.getUserId();
	}
	
}
