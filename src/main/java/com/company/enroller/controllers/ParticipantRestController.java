package com.company.enroller.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;

//http://localhost:80080/participants //tutaj projektujemy endpoint, jak dopiszemy na przodzie GET to wiemy ze chcemy zeby pobrac wszystkich

@RestController
@RequestMapping("/participants")
public class ParticipantRestController {

	@Autowired
	ParticipantService participantService;

	@RequestMapping(value = "", method = RequestMethod.GET) //sciezka jest pusta bo chcemy obslugiwac n-pint, to co w gorze
	public ResponseEntity<?> getParticipants() {
		Collection<Participant> participants = participantService.getAll();
		return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> getParticipant(@PathVariable("id") String login) {
		Participant participant = participantService.findByLogin(login);
		if (participant == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteParticipant(@PathVariable("id") String login) {
		Participant participant = participantService.findByLogin(login);
		if (participant == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		participantService.delete(participant);
		return new ResponseEntity<Participant>(participant, HttpStatus.OK);
	}

	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<?> registerParticipant(@RequestBody Participant participant){
		String login = participant.getLogin();

		Participant participantFound = participantService.findByLogin(login);
		if (participantFound == null) {
			participantService.add(participant);
			return new ResponseEntity<Participant>(participant, HttpStatus.OK);

		}
		return new ResponseEntity("Unable to create. A participant with login " + participant.getLogin() + " already exist.", HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<?> update(@PathVariable("id") String login, @RequestBody Participant participant){

		Participant foundParticipant = participantService.findByLogin(login);
		if (foundParticipant == null) {
			return new ResponseEntity<>("Not found", HttpStatus.NOT_FOUND);
		}
		foundParticipant.setPassword(participant.getPassword());
		participantService.update(foundParticipant);
		return new ResponseEntity<Participant>(foundParticipant, HttpStatus.OK);
	}


}
