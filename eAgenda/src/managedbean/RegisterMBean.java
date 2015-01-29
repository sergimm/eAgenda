package managedbean;

import java.io.Serializable;
import java.util.Properties;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.naming.Context;
import javax.naming.InitialContext;

import ejb.AdministrationFacadeRemote;
import jpa.AddressJPA;
import jpa.UserJPA;

/**
 * 
 */
@ManagedBean(name = "mbregister")
@SessionScoped
public class RegisterMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@EJB
	AdministrationFacadeRemote adminRemote;
	
	private UserJPA user;
	private AddressJPA address;
	private String name;
	private String surname;
	private String nif;
	private String language;
	private String email;
	private String password;
	//address
	private String street;
	private String number;
	private String city;
	private String state;
	private String country;
	private String zip;
	
	public RegisterMBean()throws Exception{
		this.name="";
		this.surname="";
		this.nif="";
	}
	
	
	//getters & setters methods
	public UserJPA getUser() {
		return user;
	}

	public void setUser(UserJPA user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getNif() {
		return nif;
	}


	public void setNif(String nif) {
		this.nif = nif;
	}


	public String getLanguage() {
		return language;
	}


	public void setLanguage(String language) {
		this.language = language;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
	
	public String getStreet() {
		return street;
	}


	public void setStreet(String street) {
		this.street = street;
	}


	public String getNumber() {
		return number;
	}


	public void setNumber(String number) {
		this.number = number;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public String getZip() {
		return zip;
	}


	public void setZip(String zip) {
		this.zip = zip;
	}


	public String addUser(){	
		try{
			Properties props = System.getProperties();
			Context ctx = new InitialContext(props);
			address=new AddressJPA(this.street, this.number, this.city, this.state, this.country, this.zip);
			//(user= new UserJPA(this.nif, this.name, this.surname, this.language, this.password, this.email, address);
			user=new UserJPA();
			user.setAddress(this.address);
			user.setEmail(this.email);
			user.setName(this.name);
			user.setNif(this.nif);
			user.setPassword(this.password);
			user.setPreferedLanguage(this.language);
			user.setSurname(this.surname);
			user.setAddress(address);
			
			adminRemote = (AdministrationFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/AdministrationFacadeBean!ejb.AdministrationFacadeRemote");		
			if(! adminRemote.addUser(user, address)){
				//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("User already registered"));
				return "warningUserView?faces-redirect=true&includeViewParams=true";
				
			}else{
				
			}
			return "welcomeView?faces-redirect=true&includeViewParams=true";
				
		}catch (Exception e) {
			return "ErrorView?faces-redirect=true&includeViewParams=true";	
		}		
	}
	

}
