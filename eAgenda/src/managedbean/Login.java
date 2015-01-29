package managedbean;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import ejb.AdministrationFacadeRemote;
import jpa.SuperadministratorJPA;
import jpa.UserJPA;



/**
 * Managed Bean ShowCategoryMBean
 */
@ManagedBean(name = "mblogin")
@SessionScoped
public class Login implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String email;
	private String pass;
	private SuperadministratorJPA currentSuperadmin;
	private UserJPA currentUser;
	
	//name to show name in views admin & user
	private String name;
	
	//per diferenciar tipus usuari
	private String tipusUsuari;
	
	//session
	private FacesContext context = FacesContext.getCurrentInstance();
	private HttpServletRequest request = (HttpServletRequest) 
	        context.getExternalContext().getRequest();
   private HttpSession misession= (HttpSession) request.getSession(true);

	
	@EJB
	private AdministrationFacadeRemote adminsRemote;
	
	public Login(){
		this.email="";
		this.pass="";
		this.name="";
	}

	
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public SuperadministratorJPA getCurrentSuperadmin() {
		return currentSuperadmin;
	}

	public void setCurrentSuperadmin(SuperadministratorJPA currentSuperadmin) {
		this.currentSuperadmin = currentSuperadmin;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getTipusUsuari() {
		return tipusUsuari;
	}


	public void setTipusUsuari(String tipusUsuari) {
		this.tipusUsuari = tipusUsuari;
	}

	public String loginAdmin(){

		currentSuperadmin = adminsRemote.findAdmin(email, pass);
		
		if (currentSuperadmin==null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unknown login, try again"));
			return (email=pass=null);
		}else{
			misession.setAttribute("superadmin",currentSuperadmin);
			this.name=currentSuperadmin.getName();
			this.tipusUsuari="admin";
			return "categoryListView?faces-redirect=true";
		}
	}
	
	public String loginUser(){

		currentUser = adminsRemote.findUser(email, pass);
		if (currentUser==null){
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Unknown login, try again"));
			return (email=pass=null);
		}else{
			misession.setAttribute("user",currentUser);
			this.name=currentUser.getName();
			this.tipusUsuari="user";
			return "listEventView?faces-redirect=true";
		}
	}
	
	public String logout(){
		this.currentUser=null;
		this.currentSuperadmin=null;
		this.misession.invalidate();
		return "welcomeView?faces-redirect=true";
	}


}
