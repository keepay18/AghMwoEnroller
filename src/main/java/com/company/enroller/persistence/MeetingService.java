package com.company.enroller.persistence;

import java.util.Collection;

import com.company.enroller.model.Participant;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

@Component("meetingService")
public class MeetingService {
//tutaj jetst recznie napisany service ale spring ma automatyczne servisy
	DatabaseConnector connector;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Meeting findByID(long id){//dziala jak getter
		return (Meeting) connector.getSession().get(Meeting.class, id);
	}

	public void add(Meeting meeting) {Transaction transaction=connector.getSession().beginTransaction();
		connector.getSession().save(meeting);
		transaction.commit();
	}

	public void delete(Meeting meeting){
		Transaction transaction=connector.getSession().beginTransaction();
		connector.getSession().delete(meeting);
		transaction.commit();
	}

	public void register(Meeting meeting){
		Transaction transaction=connector.getSession().beginTransaction();
		connector.getSession().delete(meeting);
		transaction.commit();
	}
	public void update(Meeting foundMeeting) {
		Transaction transaction=connector.getSession().beginTransaction();
		connector.getSession().merge(foundMeeting);
		transaction.commit();

	}

}
