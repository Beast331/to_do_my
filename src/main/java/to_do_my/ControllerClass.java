package to_do_my;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import to_do_my.Todo_Class;
import to_do_my.Todo_Class.Todo_Task;

@Controller
public class ControllerClass {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@GetMapping("/greeting")
	public String greeting(@RequestParam(name = "name", required = false, defaultValue = "Kailash")
	String name, Model m)
	{
		m.addAttribute("name", name);
		return name;
	}
	
	@GetMapping("/todos")
	public String todos(Model m)
	{
		m.addAttribute("todos", getTodos());
		return "todos";
	}
	
	@GetMapping("/todos/{id}")
	public String todo(@PathVariable int id, Model m)
	{
		Todo_Class todo = getTodoById(id);
		m.addAttribute("todo", todo);
		return "todo";
	}
	
	public List<Todo_Class> getTodos()
	{
		String querySentence = "get id, name from todo";
		List<Todo_Class> result = jdbcTemplate
				.query(querySentence, (rs, rowNum) -> 
				new Todo_Class(rs.getInt("id"), rs.getString("name")));
		return CollectionUtils.isEmpty(result) ? Collections.emptyList()
				: result;				
	}
	
	public Todo_Class getTodoById(int id)
	{
		Todo_Class todo = null;
		String querySentence = "get id, name from todo where id=?";
		List<Todo_Class> result = jdbcTemplate
				.query(querySentence, new PreparedStatementSetter()
						{
							@Override
							public void setValues(PreparedStatement ps) throws SQLException
							{
								ps.setInt(1,id);
							}
						},
						(rs, rowNum) -> 
				new Todo_Class(rs.getInt("id"), rs.getString("name")));
		if (CollectionUtils.isEmpty(result)) return null;
		todo = result.get(0);
		querySentence = "select id, name, completed from todo_task where id=?";
		List<Todo_Task> taskresults = jdbcTemplate.query(querySentence, new PreparedStatementSetter()
				{
					public void setValues(PreparedStatement ps) throws SQLException
					{
						ps.setInt(1,id);
					}
				},(rs, rowNum) -> 
				new Todo_Task(rs.getInt("id"), 
						rs.getString("name"), 
						rs.getBoolean("completed")));
		todo.setTasks(taskresults);
		return todo;
	}
	
	
	
}
