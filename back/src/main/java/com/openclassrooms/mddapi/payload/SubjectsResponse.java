
package com.openclassrooms.mddapi.payload;

import java.util.List;

import com.openclassrooms.mddapi.model.Subject;

public class SubjectsResponse {

	private List<Subject> subjects;

	public SubjectsResponse(List<Subject> subjects) {
		this.subjects = subjects;
	}

	public List<Subject> getSubjects() {
		return subjects;
	}

	public void setSubjects(List<Subject> subjects) {
		this.subjects = subjects;
	}

}
