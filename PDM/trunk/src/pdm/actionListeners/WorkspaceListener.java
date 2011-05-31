package pdm.actionListeners;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;

import pdm.Utils.ColorGradient;
import pdm.Utils.PdmLog;
import pdm.tree.TreeBean;

public class WorkspaceListener implements ActionListener, ValueChangeListener,
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8877999495074708796L;
	// node styles (colours) for included, excluded and not-decided yet concepts
	private String neutralNodeFace = ColorGradient.getInstance().neutralColor;
	private String includedNodeFace = ColorGradient.getInstance().includedColor;
	private String excludedNodeFace = ColorGradient.getInstance().excludedColor;

	@Override
	public void processAction(ActionEvent event)
			throws AbortProcessingException {
		PdmLog.getLogger().info(
				"Processing action for component: "
						+ event.getComponent().getId());
		FacesContext context = FacesContext.getCurrentInstance();
		TreeBean bean = (TreeBean) context.getApplication()
				.evaluateExpressionGet(context, "#{treeBean}", TreeBean.class);

		// akcja dodania noweg konceptu do kwalifikatora (po edycji)
		if (event.getComponent().getId().equals("includeBut"))
			bean.conceptConfirmed(neutralNodeFace, includedNodeFace);

		if (event.getComponent().getId().equals("indexingButton"))
			bean.changeMode();
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
			bean.recolour(elementName);
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
		}

		// usun TaxEelement z edytowanego konceptu poczynajac od kliknietego do
		// konca
		else if (event.getComponent().getId().equals("removeTaxElement")) {
			String taxElementName = event.getComponent().getAttributes().get(
					"taxElementName").toString();
			bean.cutConcept(taxElementName);
		}

		else if (event.getComponent().getId().equals("savingSearchResultButton")) {
			bean.saveSearchResult();

		}
		else if (event.getComponent().getId().equals("removeIndexingElement")) {
			Integer taxElementId = Integer.parseInt(event.getComponent().getAttributes().get(
			"elementID").toString());
			bean.removeFromSelectedTaxElements(taxElementId);
			//FIXME cos jest nie tak z odwieżaniem przy użyciu guzika usun

		}
		
		
	}

	@Override
	public void processValueChange(ValueChangeEvent arg0)
			throws AbortProcessingException {
		PdmLog.getLogger().info("Processing value change event...");
	}
}
