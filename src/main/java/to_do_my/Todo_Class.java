package to_do_my;

import java.util.ArrayList;
import java.util.List;

public class Todo_Class {
	public Integer id;
	public String name;
	private List<Todo_Task> tasks = new ArrayList<Todo_Class.Todo_Task>();
	public Integer userId;
	public Todo_Class()
	{
		
	}
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Integer getUserId() {
		return userId;
	}


	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Todo_Class(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
	
	public List<Todo_Task> getTasks()
	{
		return tasks;
	}
	
	public void setTasks(List<Todo_Task> tasks)
	{
		this.tasks = tasks;
	}
	
	public static class Todo_Task
	{
		public Integer id;
		public String name;
		private boolean completed = false;
		
		public Todo_Task()
		{
			
		}
		
		public Todo_Task(int id, String name, boolean completed)
		{
			this.id = id;
			this.name = name;
			this.completed = completed;
		}
		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isCompleted() {
			return completed;
		}

		public void setCompleted(boolean completed) {
			this.completed = completed;
		}

	}
}
