package ui.gui.view.dialog.cardboxpaneldialog;

import javax.swing.*;

public class RemoveCardDialog extends JDialog {


    //identify which card to remove
    private JPanel identifyCardToRemovePanel;
    private JLabel identifyCardToRemoveLabel;
    private JTextField identifyCardToRemoveTextField;

    //button panel
    private JPanel buttonPanel;
    private JButton removeButton;    //confirm move button
    private JButton cancelButton;    //cancel button

    //REQUIRES: X
    //EFFECTS: constructs pop up modal dialog when user presses remove card button in the card box screen
    public RemoveCardDialog(JFrame frame) {
        //true modal false modeless.
        //modal dialog makes users unable to control other windows until dealt with dialog
        super(frame, "Moved selected card", true);


        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        //initialize panels
        initWhichCardToRemovePanel();
        initButtonPanel();

        add(identifyCardToRemovePanel);
        add(buttonPanel);

        pack();
//        setBounds(100, 100, 500, 150);
        setLocationRelativeTo(null);


    }



    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes identifyCardToRemovePanel for users to type in the cardID for the card they wish to remove
    private void initWhichCardToRemovePanel() {
        identifyCardToRemovePanel = new JPanel();
        identifyCardToRemoveLabel = new JLabel("Enter ID of the card you wish to remove");
        identifyCardToRemoveTextField = new JTextField();
        identifyCardToRemoveTextField.setColumns(2);
        identifyCardToRemovePanel.add(identifyCardToRemoveLabel);
        identifyCardToRemovePanel.add(identifyCardToRemoveTextField);


    }


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes buttonPanel for users to either confirm removing card or cancel removing card.
    private void initButtonPanel() {

        buttonPanel = new JPanel();
        removeButton = new JButton("Remove");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(removeButton);
        buttonPanel.add(cancelButton);

    }

    //getters for initialization and button action implementation-----------------------------------------


    public JTextField getIdentifyCardToRemoveTextField() {
        return identifyCardToRemoveTextField;
    }

    public JButton getRemoveButton() {
        return removeButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
