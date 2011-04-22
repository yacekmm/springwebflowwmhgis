package pdm.actionListeners;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import pdm.tree.TreeBean;

public class WorkspaceListener implements ActionListener, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8877999495074708796L;
	//node styles (colours) for included, excluded and not-decided yet concepts
	private String neutralNodeFace = "orange", includedNodeFace = "green", excludedNodeFace = "red";
	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean bean = (TreeBean) context.getApplication().evaluateExpressionGet(context, "#{treeBean}", TreeBean.class);
		
		if(event.getComponent().getId().equals("includeBut"))
			bean.conceptConfirmed(neutralNodeFace, includedNodeFace);
		else if(event.getComponent().getId().equals("excludeBut"))
			bean.conceptConfirmed(neutralNodeFace, excludedNodeFace);
		else if(event.getComponent().getId().equals("conceptEditing")){
			String elementName = event.getComponent().getAttributes().get("elementName").toString();
			bean.recolour(elementName);
		}else if(event.getComponent().getId().equals("editHistConcept")){
			String conceptId = event.getComponent().getAttributes().get("conceptId").toString();
			bean.editHistConcept(conceptId);
		}
	}
}
