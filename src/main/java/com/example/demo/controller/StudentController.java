package com.example.demo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entity.UploadStudent;
import com.example.demo.repo.StdRepository;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/api")
public class StudentController {

	@Autowired
	private StudentService service;
	@Autowired
	StdRepository std;

	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {

		UploadStudent student = new UploadStudent();
		student.setId(5);
		student.setName("abc");
		student.setEmail("abc1@gmail.com");
		System.out.println("from postman:" + student);
		student.setFileName(file.getOriginalFilename());
		try {
			student.setResume(file.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
		std.save(student);
		return ResponseEntity.ok("File uploaded successfully" + file.getOriginalFilename());

	}

	

	@GetMapping("/download/{sid}")
	public ResponseEntity<Resource> download(@PathVariable("sid") int sid) {
		Optional<UploadStudent> findById = std.findById(sid);
		if (findById.isPresent()) {
			UploadStudent upload = findById.get();
			ByteArrayResource resource = new ByteArrayResource(upload.getResume());
			return ResponseEntity.ok().contentLength(upload.getResume().length)
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + upload.getFileName() + "\"")
					.body(resource);
		} else {
			return ResponseEntity.badRequest().build();

		}
	}

	 @PutMapping("/{id}")
	    public UploadStudent updateDocument(@PathVariable int id, @RequestBody UploadStudent student) {
		 student.setId(id);
	        return service.saveUploadStudent(student);
	    }

	    @DeleteMapping("/{id}")
	    public void deleteUploadStudent(@PathVariable int id) {
	        service.deleteStudent(id);
	    }
	
	@GetMapping("/pagegetall")
	public ResponseEntity<Page<UploadStudent>> getAllUploadStudent(@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "4") String size) {
		Page<UploadStudent> students = service.getStudentsPagebypage(page, size);
		return ResponseEntity.ok(students);
	}

	@GetMapping("/sorted")
	public ResponseEntity<List<UploadStudent>> getAllStudentsSortedByEmailAndName() {
		List<UploadStudent> students = service.getAllStudentsSortedByEmailAndName();
		
		return ResponseEntity.ok().body(students);
	}
	
	  @GetMapping("/filter")
	    public ResponseEntity<List<UploadStudent>> getFilteredstudents(
	            @RequestParam(required = false) String name,
	            @RequestParam(required = false) String email) {
	        List<UploadStudent> students;
	        if (name != null && email != null) {
	            students = service.getStudentsByNameAndEmailGreaterThan(name, email);
	        } else if (name != null) {
	            students = service.getStudentsByName(name);
	        } else if (email != null) {
	            students = service.getStudentsByEmailGreaterThan(email);
	        } else {
	            students = service.getAllStudents();
	        }
	        return ResponseEntity.ok().body(students);
	    }
}
