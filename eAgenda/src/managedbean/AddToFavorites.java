package managedbean;

import java.io.Serializable;


import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jpa.UserJPA;
import ejb.EventFacadeRemote;

/**
 * Managed Bean ShowCategoryMBean
 */
@ManagedBean(name = "favoritesAdd")
@SessionScoped
public class AddToFavorites implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@EJB
	EventFacadeRemote favoriteRemote;
	
	protected int idEvent;
	protected int idUser;
	
	//session
	private FacesContext context = FacesContext.getCurrentInstance();
	private HttpServletRequest request = (HttpServletRequest) 
	        context.getExternalContext().getRequest();
   private HttpSession misession= (HttpSession) request.getSession(true);
	
	public AddToFavorites() throws Exception {
		this.idEvent=0;
		this.idUser=0;
	}

	public int getIdEvent() {
		return idEvent;
	}

	public void setIdEvent(int idEvent) {
		this.idEvent = idEvent;
	}

	public int getIdUser() {
		return idUser;
	}

	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	public void addToFavorites (){
		UserJPA currentUser = (UserJPA)misession.getAttribute("user");
		String user=currentUser.getNif();
		System.out.println("LISTOS PARA LLAMAR EJB...");
		if (this.idEvent != 0){
			System.out.println("id de Event..."+this.idEvent);
			favoriteRemote.addToFavorites(user, this.idEvent);
		}else{
			System.out.println ("idEvent ="+this.idEvent);
		}

		
	}

}
