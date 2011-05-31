package pdm.validator;

import java.io.ObjectInputStream.GetField;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class IndexingValidator implements Validator {

	@Override
	public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
			throws ValidatorException {
		System.err.println("v");
		// TODO Auto-generated method stub
		

	}

}
