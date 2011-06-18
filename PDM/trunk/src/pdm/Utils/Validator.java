package pdm.Utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

public class Validator {

	public static void setInfoMessage(String summary) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
	}

	public static void setInfoMessage(UIComponent component, String summary) {
		FacesContext.getCurrentInstance().addMessage(
				component.getClientId(FacesContext.getCurrentInstance()),
				new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null));
	}
	
	public static void setWarnMessage(String summary) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_WARN, summary, null));
	}
	
	public static void setErrorMessage(String summary) {
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, null));
	}

}
