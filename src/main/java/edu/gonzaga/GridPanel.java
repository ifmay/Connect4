package edu.gonzaga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GridPanel extends JPanel {

    public Integer numRows = 7;
    public Integer numColumns = 6;

    public JButton[][] tokenButtons;
    public Token[][] tokens;
    public JButton[][] colorButtons;
    public JPanel topPanel;
    private JPanel modePanel;

    public JLabel currentPlayerLabel; // Label to display current player
    private JLabel timerLabel; // Label to display timer
    private Timer turnTimer; // Timer for each player's turn
    private int turnTimeInSeconds = 2; // Set turn time to 5 seconds
    private EndScreen endGamePanel;
    private JPanel endGamePanelContainer;

    public GridPanel(Players players, JButton[][] colorButtons, Color selectedColor1, Color selectedColor2, JPanel endGamePanelContainer, JPanel welcomePanel) {

        this.colorButtons = colorButtons; // Assign the provided colorButtons array

        setLayout(new BorderLayout()); // Use BorderLayout for better organization
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS)); // Vertical BoxLayout

        // Initialize and add current player label
        if (players.currentPlayer() != null && players.currentPlayer().tokenColor != null) {
            currentPlayerLabel = new JLabel("Current Player: " + getColorName(players.currentPlayer().tokenColor)); // Display current player
        } else {
            // Default to some color if currentPlayer or tokenColor is null
            currentPlayerLabel = new JLabel("Current Player: Unknown");
        }
        add(topPanel, BorderLayout.NORTH); // Add topPanel to the top of the GridPanel

        topPanel.add(currentPlayerLabel);

        // Generate token buttons with selected colors
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(numRows, numColumns)); // GridLayout for the grid


        // Generate token buttons with selected colors
        generateTokenButtons(players, selectedColor1, selectedColor2, gridPanel, welcomePanel);
        add(gridPanel, BorderLayout.CENTER); // Add the grid panel to the center of the panel

        generateTokens();
    }

    public GridPanel() {

    }

    // Method to get the name of the color
    public String getColorName(Color color) {
        if (color.equals(Color.RED)) {
            return "Red";
        } else if (color.equals(Color.YELLOW)) {
            return "Yellow";
        } else if (color.equals(Color.GREEN)) {
            return "Green";
        } else if (color.equals(Color.CYAN)) {
            return "Cyan";
        } else if (color.equals(Color.MAGENTA)) {
            return "Magenta";
        } else if (color.equals(Color.ORANGE)) {
            return "Orange";
        } else {
            return "Unknown"; // Handle if color is not recognized
        }
    }

    public void setButtonBackground(Color color) {
        for (JButton[] row : tokenButtons) {
            for (JButton button : row) {
                button.setBackground(color);
            }
        }
    }

    public void generateTokens() {
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numColumns; j++) {
                tokens[i][j] = new Token();
                tokens[i][j].tokenStatus = TokenStatusEnum.ISNOTINGRID;
            }
        }
    }

    public void generateTokenButtons(Players players, Color selectedColor1, Color selectedColor2, JPanel gridPanel, JPanel welcomePanel) {
        tokenButtons = new JButton[numRows][numColumns];
        tokens = new Token[numRows][numColumns]; // Initialize the tokens array

        // Loop through each row
        for (int i = 0; i < numRows; i++) {
            // Loop through each column
            for (int j = 0; j < numColumns; j++) {
                final int row = i;
                final int col = j;
                tokenButtons[i][j] = new JButton();

                tokenButtons[i][j].setBackground(Color.WHITE); // Set initial background color to white
                // Disable all buttons initially
                tokenButtons[i][j].setEnabled(false);

                // If it's the bottom row, enable the buttons
                if (i == numRows - 1) {
                    tokenButtons[i][j].setEnabled(true);
                }

                // ActionListener method for button clicks
                tokenButtons[i][j].addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton clickedButton = (JButton) e.getSource();
                        // Set the background color based on the currently active player's selected
                        if (players.currentPlayerIndex == 0) // if player 1
                        {
                            clickedButton.setBackground(selectedColor1);
                            tokens[row][col].tokenStatus = TokenStatusEnum.ISPLAYERONE;
                        }
                        if (players.currentPlayerIndex == 1) // if player 2
                        {
                            clickedButton.setBackground(selectedColor2);
                            tokens[row][col].tokenStatus = TokenStatusEnum.ISPLAYERTWO;
                        }

                        clickedButton.setEnabled(true);
                        System.out.println("button row " + row + " column " + col + " clicked");

                        // Check and enable the button directly above if it's in the valid range
                        if (row > 0) {
                            tokenButtons[row - 1][col].setEnabled(true);
                        }
                        // Switch to the next player
                        players.switchPlayer();
                        currentPlayerLabel
                                .setText("Current Player: " + getColorName(players.currentPlayer().tokenColor));

                        // go to end screen if someone wins
                        if (checkForFour(players)) {
                            int winningPlayerIndex = (players.currentPlayerIndex == 0) ? 1 : 0;
                            System.out.println("Four in a row!! Player " + (winningPlayerIndex + 1) + " is the winner!");
                            endGamePanel = new EndScreen(players.playersList.get(winningPlayerIndex), players);
                            if (endGamePanelContainer == null) {
                                endGamePanelContainer = new JPanel(new BorderLayout());
                            }
                            endGamePanelContainer.removeAll();
                            if(endGamePanelContainer != null)
                            {
                                endGamePanelContainer.removeAll();
                            }
                            endGamePanelContainer.add(endGamePanel, BorderLayout.CENTER);
                            welcomePanel.add(endGamePanelContainer, BorderLayout.CENTER);
                            welcomePanel.revalidate();
                            welcomePanel.repaint();
                        }
                        // welcomePanel.removeAll(); // ***************

                        // Initialize the token if it's null
                        if (tokens[row][col] == null) {
                            tokens[row][col] = new Token();
                        }
                    }
                });
                gridPanel.add(tokenButtons[i][j]); // Add button to the grid panel
            }
        }
    }

    // checks for four in a row
    boolean checkForFour(Players players) { // Pass the players object as a parameter
        // Horizontal check
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j <= numColumns - 4; j++) {
                if (checkSequence(tokens[i][j], tokens[i][j + 1], tokens[i][j + 2], tokens[i][j + 3])) {
                    removeGrid(players);
                    return true;
                }
            }
        }

        // Vertical check
        for (int i = 0; i <= numRows - 4; i++) {
            for (int j = 0; j < numColumns; j++) {
                if (checkSequence(tokens[i][j], tokens[i + 1][j], tokens[i + 2][j], tokens[i + 3][j])) {
                    removeGrid(players);
                    return true;
                }
            }
        }

        // Diagonal check (top-left to bottom-right)
        for (int i = 0; i <= numRows - 4; i++) {
            for (int j = 0; j <= numColumns - 4; j++) {
                if (checkSequence(tokens[i][j], tokens[i + 1][j + 1], tokens[i + 2][j + 2], tokens[i + 3][j + 3])) {
                    removeGrid(players);
                    return true;
                }
            }
        }

        // Diagonal check (top-right to bottom-left)
        for (int i = 0; i <= numRows - 4; i++) {
            for (int j = numColumns - 1; j >= 3; j--) {
                if (checkSequence(tokens[i][j], tokens[i + 1][j - 1], tokens[i + 2][j - 2], tokens[i + 3][j - 3])) {
                    removeGrid(players);
                    return true;
                }
            }
        }
        return false;
    }

    private void removeGrid(Players players) {
        // Disable all token buttons
        for (JButton[] row : tokenButtons) {
            for (JButton button : row) {
                button.setEnabled(false);
            }
        }

        // Disable all token buttons
        for (JButton[] row : tokenButtons) {
            for (JButton button : row) {
                button.setEnabled(false);
            }
        }

        // Set the background color of the screen to the winner's color
        Color winnerColor = players.currentPlayer().tokenColor;
        setButtonBackground(winnerColor);

        // Remove the grid panel from its parent container
        Container parent = getParent();
        if (parent != null) {
            parent.remove(this);
            parent.revalidate();
            parent.repaint();
        }
    }

    public Boolean checkSequence(Token... tokens) {
        TokenStatusEnum currentStatus = null;

        // Count consecutive tokens with the same status
        int count = 0;

        for (Token token : tokens) {
            // If the token is not in the grid or its status is different from the current status, reset count
            if (token.tokenStatus == TokenStatusEnum.ISNOTINGRID || token.tokenStatus != currentStatus) {
                count = 1; // Reset count and update the current status
                currentStatus = token.tokenStatus;
            } else {
                count++; // Increment count for consecutive tokens with the same status
            }

            // If four consecutive tokens of the same status are found, return true
            if (count == 4 && currentStatus != TokenStatusEnum.ISNOTINGRID) {
                return true;
            }
        }

        return false; // Return false if four consecutive tokens are not found
    }
}