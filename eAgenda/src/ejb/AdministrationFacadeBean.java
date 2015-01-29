package ejb;


import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import jpa.AddressJPA;
import jpa.SuperadministratorJPA;
import jpa.UserJPA;


@Stateless
public class AdministrationFacadeBean implements AdministrationFacadeRemote, AdministrationFacade {
	
	//Persistence Unit Context
	@PersistenceContext(unitName="infogroup2eagenda") 
	private EntityManager entman;
	
	@Override
	public SuperadministratorJPA findAdmin (String email, String pass){
		SuperadministratorJPA sadmin=null;
		
		try {
			@SuppressWarnings("unchecked")
			Collection<SuperadministratorJPA> admins = entman.createQuery("SELECT s FROM SuperadministratorJPA s WHERE s.email LIKE :saMail")
			.setParameter("saMail", email).getResultList();
			
			if (!admins.isEmpty()|| admins.size()==1){
				Iterator<SuperadministratorJPA> iter =admins.iterator();
				sadmin = (SuperadministratorJPA)iter.next();
				
				if (!(sadmin.getPassword().equals(pass))){
					sadmin=null;
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Problema en EJB --> findAdmin");
		}
		
		return sadmin;
	
	}
	
	@Override
	public UserJPA findUser (String email, String pass){
		UserJPA suser=null;
		
		try {
			@SuppressWarnings("unchecked")
			Collection<UserJPA> users = entman.createQuery("SELECT s FROM UserJPA s WHERE s.email LIKE :saMail")
			.setParameter("saMail", email).getResultList();
			
			if (!users.isEmpty()|| users.size()==1){
				Iterator<UserJPA> iter =users.iterator();
				suser = (UserJPA)iter.next();
				
				if (!(suser.getPassword().equals(pass))){
					suser=null;
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Problema en EJB --> findUser");
		}
		
		return suser;
	
	}
	
	@Override
	public boolean addUser(UserJPA user, AddressJPA address){
	   boolean afegit=false;
	   UserJPA usuariRegistrat=findUser(user.getEmail(), user.getPassword());
		try{
			if(usuariRegistrat==null){
				
				entman.joinTransaction();
				entman.persist(address);
				entman.flush();
				entman.refresh(address);
				entman.joinTransaction();
				entman.persist(user);
				entman.flush();
				afegit=true;
			}else{
				System.out.println ("Usuari ja existeix!!!");
			}
	   }catch (Exception e){
		   e.printStackTrace();
	   }
		return afegit;
	}
	
	@Override
	public UserJPA findUserByEmail (String email){
		UserJPA suser=null;
		
		try {
			//We cannot use getSingleResult because if no result an exception in thrown.
			@SuppressWarnings("unchecked")
			List<UserJPA> user =  entman.createQuery("SELECT s FROM UserJPA s WHERE s.email LIKE :saMail")
					.setParameter("saMail", email).getResultList();			
			return (user.isEmpty()) ? null : user.get(0);
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Problema en EJB --> findUserByEmail");
		}		
		return suser;	
	}
}