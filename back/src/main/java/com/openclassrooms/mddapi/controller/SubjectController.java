
package com.openclassrooms.mddapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.mddapi.payload.MessageApiResponse;
import com.openclassrooms.mddapi.payload.SubjectsResponse;
import com.openclassrooms.mddapi.service.SubjectService;

@RestController
@RequestMapping("api/subject")
@CrossOrigin("*")
public class SubjectController {

	@Autowired
	private SubjectService subjectService;

	@GetMapping("")
	public ResponseEntity<SubjectsResponse> getSubjects() {
		return new ResponseEntity<>(subjectService.getSubjects(), HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<SubjectsResponse> getSubjectSubscribed() {
		return new ResponseEntity<>(subjectService.getSubjectSubscribed(), HttpStatus.OK);
	}

	@PostMapping("/subscribe/{id}")
	public ResponseEntity<MessageApiResponse> subscribeFromSubject(@PathVariable("id") final Long id) {
		return new ResponseEntity<>(subjectService.subscribe(id), HttpStatus.OK);
	}

	@DeleteMapping("/unsubscribe/{id}")
	public ResponseEntity<MessageApiResponse> unsubscribeFromSubject(@PathVariable("id") final Long id) {
		return new ResponseEntity<>(subjectService.unsubscribe(id), HttpStatus.OK);
	}

}
