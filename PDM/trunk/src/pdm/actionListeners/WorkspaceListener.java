package pdm.actionListeners;

import java.io.Serializable;


import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;

import pdm.Utils.Const;
import pdm.Utils.FileUploadBean;
import pdm.Utils.PdmLog;
import pdm.tree.TreeBean;

public class WorkspaceListener implements ActionListener, /*ValueChangeListener,*/
		Serializable {

	/**
	 * Serializacja
	 */
	private static final long serialVersionUID = 8877999495074708796L;
	/**
	 * oznaczenie (kolor) konceptu ktory jest w trakcie edycji (wybrany, ale jeszcze nie zatwierdzony)
	 */
	private String neutralNodeFace = Const.neutralColor;
	/**
	 * oznaczenie (kolor) konceptu ktory jest WLACZONY do kwalifikatora
	 */
	private String includedNodeFace = Const.includedColor;
	/**
	 * oznaczenie (kolor) konceptu ktory jest WYLACZONY z kwalifikatora
	 */
	private String excludedNodeFace = Const.excludedColor;

	/**
	 * obsuguje zdarzenia pochodzace z interfejsu (akcje uzytkownika)
	 */
	@Override
	public void processAction(ActionEvent event)
			throws AbortProcessingException {
		PdmLog.getLogger().info(
				"Processing action for component: "
						+ event.getComponent().getId());
		
		//odwolaj sie do kontekstu z ktorego pochodzi zdarzenie
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean bean = (TreeBean) context.getApplication()
				.evaluateExpressionGet(context, "#{treeBean}", TreeBean.class);
		FileUploadBean fbean = (FileUploadBean) context.getApplication()
		.evaluateExpressionGet(context, "#{fileUploadBean}", FileUploadBean.class);

		// akcja dodania noweg konceptu do kwalifikatora (po edycji)
		if (event.getComponent().getId().equals("includeBut"))
			bean.conceptConfirmed(neutralNodeFace, includedNodeFace);

		if (event.getComponent().getId().equals("indexingButton"))
		{
			bean.changeMode();
		    fbean.clearUploadData();
		}
		
		
		if (event.getComponent().getId().equals("removeIndexingElement")) {
			String ElementId = event.getComponent().getAttributes().get(
					"elementId").toString();
			bean.removeFromSelectedTaxElements(Integer.parseInt(ElementId));
		}

		// akcja dodania noweg konceptu do kwalifikatora z prefixem NOT (po
		// edycji)
		else if (event.getComponent().getId().equals("excludeBut"))
			bean.conceptConfirmed(neutralNodeFace, excludedNodeFace);

		// pokolorowanie drzewa taksonomii w trakcie edycji zapytania
		else if (event.getComponent().getId().equals("conceptEditing")) {
			String elementName = event.getComponent().getAttributes().get(
					"elementName").toString();
			bean.conceptEditing(elementName);
			//bean.recolour(elementName);
		}

		// edytuj wczesniej wybrany koncept
		else if (event.getComponent().getId().equals("editHistConcept")) {
			String conceptId = event.getComponent().getAttributes().get(
					"conceptId").toString();
			bean.editHistConcept(conceptId);
		}

		// usun wczesniej wybrany koncept z kwalifikatora
		else if (event.getComponent().getId().equals("removeHistConcept")) {
			String conceptId = event.getComponent().getAttributes().get(
					"conceptId").toString();
			bean.removeHistConcept(conceptId);
			
			//wymus odswiezenie strony (bo sa problemy z onclick=submit();
//			String viewId = context.getViewRoot().getViewId();
//			ViewHandler handler = context.getApplication().getViewHandler();
//			UIViewRoot root = handler.createView(context, viewId);
//			root.setViewId(viewId);
//			context.setViewRoot(root);
		}

		// usun TaxEelement z edytowanego konceptu poczynajac od kliknietego do
		// konca
		else if (event.getComponent().getId().equals("removeTaxElement")) {
			String taxElementName = event.getComponent().getAttributes().get(
					"taxElementName").toString();
			//bean.cutConceptV1(taxElementName);
			bean.cutConceptV2(taxElementName);
		}

		else if (event.getComponent().getId().equals("savingSearchResultButton")) {
			if (bean.saveSearchResult())
				fbean.clearUploadData();
			

		}
		else if (event.getComponent().getId().equals("removeIndexingElement")) {
			Integer taxElementId = Integer.parseInt(event.getComponent().getAttributes().get(
			"elementID").toString());
			bean.removeFromSelectedTaxElements(taxElementId);		 
		}
		else if (event.getComponent().getId().equals("clearUploadData")) {
			fbean.clearUploadData();			
		}
		//rozpoczecie nowego wyszkiwania
		else if (event.getComponent().getId().equals("newSearch")) {
			bean.newSearch();	
		}
		
		
	}
}
