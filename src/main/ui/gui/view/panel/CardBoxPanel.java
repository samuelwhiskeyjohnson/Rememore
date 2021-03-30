package ui.gui.view.panel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;



/*
CardBoxPanel represents the screen showing table of cards in a selected card box

It presents the options of
going back to main menu
adding card in the selected box
removing card in the selected box
modify card in the selected box
moving card in the selected box to another box

 */

public class CardBoxPanel extends JPanel {

    //buttons panel at the top
    private JPanel buttonsPanel;
    private JLabel boxIndicator;
    private JButton backToMainMenuButton;
    private JButton addCardButton;
    private JButton removeCardButton;
    private JButton modifyCardButton;
    private JButton moveCardToAnotherBoxButton;

    //table panel in the middle of card box screen
    private JPanel tablePanel;
    private DefaultTableModel cardTableModel;
    private JTable cardTable;

    //REQUIRES: X
    //EFFECTS: constructs structure for card box screen
    public CardBoxPanel() {
        setLayout(new BorderLayout());
        //CardBox panel has two panels: table panel and buttons panel
        initButtonsPanel();

        initTablePanel();


    }

    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes buttons on the card box screen
    private void initButtonsPanel() {
        //buttons panel
        buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        boxIndicator = new JLabel("BOX");
        backToMainMenuButton = new JButton("Back");
        addCardButton = new JButton("Add Card");
        removeCardButton = new JButton("Remove card");
        modifyCardButton = new JButton("Modify card");
        moveCardToAnotherBoxButton = new JButton("Move card to another box");
        buttonsPanel.add(boxIndicator);
        buttonsPanel.add(backToMainMenuButton);
        buttonsPanel.add(addCardButton);
        buttonsPanel.add(removeCardButton);
        buttonsPanel.add(modifyCardButton);
        buttonsPanel.add(moveCardToAnotherBoxButton);
        add(buttonsPanel, BorderLayout.NORTH);
    }


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes card table (table and table model) on card box screen
    private void initTablePanel() {
        //table panel
        tablePanel = new JPanel();
        tablePanel.setLayout(new BorderLayout());
        initCardTableModel();
        initCardTable();

    }


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes card table model to manage the data in the table on card box screen
    private void initCardTableModel() {
        //table internal data
        cardTableModel = new DefaultTableModel() {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                switch (columnIndex) {
                    case 0:
                        return Integer.class;// .class is a field from Object class. Column 0 can have integer.
                    case 1:
                    case 2:
                        return String.class;
                    case 3:
                        return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }


//            @Override
//            public boolean isCellEditable(int row, int column) {
//                int selectedRow = cardTable.getSelectedRow();
//
//                if (cardTable.isEnabled()) {
//                    return true;
//                } else {
//                    return false;
//                }
//            }
        };
        Object[] columnTitle = new Object[]{"ID", "front", "back", "card testable?"};
        cardTableModel.setColumnIdentifiers(columnTitle);
    }


    //REQUIRES: X
    //MODIFIES: this
    //EFFECTS: initializes card table on card box screen
    private void initCardTable() {
        //table external face
        cardTable = new JTable(cardTableModel);
        cardTable.setEnabled(false);

        JScrollPane cardTableScrollable = new JScrollPane(cardTable);
        tablePanel.add(cardTableScrollable, BorderLayout.CENTER);
        add(tablePanel, BorderLayout.CENTER);
    }





    //getters for initialization and button action implementation-----------------------------------------
    public DefaultTableModel getCardTableModel() {
        return cardTableModel;
    }

    public JButton getBackToMainMenuButton() {
        return backToMainMenuButton;
    }

    public JButton getAddCardButton() {
        return addCardButton;
    }

    public JButton getRemoveCardButton() {
        return removeCardButton;
    }

    public JButton getModifyCardButton() {
        return modifyCardButton;
    }

    public JButton getMoveCardToAnotherBoxButton() {
        return moveCardToAnotherBoxButton;
    }

    public JTable getCardTable() {
        return cardTable;
    }

    public void setModifiedMode() {

        // toggle enable function
        cardTable.setEnabled(!cardTable.isEnabled());

    }

    public JLabel getBoxIndicator() {
        return boxIndicator;
    }
}
