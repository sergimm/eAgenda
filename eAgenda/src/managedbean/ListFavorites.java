package managedbean;

import java.io.Serializable;
import java.util.Collection;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;


import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ejb.EventFacadeRemote;
import jpa.EventJPA;
import jpa.UserJPA;


/**
 * Managed Bean ShowCategoryMBean
 */
@ManagedBean(name = "mbfavorites")
@SessionScoped
public class ListFavorites implements Serializable {
	private static final long serialVersionUID = 1L;
	@EJB
	EventFacadeRemote favoriteRemote;
	
	protected Collection<EventJPA> favoritesList;
	protected int idEvent = 1;
	
	//session
	private FacesContext context = FacesContext.getCurrentInstance();
	private HttpServletRequest request = (HttpServletRequest) 
	        context.getExternalContext().getRequest();
   private HttpSession misession= (HttpSession) request.getSession(true);
	
	public ListFavorites()throws Exception{
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		favoriteRemote = (EventFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/EventFacadeBean!ejb.EventFacadeRemote");
		
		
		this.favoritesList();
	}
	
	
	public Collection<EventJPA> getFavoritesList() {
		return favoritesList;
	}


	public void setFavoritesList(Collection<EventJPA> favoritesList) {
		this.favoritesList = favoritesList;
	}


	public int getIdEvent() {
		return idEvent;
	}


	public void setIdEvent(int idEvent) throws Exception {
		this.idEvent = idEvent;
		favoritesList();
	}


	public String favoritesList() throws Exception {	
		UserJPA currentUser = (UserJPA)misession.getAttribute("user");
		//Hotfix error
		if(currentUser != null){
			String user=currentUser.getNif();
			try{
				this.favoritesList = (Collection<EventJPA>) favoriteRemote.listAllFavorites(user);
				return "favoritesListView?faces-redirect=true&includeViewParams=true";
				//return "categoryDetailView?faces-redirect=true&favoritesList="+this.favoritesList();
			}catch (Exception e) {
				return "ErrorView?faces-redirect=true&includeViewParams=true";	
			}
		}
		return null;
	}

}
