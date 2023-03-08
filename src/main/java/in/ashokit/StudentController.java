package in.ashokit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.binding.Student;
import in.ashokit.entity.StudentEntity;
import in.ashokit.repo.StudentRepository;

@Controller
public class StudentController {

	@Autowired
	private StudentRepository repo;

	// method to load student form

	@GetMapping("/")
	public String loadForm(Model model) {

		loadFormData(model);

		return "index";
	}

	private void loadFormData(Model model) {
		List<String> coursesList = new ArrayList<>();
		coursesList.add("Java");
		coursesList.add("DevOps");
		coursesList.add("AWS");
		coursesList.add("Python");

		List<String> timingsList = new ArrayList<>();
		timingsList.add("Morning");
		timingsList.add("Afternoon");
		timingsList.add("Evening");

		Student student = new Student();

		model.addAttribute("courses", coursesList);
		model.addAttribute("timings", timingsList);
		model.addAttribute("student", student);
	}

	// method to save student form data

	@PostMapping("/save")
	public String handleSubmit(Student s, Model model) {

		// logic to save

		StudentEntity entity = new StudentEntity();

		// copy data from binding obj to entity obj
		BeanUtils.copyProperties(s, entity);

		entity.setTimings(Arrays.toString(s.getTimings()));

		repo.save(entity);

		model.addAttribute("msg", "Student Saved");

		loadFormData(model);

		return "index";
	}

	// method to display saved students data
	
	@GetMapping("/viewStudents")
	public String getStudentsData(Model model) {
		
		// logic to fetch and send students data
		
		List<StudentEntity> studentsList = repo.findAll();
		
		model.addAttribute("students", studentsList);
		
		return "data";
	}

}
