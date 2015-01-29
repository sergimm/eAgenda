package ejb;

import java.util.Collection;

import javax.ejb.Local;

import jpa.CategoryJPA;
import jpa.SuperadministratorJPA;

/**
 * Session EJB Local Interface
 */
@Local
public interface CategoryFacade {
	  public Collection<?> listAllCategories();
	  public void addCategory(String name, String description, SuperadministratorJPA superadministrator);
	  public void updateCategory(int id, String name, String description, SuperadministratorJPA superadministrator);
	  public CategoryJPA showCategory(int id);
	  public Collection<?> listAllSuperadministradors();
	  public SuperadministratorJPA showAdministrador(int id);

}
