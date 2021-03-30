package ui.gui.view.panel;


import javax.swing.*;
import java.awt.*;



/*

TestCardPanel represents the test cards screen when the user starts the testing flash cards

It shows the user the front side of the card with a question
It presents the option of show answer
When the answer is shown
it presents the options of whether the user got it correct or incorrect.
If the user gets the card correct, the card proceeds to the next box.
If the user gets the card incorrect, the card moves back one box.


 */

public class TestCardPanel extends JPanel {

    //card orientation panel at the top of the test card screen
    // tells the users whether the card shows front or back side
    private JPanel cardOrientationLabelPanel;
    private JLabel cardOrientationLabel;

    //card panel where the question is shown first. When user presses show answer button, the answer is shown
    private JPanel cardPanel;
    private JTextArea cardArea;

    //front button panel is shown when card orientation is front
    private JPanel frontButtonPanel;
    private JButton showAnswerButton;

    //back button panel is shown when card orientation is back
    private JPanel backButtonPanel;
    private JButton gotCorrectButton;
    private JButton gotWrongButton;

    //result button panel is shown when done testing
    private JPanel resultButtonPanel;
    private JButton goBackToMainMenuButton;

    //REQUIRES: X
    //EFFECTS: constructs structure for test card screen
    public TestCardPanel() {
        //test card panel
        setLayout(new BorderLayout());


        //filler panel for left and right
        JPanel fillerLeftPanel = new JPanel();
        JPanel fillerRightPanel = new JPanel();
        add(fillerLeftPanel, BorderLayout.WEST);
        add(fillerRightPanel, BorderLayout.EAST);


        //test card panel has three panels at one time:
        //cardOrientationLabelPanel , card panel and front button panel
        // or cardOrientationLabelPanel  card panel and back button panel

        //cardOrientationLabelPanel panel
        initCardOrientationLabelPanel();
        //card panel
        initCardPanel();
        //front button panel
        initFrontButtonPanel();
        //back button panel
        initBackButtonPanel();
//        add( backButtonPanel, BorderLayout.SOUTH);
        //results button panel
        initResultsButtonPanel();



    }





    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes the label at the top of the card test screen
    private void initCardOrientationLabelPanel() {
        cardOrientationLabelPanel = new JPanel();
        cardOrientationLabelPanel.setLayout(new FlowLayout());
        cardOrientationLabel = new JLabel("Front");
        cardOrientationLabel.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        cardOrientationLabelPanel.add(cardOrientationLabel);
        add(cardOrientationLabelPanel, BorderLayout.NORTH);


    }


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes the card area (where question and answer is presented) in the middle of the card test screen
    private void initCardPanel() {

        cardPanel = new JPanel();
        cardPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        cardArea = new JTextArea();
        cardArea.setEditable(false);
        cardArea.setBackground(null);
        cardArea.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
        cardArea.setText("Sample text");

        cardPanel.add(cardArea, gbc);
        add(cardPanel, BorderLayout.CENTER);

    }

    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes the buttons at the bottom of the card test screen when front side of the card is showing
    private void initFrontButtonPanel() {
        frontButtonPanel = new JPanel();
        frontButtonPanel.setLayout(new FlowLayout());
        showAnswerButton = new JButton("Show Answer");
        frontButtonPanel.add(showAnswerButton);
        add(frontButtonPanel, BorderLayout.SOUTH);
    }

    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes the buttons at the bottom of the card test screen when back side of the card is showing
    private void initBackButtonPanel() {
        backButtonPanel = new JPanel();
        backButtonPanel.setLayout(new FlowLayout());
        gotWrongButton = new JButton("Got wrong");
        gotCorrectButton = new JButton("Got right");
        backButtonPanel.add(gotWrongButton);
        backButtonPanel.add(gotCorrectButton);
    }

    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes the buttons at the bottom of the card test screen when card testing is done.
    private void initResultsButtonPanel() {
        resultButtonPanel = new JPanel();
        goBackToMainMenuButton = new JButton("Go back to main menu");
        resultButtonPanel.add(goBackToMainMenuButton);
    }

    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: switches from button (show answer) for front side of card to buttons (correct or incorrect)
    // buttons for back side of card.
    public void switchBackPanel() {
        frontButtonPanel.setVisible(false);
        backButtonPanel.setVisible(true);
        resultButtonPanel.setVisible(false);

        add(backButtonPanel, BorderLayout.SOUTH);
    }

    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: switches from buttons (correct or incorrect)
    //buttons for back side of card to button (show answer) for front side of card
    public void switchFrontPanel() {
        backButtonPanel.setVisible(false);
        frontButtonPanel.setVisible(true);
        resultButtonPanel.setVisible(false);

        add(frontButtonPanel, BorderLayout.SOUTH);
    }

    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: when testing is done, switch to the button (go back to the main menu)
    public void switchResultPanel() {
        backButtonPanel.setVisible(false);
        frontButtonPanel.setVisible(false);
        resultButtonPanel.setVisible(true);

        add(resultButtonPanel, BorderLayout.SOUTH);
    }




    //getters for initialization and button action implementation-----------------------------------------
    public JLabel getCardOrientationLabel() {
        return cardOrientationLabel;
    }

    public JTextArea getCardArea() {
        return cardArea;
    }

    public JButton getShowAnswerButton() {
        return showAnswerButton;
    }

    public JButton getGotCorrectButton() {
        return gotCorrectButton;
    }

    public JButton getGotWrongButton() {
        return gotWrongButton;
    }

    public JButton getGoBackToMainMenuButton() {
        return goBackToMainMenuButton;
    }



}
