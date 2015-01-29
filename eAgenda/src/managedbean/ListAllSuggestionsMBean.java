/*
 * Copyright UOC.  All rights reserved.
 */
package managedbean;

import java.io.Serializable;
import java.util.*;

import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jpa.SuggestionJPA;
import jpa.UserJPA;
import ejb.EventFacadeRemote;


/**
 * Managed Bean ListCategoriesMBean
 */
@ManagedBean(name = "suggestList")
@SessionScoped
public class ListAllSuggestionsMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@EJB
	private EventFacadeRemote eventRemote;		
	protected Collection<SuggestionJPA> suggestionList;
	protected int idSuggestion = -1;
	private UserJPA user;

	
	private FacesContext context = FacesContext.getCurrentInstance();
	private HttpServletRequest request = (HttpServletRequest) 
	        context.getExternalContext().getRequest();
    private HttpSession misession= (HttpSession) request.getSession(true);
	
	public ListAllSuggestionsMBean() throws Exception {
		Properties props = System.getProperties();
		Context ctx = new InitialContext(props);
		this.eventRemote = (EventFacadeRemote) ctx.lookup("java:app/infogroup2eagenda.jar/EventFacadeBean!ejb.EventFacadeRemote");
		
		suggestionList();
	}	

	public Collection<SuggestionJPA> getSuggestionList() {
		return suggestionList;
	}

	public void setSuggestionList(Collection<SuggestionJPA> suggestionList) {
		this.suggestionList = suggestionList;
	}

	public int getIdSuggestion() {
		return idSuggestion;
	}

	public void setIdSuggestion(int idSuggestion) throws Exception {
		this.idSuggestion = idSuggestion;
		suggestionList();
	}

	public String suggestionList() throws Exception {
		this.user = (UserJPA) misession.getAttribute("user");
		if(user != null){
			try{
				this.suggestionList = this.eventRemote.findReceivedSuggestions(user.getEmail());
				return "listAllSuggestionsView?faces-redirect=true&includeViewParams=true";
			}catch (Exception e) {
				return "ErrorView?faces-redirect=true&includeViewParams=true";	
			}
		}else{
			return "ErrorView?faces-redirect=true&includeViewParams=true";
		}
	}
}