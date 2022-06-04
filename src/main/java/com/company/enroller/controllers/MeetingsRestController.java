package com.company.enroller.controllers;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

//http://localhost:80080/meeting_meetings

@RestController
@RequestMapping("/meetings")
public class MeetingsRestController {

	@Autowired
	MeetingService meetingService;

	@Autowired
	ParticipantService participantService;

////////////pobieranie spotkan  - wszystkie i konkretne spotkanie
	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<?> getMeetings() {
		Collection<Meeting> meetings = meetingService.getAll();
		return new ResponseEntity<Collection<Meeting>>(meetings, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
		Meeting meeting = meetingService.findByID(id);
		if (meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}
////////usuwanie spotkania
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteMeeting(@PathVariable("id") long id) {
		Meeting meeting = meetingService.findByID(id);
		if (Optional.of(meeting).isEmpty()) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		meetingService.delete(meeting);
		return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
	}
////////dodawanie spotkan na dwa sposoby
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> registerMeeting(@RequestBody Meeting meeting){
		long id = meeting.getId();

		Meeting meetingFound = meetingService.findByID(id);
		if (meetingFound == null) {
			meetingService.add(meeting);
			return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
		}
		return new ResponseEntity("Unable to create. A meeting with id " + meeting.getId() + " already exist.", HttpStatus.CONFLICT);
	}


	@RequestMapping(value = "/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> registerMeeting(@RequestBody Meeting meeting,@PathVariable("id") long id){

		Meeting meetingFound = meetingService.findByID(id);
		if (meetingFound == null) {
			meetingService.add(meeting);
			return new ResponseEntity<Meeting>(meeting, HttpStatus.OK);
		}
		return new ResponseEntity("Unable to create. A meeting with id " + meeting.getId() + " already exist.", HttpStatus.CONFLICT);
	}

///////// uaktualnianie spotkan na 2 sposoby
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Meeting meeting){

		Meeting foundMeeting = meetingService.findByID(id);
		if (foundMeeting == null) {
			return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
		}
		foundMeeting.setTitle(meeting.getTitle());
		foundMeeting.setDescription(meeting.getDescription());
		foundMeeting.setDate(meeting.getDate());
		meetingService.update(foundMeeting);
		return new ResponseEntity<Meeting>(foundMeeting, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@RequestBody Meeting meeting){
		long id = meeting.getId();
		Meeting foundMeeting = meetingService.findByID(id);
		if (foundMeeting == null) {
			return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
		}
		foundMeeting.setTitle(meeting.getTitle());
		foundMeeting.setDescription(meeting.getDescription());
		foundMeeting.setDate(meeting.getDate());
		meetingService.update(foundMeeting);
		return new ResponseEntity<Meeting>(foundMeeting, HttpStatus.OK);
	}

///////////////////dodawanie uczestnika do spotkania
	@RequestMapping(value = "/{meetingId}/{participantLogin}", method = RequestMethod.PUT)
	public ResponseEntity<?> addPartToMeet(@PathVariable("meetingId") long meetingId, @PathVariable("participantLogin") String participantLogin){

		Meeting foundMeeting = meetingService.findByID(meetingId);
		if (foundMeeting == null) {
			return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
		}

		Participant participant = participantService.findByLogin(participantLogin);

		foundMeeting.addParticipant(participant);
		meetingService.update(foundMeeting);
		return new ResponseEntity<Meeting>(foundMeeting, HttpStatus.OK);
	}

/////////////////usuwanie uczestnika ze spotkania
	@RequestMapping(value = "/{meetingId}/{participantLogin}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deletePartFromMeet(@PathVariable("meetingId") long meetingId, @PathVariable("participantLogin") String participantLogin){

		Meeting foundMeeting = meetingService.findByID(meetingId);
		if (foundMeeting == null) {
			return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
		}

		Participant participant = participantService.findByLogin(participantLogin);

		foundMeeting.removeParticipant(participant);
		meetingService.update(foundMeeting);
		return new ResponseEntity<Meeting>(foundMeeting, HttpStatus.OK);
	}


///////////////pobieranie uczestnikow danego spotkania
	@RequestMapping(value = "/participants/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipants(@PathVariable("id") long id ){
		Meeting meeting = meetingService.findByID(id);
		if (meeting == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		Collection<Participant> participants = meeting.getParticipants();
		return new ResponseEntity<>(participants, HttpStatus.OK);
	}
}

