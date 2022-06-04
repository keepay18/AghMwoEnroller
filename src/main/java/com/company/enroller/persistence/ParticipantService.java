package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Participant;

@Component("participantService")
public class ParticipantService {

	DatabaseConnector connector;

	public ParticipantService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Participant> getAll() {
		String hql = "FROM Participant";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Participant findByLogin(String login){//dziala jak getter
		return (Participant) connector.getSession().get(Participant.class, login);
	}

	public void delete(Participant participant){
		Transaction transaction=connector.getSession().beginTransaction();
		connector.getSession().delete(participant);
		transaction.commit();
	}

	public void register(Participant participant){
		Transaction transaction=connector.getSession().beginTransaction();
		connector.getSession().delete(participant);
		transaction.commit();
	}

	public void add(Participant participant) {
		Transaction transaction=connector.getSession().beginTransaction();
		connector.getSession().save(participant);
		transaction.commit();
	}

	public void update(Participant foundParticipant) {
		Transaction transaction=connector.getSession().beginTransaction();
		connector.getSession().merge(foundParticipant);
		transaction.commit();

	}


}
