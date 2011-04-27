package pdm.actionListeners;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import pdm.Utils.ColorGradient;
import pdm.tree.TreeBean;

public class WorkspaceListener implements ActionListener, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8877999495074708796L;
	//node styles (colours) for included, excluded and not-decided yet concepts
	private String neutralNodeFace = ColorGradient.getInstance().neutralColor;
	private String includedNodeFace = ColorGradient.getInstance().includedColor;
	private String excludedNodeFace = ColorGradient.getInstance().excludedColor;
	
	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean bean = (TreeBean) context.getApplication().evaluateExpressionGet(context, "#{treeBean}", TreeBean.class);
		
		//akcja dodania noweg konceptu do kwalifikatora (po edycji)
		if(event.getComponent().getId().equals("includeBut"))
			bean.conceptConfirmed(neutralNodeFace, includedNodeFace);
		
		//akcja dodania noweg konceptu do kwalifikatora z prefixem NOT (po edycji)
		else if(event.getComponent().getId().equals("excludeBut"))
			bean.conceptConfirmed(neutralNodeFace, excludedNodeFace);
		
		//pokolorowanie drzewa taksonomii w trakcie edycji zapytania
		else if(event.getComponent().getId().equals("conceptEditing")){
			String elementName = event.getComponent().getAttributes().get("elementName").toString();
			bean.recolour(elementName);
		}
		
		//edytuj wczesniej wybrany koncept
		else if(event.getComponent().getId().equals("editHistConcept")){
			String conceptId = event.getComponent().getAttributes().get("conceptId").toString();
			bean.editHistConcept(conceptId);
		}
		
		//usun wczesniej wybrany koncept z kwalifikatora
		else if(event.getComponent().getId().equals("removeHistConcept")){
			String conceptId = event.getComponent().getAttributes().get("conceptId").toString();
			bean.removeHistConcept(conceptId);
		}
	}
}
