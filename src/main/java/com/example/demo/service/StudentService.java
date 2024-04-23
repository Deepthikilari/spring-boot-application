package com.example.demo.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UploadStudent;
import com.example.demo.repo.StdRepository;

@Service
public class StudentService {
	
	@Autowired
	private StdRepository repository;
  
	 public Page<UploadStudent> getStudentsPagebypage(int id, String name){
 		 PageRequest of = PageRequest.of(id -1, id);
 		  Page<UploadStudent> all = repository.findAll(of);
 		 return all;
 	 }

	 public List<UploadStudent> getAllStudentsSortedByEmailAndName() {
		   Sort sort = Sort.by(Sort.Direction.ASC, "category").and(Sort.by(Sort.Direction.DESC, "price"));
	       return repository.findAll(sort);
		
	 }

	 public UploadStudent saveUploadStudent(UploadStudent student) {
	        return repository.save(student);
	    }

	    public void deleteStudent(int id) {
	    	repository.deleteById(id);
	    }


    public List<UploadStudent> getAllStudents() {
        return repository.findAll();
    }
	
	  public List<UploadStudent> getStudentsByName(String name) { return
	  repository.findByName(name); }
	  
	  public List<UploadStudent> getStudentsByEmailGreaterThan(String email) {
	  return repository.findByEmailGreaterThan(email); }
	  
	  public List<UploadStudent> getStudentsByNameAndEmailGreaterThan(String
	  name, String email) { return
	  repository.findByNameAndEmailGreaterThan(name, email); }


	 
	    }

	 
	

