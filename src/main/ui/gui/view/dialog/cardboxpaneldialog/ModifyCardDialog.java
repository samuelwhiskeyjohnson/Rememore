package ui.gui.view.dialog.cardboxpaneldialog;

import javax.swing.*;

public class ModifyCardDialog extends JDialog {

    //identify which card to modify
    private JPanel identifyCardToModifyPanel;
    private JLabel identifyCardToModifyLabel;
    private JTextField identifyCardToModifyTextField;


    //overwrite front info
    private JPanel overwriteFrontInfoPanel;
    private JLabel overwriteFrontInfoLabel;
    private JTextField overWriteFrontInfoTextField;


    //overwrite back info
    private JPanel overwriteBackInfoPanel;
    private JLabel overwriteBackInfoLabel;
    private JTextField overwriteBackInfoTextField;

    //button panel
    private JPanel buttonPanel;
    private JButton modifyButton;    //confirm modify button
    private JButton cancelButton;    //cancel button


    //REQUIRES: X
    //EFFECTS: constructs pop up modal dialog when user presses modify card button in the card box screen
    public ModifyCardDialog(JFrame frame) {
        //true modal false modeless.
        //modal dialog makes users unable to control other windows until dealt with dialog
        super(frame, "Modify selected card", true);

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        //initialize panels
        initWhichCardToModifyPanel();
        initFrontOverWritePanel();
        initBackOverwritePanel();
        initButtonPanel();

        add(identifyCardToModifyPanel);
        add(overwriteFrontInfoPanel);
        add(overwriteBackInfoPanel);
        add(buttonPanel);

        pack();
//        setBounds(100, 100, 500, 150);
        setLocationRelativeTo(null);


    }


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes identifyCardToModifyPanel for users to type in card ID for a card they desire to change
    // its content for the box selected
    private void initWhichCardToModifyPanel() {
        identifyCardToModifyPanel = new JPanel();
        identifyCardToModifyLabel = new JLabel("Enter ID of the card you wish to modify");
        identifyCardToModifyTextField = new JTextField();
        identifyCardToModifyTextField.setColumns(2);
        identifyCardToModifyPanel.add(identifyCardToModifyLabel);
        identifyCardToModifyPanel.add(identifyCardToModifyTextField);

    }


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes overwriteFrontInfoPanel for users to type in the front content of the card
    // they wish to overwrite
    private void initFrontOverWritePanel() {
        overwriteFrontInfoPanel = new JPanel();
        overwriteFrontInfoLabel = new JLabel("Overwrite the question: ");
        overWriteFrontInfoTextField = new JTextField();
        overWriteFrontInfoTextField.setColumns(30);
        overwriteFrontInfoPanel.add(overwriteFrontInfoLabel);
        overwriteFrontInfoPanel.add(overWriteFrontInfoTextField);

    }


    //REQUIRES: X
    //MODIFIES: initializes overwriteBackInfoPanel
    //EFFECTS: initializes overwriteBackInfoPanel for users to type in the back content of the card
    // they wish to overwrite
    private void initBackOverwritePanel() {
        overwriteBackInfoPanel = new JPanel();
        overwriteBackInfoLabel = new JLabel("Overwrite the answer: ");
        overwriteBackInfoTextField = new JTextField();
        overwriteBackInfoTextField.setColumns(30);
        overwriteBackInfoPanel.add(overwriteBackInfoLabel);
        overwriteBackInfoPanel.add(overwriteBackInfoTextField);

    }


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes buttonPanel for users to either confirm modifying card or cancel adding card.
    private void initButtonPanel() {
        buttonPanel = new JPanel();
        modifyButton = new JButton("Modify");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(modifyButton);
        buttonPanel.add(cancelButton);
    }

    //getters for initialization and button action implementation-----------------------------------------


    public JTextField getIdentifyCardToModifyTextField() {
        return identifyCardToModifyTextField;
    }

    public JTextField getOverWriteFrontInfoTextField() {
        return overWriteFrontInfoTextField;
    }

    public JTextField getOverwriteBackInfoTextField() {
        return overwriteBackInfoTextField;
    }

    public JButton getModifyButton() {
        return modifyButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
