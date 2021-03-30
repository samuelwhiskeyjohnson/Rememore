package ui.gui.view.dialog.cardboxpaneldialog;

import javax.swing.*;

public class MoveCardToDifferentBoxDialog extends JDialog  {

    //identify which card to modify
    private JPanel identifyCardToMovePanel;
    private JLabel identifyCardToMoveLabel;
    private JTextField identifyCardToMoveTextField;


    //move card to which box panel
    private JPanel moveCardPanel;
    private JLabel moveCardLabel;
    private JTextField moveToDifferentBoxTextField;



    //button panel
    private JPanel buttonPanel;
    private JButton moveButton;    //confirm move button
    private JButton cancelButton;    //cancel button


    //REQUIRES: X
    //EFFECTS: constructs pop up modal dialog when user presses move card button in the card box screen
    public MoveCardToDifferentBoxDialog(JFrame frame) {
        //true modal false modeless.
        //modal dialog makes users unable to control other windows until dealt with dialog
        super(frame, "Moved selected card", true);

        setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));

        //initialize panels
        initWhichCardToMovePanel();
        initMoveCardPanel();
        initButtonPanel();

        add(identifyCardToMovePanel);
        add(moveCardPanel);
        add(buttonPanel);

        pack();
//        setBounds(100, 100, 500, 150);
        setLocationRelativeTo(null);


    }

    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes identifyCardToMovePanel for users to type in card ID for a card they desire to move to
    //different box
    private void initWhichCardToMovePanel() {
        identifyCardToMovePanel = new JPanel();
        identifyCardToMoveLabel = new JLabel("Enter ID of the card you wish to move");
        identifyCardToMoveTextField = new JTextField();
        identifyCardToMoveTextField.setColumns(2);
        identifyCardToMovePanel.add(identifyCardToMoveLabel);
        identifyCardToMovePanel.add(identifyCardToMoveTextField);

    }

    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes moveCardPanel for users to type in the box number they wish the card to move to
    private void initMoveCardPanel() {
        moveCardPanel = new JPanel();
        moveCardLabel = new JLabel("Move to which box?");
        moveToDifferentBoxTextField = new JTextField();
        moveToDifferentBoxTextField.setColumns(2);
        moveCardPanel.add(moveCardLabel);
        moveCardPanel.add(moveToDifferentBoxTextField);

    }

    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes buttonPanel for users to either confirm moving card or cancel moving card.
    private void initButtonPanel() {
        buttonPanel = new JPanel();
        moveButton = new JButton("Move");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(moveButton);
        buttonPanel.add(cancelButton);
    }

    //getters for initialization and button action implementation-----------------------------------------


    public JTextField getIdentifyCardToMoveTextField() {
        return identifyCardToMoveTextField;
    }

    public JTextField getMoveToDifferentBoxTextField() {
        return moveToDifferentBoxTextField;
    }

    public JButton getMoveButton() {
        return moveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }
}
