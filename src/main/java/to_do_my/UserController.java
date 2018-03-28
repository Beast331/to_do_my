package to_do_my;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserController {
	
	@Autowired
	TodoRepo todoRepo;
	
	@Autowired
	TodoTaskRepo ttRepo;
	
	@Autowired
	UserRepository userRepo;
	


	
	@PostMapping ("/createuser")
	public String createuser(@Valid UserDTO user)
	{
		for (int i = 0; i < returnAllUsers().size(); i++)
		{
			if (user.getUsername().equalsIgnoreCase(returnAllUsers().get(i).getUsername()))
			{
				return "redirect:/login";
			}
		}
		userRepo.save(returnUser(user));
		return "redirect:/login";
	}
	
	private List<UserDTO> returnAllUsers()
	{
		System.out.println("Seacrhing for user...");
		return userRepo.findAll().stream().map(t -> {
			return mapToUserDTO(t);
		}).collect(Collectors.toList());			
	}
	
	private UserDTO mapToUserDTO(User user) {
		UserDTO dto = new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getName());
		return dto;
	}
	
	private User returnUser(UserDTO user)
	{
		System.out.println("User being created...");
		User userEntity = new User();
		userEntity.setName(user.getName());
		userEntity.setUsername(user.getUsername());
		userEntity.setPassword(user.getPassword());
		return userEntity;
	}
	
}
