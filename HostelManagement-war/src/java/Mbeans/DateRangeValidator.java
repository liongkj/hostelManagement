/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import org.primefaces.context.RequestContext;
import sun.security.validator.ValidatorException;

@FacesValidator(value = "dateRangeValidator")
public class DateRangeValidator implements Validator {

    @Override
    public void validate(FacesContext context,
            UIComponent uiComponent, Object o) {
        UIInput startDateInput = (UIInput) uiComponent.getAttributes().get("startDateAttr");
        Date startDate = (Date) startDateInput.getValue();
        Date endDate = (Date) o;

        if (endDate.before(startDate)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid date selected","Start date cannot be after end date."));

        } else {
            RequestContext c = RequestContext.getCurrentInstance();
            c.execute("PF('dateRangeDlg').hide();");
            c.execute("location.reload();");
        }
    }
}
