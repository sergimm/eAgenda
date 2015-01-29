package ejb;

import java.util.Collection;

import javax.ejb.Local;

import jpa.CompanyJPA;
import jpa.SuperadministratorJPA;

/**
 * Session EJB Local Interface
 */
@Local
public interface CompanyFacade {
	  public Collection<?> listAllSuperadministradors();
	  public SuperadministratorJPA showAdministrador(int id);
	  public Collection<?> listAllCompanies();
	  public CompanyJPA showCompany(int id);
	  public void addCompany(String name, SuperadministratorJPA superadministrator);
	  public void updateCompany(int id, String name,
			SuperadministratorJPA superadministrator);

}
