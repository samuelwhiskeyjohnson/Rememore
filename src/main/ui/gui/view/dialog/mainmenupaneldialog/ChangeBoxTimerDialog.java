package ui.gui.view.dialog.mainmenupaneldialog;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.text.NumberFormat;

public class ChangeBoxTimerDialog extends JDialog {

    //Enter minutes panel
    private JPanel changeMinutesPanel;
    private JLabel enterMinutesLabel;
    private JFormattedTextField changeBoxTimerField;


    //Button panel
    private JPanel buttonPanel;
    private JButton changeTimerButton;
    private JButton cancelButton;



    //REQUIRES: X
    //EFFECTS: constructs pop up modal dialog when user presses change box timer button in the main menu screen
    public ChangeBoxTimerDialog(JFrame frame) {
        super(frame, "CardBox Timer Setting", true);
//        setLayout(new FlowLayout());
        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        initChangeMinutesPanel();
        initButtonPanel();

        pack();
//        setBounds(100, 100, 300, 100);
        setLocationRelativeTo(null);


    }


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes changeMinutesPanel for users to type in their desired timer for the box selected
    private void initChangeMinutesPanel() {
        changeMinutesPanel = new JPanel();
        enterMinutesLabel = new JLabel("Enter the minutes: ");
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(1440); // 24 hours
        formatter.setAllowsInvalid(true);

        // If you want the value to be committed on each keystroke instead of focus lost
        formatter.setCommitsOnValidEdit(true);
        changeBoxTimerField = new JFormattedTextField(formatter);
        changeBoxTimerField.setText("10");
        changeBoxTimerField.setColumns(10);
        changeMinutesPanel.add(enterMinutesLabel);
        changeMinutesPanel.add(changeBoxTimerField);
    }

    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes buttonPanel for users to either confirm change box timer or change box timer
    private void initButtonPanel() {
        buttonPanel = new JPanel();
        cancelButton = new JButton("Cancel");
        changeTimerButton = new JButton("Confirm change");
        buttonPanel.add(cancelButton);
        buttonPanel.add(changeTimerButton);


        add(changeMinutesPanel);
        add(buttonPanel);
    }







    //getters for initialization and button action implementation-----------------------------------------


    public JButton getChangeTimerButton() {
        return changeTimerButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }



    public JFormattedTextField getChangeBoxTimerField() {
        return changeBoxTimerField;
    }
}