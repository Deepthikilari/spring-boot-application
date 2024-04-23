package com.example.demo.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.UploadStudent;

@Repository
public interface StdRepository extends JpaRepository<UploadStudent, Integer> {

	List<UploadStudent> findByName(String name);

	List<UploadStudent> findByEmailGreaterThan(String email);

	List<UploadStudent> findByNameAndEmailGreaterThan(String name, String email);


}
