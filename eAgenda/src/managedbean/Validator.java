package managedbean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * Managed Bean Validator
 * Valida camps dels formularis
 */
@ManagedBean(name ="mbValidate")
@SessionScoped
public class Validator implements Serializable{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Valida que les dades introduides no estiguin sense contingut
	 * o siguin espais en blanc
	 */
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
	         throws ValidatorException {
	      if (((String)arg2).trim().length()==0 || ((String)arg2)==null) {
	         throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Content is required", null));
	      }
	   }

}
