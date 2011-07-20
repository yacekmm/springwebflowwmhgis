package pdm.Utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
/**
 * Klasa odpowiedzialna za komunikaty walidacyjny
 * @author pkonstanczuk
 *
 */
public class Validator {
/**
 * Wystawienie komunikatu informacyjnego
 * @param summary
 */
	public static void setInfoMessage(String summary) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
	}
	/**
	 * Wystawienie komunikatu informacyjnego
	 * 
	 */
	public static void setInfoMessage(UIComponent component, String summary) {
		FacesContext.getCurrentInstance().addMessage(
				component.getClientId(FacesContext.getCurrentInstance()),
				new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
	}
	/**
	 * Wystawienie ostrzeżenia
	 * @param summary
	 */
	public static void setWarnMessage(String summary) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null));
	}
	/**
	 * Wystawienie błędu
	 * @param summary
	 */
	public static void setErrorMessage(String summary) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
	}

}
