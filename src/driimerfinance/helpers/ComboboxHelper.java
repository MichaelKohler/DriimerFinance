package driimerfinance.helpers;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * Helper for the different comboboxes we use.
 * 
 * (C) 2014 Driimer Finance
 *
 */
public class ComboboxHelper extends JLabel implements ListCellRenderer {

	// set the content of the cell
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Object[] itemData = (Object[])value;
        setText((String)itemData[1]);
        return this;
    }
}
