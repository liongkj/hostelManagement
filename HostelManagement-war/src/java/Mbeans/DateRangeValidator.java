/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mbeans;

import java.time.LocalDate;
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

        if (endDate.compareTo(startDate) == 0) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid date selected", "Check In date cannot be same with Check Out date."));

            if (endDate.before(startDate)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Invalid date selected", "Check In date cannot be after Check Out date."));

            }
        } else {
            RequestContext c = RequestContext.getCurrentInstance();
            c.execute("PF('dateRangeDlg').hide();");
//            c.execute("location.reload();");
        }

    }
}
