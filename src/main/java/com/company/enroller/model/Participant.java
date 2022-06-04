package com.company.enroller.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "participant")
public class Participant {

	@Id
	private String login;

	@Column
	private String password;

	///added for the sake of joint table
//	@ManyToMany(mappedBy = "participant", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//	@JsonIgnore
//	private Set<Meeting> meetings = new HashSet<>();
//	///////////////////////////////////////////

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	/////added for a joint table///////////
//	public Set<Meeting> getMeetings() {
//		return meetings;
//	}
//	public void setMeetings(Set<Meeting> meetings) {
//		this.meetings = meetings;
//	}
	//////////////////////////////////////
}
