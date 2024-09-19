package edu.gonzaga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EndScreen extends JPanel {
    private JLabel endMessage = new JLabel();

    // Constructor for both cases - with winner and without winner
    public EndScreen(Player winner, Players players) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Use BoxLayout for vertical alignment
        // Check if there is a winner
        if (winner != null) {
            setupWinnerScreen(players, winner);
        } else {
            setupNoWinnerScreen();
        }

        // Add some vertical space between components
        add(Box.createVerticalStrut(20));

        // Check if players is not null before creating the "Play Again" button
        if (players != null) {
            add(createPlayAgainButton(players));
        }
    }

    // Method to set up the screen for the winner
    private void setupWinnerScreen(Players players, Player winner) {
        endMessage.setFont(new Font("Roboto Condensed", Font.BOLD, 25));
        endMessage.setText("CONGRATULATIONS PLAYER " + (players.currentPlayerIndex + 1) + ", YOU WIN!!");
        endMessage.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the text
        setBackground(winner.tokenColor); // Set background color to the winning player's color

        // Add the endMessage label to the panel
        add(endMessage);
    }

    // Method to set up the screen when there is no winner
    public void setupNoWinnerScreen() {
        endMessage.setFont(new Font("Roboto Condensed", Font.BOLD, 30));
        endMessage.setText("GAME ENDED, NO WINNER");
        endMessage.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the text
        setBackground(Color.WHITE); // Set default background color

        // Add the endMessage label to the panel
        add(endMessage);
    }

    // Method to create the "Play Again" button
    private JButton createPlayAgainButton(Players players) {
        JButton playAgainButton = new JButton("Play Again");
        playAgainButton.setFont(new Font("Roboto Condensed", Font.BOLD, 25)); // Set font size
        playAgainButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center-align the button

        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current EndScreen window
                SwingUtilities.getWindowAncestor(EndScreen.this).dispose();

                // Open the IntroScreen again
                IntroScreen introScreen = new IntroScreen(players);
                introScreen.setFrameVisibility();
            }
        });
        return playAgainButton;
    }
}