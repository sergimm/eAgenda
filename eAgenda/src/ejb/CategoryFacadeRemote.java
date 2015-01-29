/*
 * Copyright UOC.  All rights reserved.
 */
package ejb;

import java.util.Collection;
import javax.ejb.Remote;
import jpa.CategoryJPA;
import jpa.SuperadministratorJPA;


/**
 * Session EJB Remote Interfaces
 */
@Remote
public interface CategoryFacadeRemote {
	  /**
	   * Remotely invoked method.
	   */
	  public Collection<?> listAllCategories();
	  public void addCategory(String name, String description, SuperadministratorJPA superadministrator);
	  public void updateCategory(int id, String name, String description, SuperadministratorJPA superadministrator);
	  public CategoryJPA showCategory(int id);
	  public Collection<?> listAllSuperadministradors();
	  public SuperadministratorJPA showAdministrador(int id);
}