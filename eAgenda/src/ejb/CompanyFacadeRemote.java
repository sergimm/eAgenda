/*
 * Copyright UOC.  All rights reserved.
 */
package ejb;

import java.util.Collection;

import javax.ejb.Remote;



import jpa.SuperadministratorJPA;
import jpa.CompanyJPA;

/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface CompanyFacadeRemote {
	  public Collection<?> listAllSuperadministradors();
	  public SuperadministratorJPA showAdministrador(int id);
	  public Collection<?> listAllCompanies();
	  public CompanyJPA showCompany(int id);
	  public void addCompany(String name, SuperadministratorJPA superadministrator);
	  public void updateCompany(int id, String name,
			SuperadministratorJPA superadministrator);
}