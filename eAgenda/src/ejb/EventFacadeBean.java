/*
 * Copyright UOC.  All rights reserved.
 */
package ejb;

import java.util.*;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import jpa.CategoryJPA;
import jpa.CommentJPA;
import jpa.CompanyJPA;
import jpa.EventJPA;
import jpa.AddressJPA;
import jpa.FavoriteJPA;
import jpa.RattingJPA;
import jpa.SuggestionJPA;
import jpa.UserJPA;
import jpa.WordsJPA;

/**
 * EJB Session Bean Class of example "Practical Case Study JEE"
 */
@Stateless
public class EventFacadeBean implements EventFacade, EventFacadeRemote {	
	//Persistence Unit Context
	@PersistenceContext(unitName="infogroup2eagenda") 
	private EntityManager entman;
   
	@SuppressWarnings("unchecked")
	@Override
	public Collection<?> listAllCategories() {
		try{
			Collection<CategoryJPA> allCategory = entman.createQuery("from CategoryJPA").getResultList();
			return allCategory;	
		}catch (PersistenceException e) {
			throw e;
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public java.util.Collection<EventJPA> listAllEvents() {		
		try{
			Collection<EventJPA> allEvents = entman.createQuery("from EventJPA").getResultList();
			return allEvents;	
		}catch (PersistenceException e) {
			throw e;
		}
	}
	@Override
	public AddressJPA showAddress(int id) {
		AddressJPA address = null;
		try	{
			//Obtenim la adreca amb id que busquem
			address = entman.find(AddressJPA.class, id);
		}catch (PersistenceException e) {
			throw e;
		}
	    return address;
	}
	@SuppressWarnings("unchecked")
	@Override
	public java.util.Collection<EventJPA> SearchEventByName(String nameEvent){
		Collection <EventJPA> filteredEvents = entman.createQuery(
				"Select e from EventJPA e where lower(name) like :custName")				
				.setParameter("custName", nameEvent.toLowerCase())
				.getResultList();
		System.out.println(filteredEvents  + " desde JPA  " + filteredEvents.toString() );

		return filteredEvents;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Collection<EventJPA> SearchEventByCategory(int idCategory) {
		Collection <EventJPA> filteredEvents = entman.createQuery(
				"Select p from EventJPA p  join p.categoriesEvent e where e.id in :custName")
				.setParameter("custName", idCategory)
				.getResultList();
		System.out.println(filteredEvents  + " desde JPA  " + filteredEvents.toString() );

		return filteredEvents;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<EventJPA> SearchEventByWord(String nameword) {
		Collection <EventJPA> filteredEvents = entman.createQuery(
				"Select p from EventJPA p  join p.wordsEvent e where e.name like :custName")
				.setParameter("custName", nameword)
				.getResultList();
		System.out.println(filteredEvents  + " desde JPA  " + filteredEvents.toString() );

		return filteredEvents;
		
	}
	
	@Override
	public EventJPA showEvent(int id) {
		EventJPA event = null;
		try	{
			event = entman.find(EventJPA.class, id);
			System.out.println(event);
		}catch (PersistenceException e) {
			throw e;
		}
	    return event;
	}
	
	@Override
	public void addCategoryToEvent(int idEvent, int idCategory) {
		EventJPA event = null;
		CategoryJPA category = null;
		try	{
				event = entman.find(EventJPA.class, idEvent);
				category = entman.find(CategoryJPA.class, idCategory);
				event.getCategoriesEvent().add(category);			
				entman.merge(event);
				entman.flush();
		}catch (PersistenceException e) {
			throw e;
		}
	}
	
	@Override
	public void removeCategoryToEvent(int idEvent, int idCategory) {
		EventJPA event = null;
		CategoryJPA category = null;
		try	{
			event = entman.find(EventJPA.class, idEvent);
			category = entman.find(CategoryJPA.class, idCategory);
			event.getCategoriesEvent().remove(category);			
			entman.merge(event);
			entman.flush();
		}catch (PersistenceException e) {
			throw e;
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<?> listAllAdress() {
		try{
			Collection<AddressJPA> allSuperAdmin = entman.createQuery("from AddressJPA").getResultList();
			return allSuperAdmin;	
		}catch (PersistenceException e) {
			throw e;
		}
	}
	
	@Override
	public void addEvent(String name, String description, String picture,
			AddressJPA address, Date initDate, Date endDate, CompanyJPA company) {
		try	{
			EventJPA event = new EventJPA();			
			event.setName(name);
			event.setDescription(description);
			event.setPicture(picture);
			event.setAddress(address);
			event.setInitDate(initDate);
			event.setEndDate(endDate);
			event.setCompany(company);
			entman.joinTransaction();
			entman.persist(event);
			entman.flush();
		}catch (PersistenceException e) {
			throw e;
		}
		
	}
	@Override
	public void updateEvent(int id, String name, String description,
			String picture, AddressJPA address, Date initDate,
			Date endDate, CompanyJPA company) {
		try	{
			EventJPA event = entman.find(EventJPA.class, id);			
			event.setName(name);
			event.setDescription(description);
			event.setPicture(picture);
			event.setAddress(address);
			event.setInitDate(initDate);
			event.setEndDate(endDate);
			event.setCompany(company);
			entman.merge(event);
			entman.flush();
		}catch (PersistenceException e) {
			throw e;
		}
	}
	@Override
	public void addWordToEvent(int idEventToAdd, String newWordName) {
		try	{
			WordsJPA word = new WordsJPA();	
			EventJPA event = entman.find(EventJPA.class, idEventToAdd);
			word.setName(newWordName);
			word.setEventWords(event);			
			entman.joinTransaction();
			entman.persist(word);
			entman.flush();
		}catch (PersistenceException e) {
			throw e;
		}		
	}
	@Override
	public void removeWordToEvent(int idWordToRemove) {
		try	{
			WordsJPA word =  entman.find(WordsJPA.class, idWordToRemove);		
			entman.joinTransaction();
			entman.remove(word);
			entman.flush();
		}catch (PersistenceException e) {
			throw e;
		}		
	}
	@SuppressWarnings("unchecked")
	@Override
	public Collection<WordsJPA> listAllEventWords(EventJPA event) {
		Collection <WordsJPA> words = entman.createQuery(
				"Select w from WordsJPA w where w.eventWords = :event")
				.setParameter("event", event)
				.getResultList();
		return words;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Collection<CommentJPA> listAllEventComments(EventJPA event) {
		Collection <CommentJPA> comments = entman.createQuery(
				"Select c from CommentJPA c where c.eventComment = :event")
				.setParameter("event", event)
				.getResultList();
		return comments;
	}
	@SuppressWarnings("unchecked")
	@Override
	public CommentJPA findEventCommentByUser(EventJPA event, UserJPA user) {
		//We cannot use getSingleResult because if no result an exception in thrown.
		List<CommentJPA> comments = entman.createQuery(
				"Select c from CommentJPA c where c.eventComment = :event and c.userComment = :user")
				.setParameter("event", event)
				.setParameter("user", user)
				.getResultList();
		
		return (comments.isEmpty()) ? null : comments.get(0);
	}
	
	@Override
	public void addCommentToEvent(int idEvent, int idUser, String commentTxt) {
		try	{
			UserJPA user = entman.find(UserJPA.class, idUser);	
			EventJPA event = entman.find(EventJPA.class, idEvent);
			CommentJPA comment = new CommentJPA();
			if(user != null && event != null){
				comment.setEventComment(event);
				comment.setUserComment(user);
				comment.setText(commentTxt);
				entman.joinTransaction();
				entman.persist(comment);
				entman.flush();
			}
		}catch (PersistenceException e) {
			throw e;
		}		
	}
	
	@Override
	public void updateCommentToEvent(int idComment, String commentTxt) {
		try	{
			CommentJPA comment = entman.find(CommentJPA.class, idComment);
			if(comment != null){
				comment.setText(commentTxt);
				entman.merge(comment);
				entman.flush();
			}
		}catch (PersistenceException e) {
			throw e;
		}	
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void addToFavorites (String user, int eventId){
		  FavoriteJPA favorite = new FavoriteJPA();
		  
		  try{

			  UserJPA myuser=this.findUser(user);
			  EventJPA myevent = entman.find(EventJPA.class, eventId);
			  
			  //validate
				Collection <EventJPA> favorites = entman.createQuery(
						"Select f.event from FavoriteJPA f join f.user u where u.id = :idUser")
						.setParameter("idUser", myuser.getId())
						.getResultList();
				
				if (favorites.contains(myevent)){
					System.out.println("Event ja es favorit :"+myevent.getName());
				}else{
					  //ready to add to favorites
					  favorite.setEvent(myevent);
					  favorite.setUser(myuser);
					  
						entman.joinTransaction();
						entman.persist(favorite);
						entman.flush();
				}
			  
		  } catch (PersistenceException e){
			  e.printStackTrace();
		  }
	  }
	  
	  
	/**
	 * find a user with an nif given
	 * @param mail
	 * @return user
	 */
	 private UserJPA findUser (String nif){
		UserJPA user=null;
		
		try {
			@SuppressWarnings("unchecked")
			Collection<UserJPA> admins = entman.createQuery("SELECT s FROM UserJPA s WHERE s.nif LIKE :saName")
			.setParameter("saName", nif).getResultList();
			
			if (!admins.isEmpty()|| admins.size()==1){
				Iterator<UserJPA> iter =admins.iterator();
				user = (UserJPA)iter.next();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return user;
	
	}

	@SuppressWarnings("unchecked")
	@Override
	public RattingJPA findEventRattingByUser(EventJPA event, UserJPA user) {
		//We cannot use getSingleResult because if no result an exception in thrown.
		List<RattingJPA> ratting = entman.createQuery(
				"Select r from RattingJPA r where r.eventRatting = :event and r.userRatting = :user")
				.setParameter("event", event)
				.setParameter("user", user)
				.getResultList();
		
		return (ratting.isEmpty()) ? null : ratting.get(0);
	}
	
	
	@Override
	public void addRattingToEvent(int idEvent, int idUser, int rattingNum) {
		try	{
			UserJPA user = entman.find(UserJPA.class, idUser);	
			EventJPA event = entman.find(EventJPA.class, idEvent);
			RattingJPA ratting = new RattingJPA();
			if(user != null && event != null){
				ratting.setEventRatting(event);
				ratting.setUserRatting(user);
				ratting.setRatting(rattingNum);
				entman.joinTransaction();
				entman.persist(ratting);
				entman.flush();
			}
		}catch (PersistenceException e) {
			throw e;
		}		
	}
	@Override
	public void updateRattingToEvent(int idRatting, int rattingNum) {
		try	{
			RattingJPA ratting  = entman.find(RattingJPA.class, idRatting);
			if(ratting != null){
				ratting.setRatting(rattingNum);
				entman.joinTransaction();
				entman.persist(ratting);
				entman.flush();
			}
		}catch (PersistenceException e) {
			throw e;
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection <EventJPA> listAllFavorites (String user){
		UserJPA myuser=this.findUser(user);
		try{
			Collection <EventJPA> favorites = entman.createQuery(
					"Select f.event from FavoriteJPA f join f.user u where u.id = :idUser")
					.setParameter("idUser", myuser.getId())
					.getResultList();
			return favorites;
		}catch (PersistenceException e){
		
			throw e;
		}
	}
	@Override
	public void suggest(int idEvent, String email, UserJPA senderUser,
			String text) {
		SuggestionJPA suggestion = new SuggestionJPA();
		EventJPA event = entman.find(EventJPA.class, idEvent);
		if(senderUser != null && event != null){
			suggestion.setEventSuggestion(event);
			suggestion.setUserSuggestion(senderUser);
			suggestion.setEmail(email);
			suggestion.setText(text);
			entman.joinTransaction();
			entman.persist(suggestion);
			entman.flush();
		}		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<SuggestionJPA> findReceivedSuggestions(String email) {
		Collection <SuggestionJPA> suggestions = entman.createQuery(
				"Select s from SuggestionJPA s where s.email = :userEmail")
				.setParameter("userEmail", email)
				.getResultList();
		return suggestions;
	}	
}
