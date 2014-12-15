package driimerfinance.gui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.swing.JFormattedTextField.AbstractFormatter;

/**
 * Creates a DateLabelFormatter instance to format the given date to a user-readble format.
 * 
 * (C) 2014 Driimer Finance
 */
public class DateLabelFormatter extends AbstractFormatter {

	private static final long serialVersionUID = 885389720578704977L;
	private String datePattern = "dd.MM.yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
}