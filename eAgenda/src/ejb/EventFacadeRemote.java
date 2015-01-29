package ejb;

import java.util.Collection;
import java.util.Date;


import javax.ejb.Remote;

import jpa.AddressJPA;
import jpa.CommentJPA;
import jpa.CompanyJPA;
import jpa.EventJPA;
import jpa.RattingJPA;
import jpa.SuggestionJPA;
import jpa.UserJPA;
import jpa.WordsJPA;


/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface EventFacadeRemote {
	  public Collection<?> listAllEvents();

	  public AddressJPA showAddress(int id);
	  public void addCategoryToEvent(int idEvent, int idCategory);
	  public void removeCategoryToEvent(int idEvent, int idCategory);
	  
	  public void addEvent(String name, String description, String image, AddressJPA address, Date initDate, Date endDate, CompanyJPA company);
	  public void updateEvent(int id, String name, String description, String image, AddressJPA address, Date initDate, Date endDate, CompanyJPA company);
	  public EventJPA showEvent(int id);
	  
	  public Collection<?> listAllAdress();
	  
	  public Collection<EventJPA> SearchEventByName(String nameEvent);
	  public Collection<EventJPA> SearchEventByCategory(int idCategory);
	  public Collection<EventJPA> SearchEventByWord(String nameword);
	  public Collection<?> listAllCategories();
	  
	  public void addWordToEvent(int idEventToAdd, String newWordName);
	  public void removeWordToEvent(int idWordToRemove);
	  public Collection<WordsJPA> listAllEventWords(EventJPA event);
	  
	  public Collection<CommentJPA> listAllEventComments(EventJPA event);
	  public CommentJPA findEventCommentByUser(EventJPA event, UserJPA user);	  
	  public void addCommentToEvent(int idEvent, int idUser, String commentTxt);
	  public void updateCommentToEvent(int idComment, String commentTxt);
	  
	  public void addToFavorites (String user, int eventId);

	  public RattingJPA findEventRattingByUser(EventJPA event, UserJPA user);
	  public void addRattingToEvent(int idEvent, int idUser, int rattingNum);
	  public void updateRattingToEvent(int idRatting, int rattingNum);
	  
	  public Collection<EventJPA> listAllFavorites (String user);
	  
	  public void suggest(int idEvent, String email, UserJPA senderUser, String text);
	  public Collection<SuggestionJPA> findReceivedSuggestions(String email);

}
