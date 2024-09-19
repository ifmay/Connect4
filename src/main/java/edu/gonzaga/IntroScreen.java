package edu.gonzaga;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IntroScreen {
    private JFrame frame;
    private JPanel welcomePanel;
    private JPanel colorChoicesPanel;
    private JPanel modePanel;
    private JPanel headerPanel;
    private JLabel welcomeLabel;
    private JLabel colorChoiceLabel;

    private JPanel normalGridPanelContainer;
    private JPanel bigGridPanelContainer;
    private JPanel timerGridPanelContainer;
    private JPanel endGamePanelContainer;
    JButton endButton = new JButton("X");
    public static JButton[][] colorButtons; // Array to store color buttons
    private Color selectedColor1;
    private Color selectedColor2;

    private GridPanel normalGridPanel;
    private GridPanel2 bigGridPanel;
    private GridPanel3 timerGridPanel;
    private EndScreen endGamePanel;

    public IntroScreen(Players players) {

        frame = new JFrame("Connect Four");
        welcomePanel = new JPanel(new BorderLayout());
        colorChoicesPanel = new JPanel();
        modePanel = new JPanel();

        normalGridPanel = new GridPanel(players, colorButtons, selectedColor1, selectedColor2, endGamePanelContainer, welcomePanel);

        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        welcomePanel.setLayout(new GridLayout(0, 1));

        welcomeLabel = new JLabel("Welcome to Connect Four", JLabel.CENTER); // Initialize the welcome label
        welcomeLabel.setFont(new Font("Roboto Condensed", Font.BOLD, 35));
        welcomePanel.add(welcomeLabel); // Add the welcome label to the panel

        colorChoiceLabel = new JLabel("Player 1 choose your token color: ", JLabel.CENTER);
        colorChoicesPanel.add(colorChoiceLabel);

        JButton startButton = new JButton("Start Game");
        JButton color1Button = new JButton("Red");
        JButton color2Button = new JButton("Yellow");
        JButton color3Button = new JButton("Green");
        JButton color4Button = new JButton("Blue");
        JButton color5Button = new JButton("Purple");
        JButton color6Button = new JButton("Orange");

        // Initialize colorButtons array
        colorButtons = new JButton[1][6];
        colorButtons[0][0] = color1Button;
        colorButtons[0][1] = color2Button;
        colorButtons[0][2] = color3Button;
        colorButtons[0][3] = color4Button;
        colorButtons[0][4] = color5Button;
        colorButtons[0][5] = color6Button;

        JButton timerModeButton = new JButton("Timer Mode");
        JButton biggerGridButton = new JButton("Bigger Grid");

        // Setting the button colors
        setColor(startButton, Color.BLUE);
        setColor(color1Button, Color.RED);
        setColor(color2Button, Color.YELLOW);
        setColor(color3Button, Color.GREEN);
        setColor(color4Button, Color.CYAN);
        setColor(color5Button, Color.MAGENTA);
        setColor(color6Button, Color.ORANGE);

        setColor(timerModeButton, Color.PINK);
        setColor(biggerGridButton, Color.PINK);

        startButton.setFont(new Font("Roboto Condensed", Font.BOLD, 25));
        color1Button.setFont(new Font("Roboto Condensed", Font.BOLD, 20));
        color2Button.setFont(new Font("Roboto Condensed", Font.BOLD, 20));
        color3Button.setFont(new Font("Roboto Condensed", Font.BOLD, 20));
        color4Button.setFont(new Font("Roboto Condensed", Font.BOLD, 20));
        color5Button.setFont(new Font("Roboto Condensed", Font.BOLD, 20));
        color6Button.setFont(new Font("Roboto Condensed", Font.BOLD, 20));

        timerModeButton.setFont(new Font("Roboto Condensed", Font.BOLD, 20));
        biggerGridButton.setFont(new Font("Roboto Condensed", Font.BOLD, 20));

        // Add action listeners for color buttons
        for (JButton[] row : colorButtons) {
            for (JButton button : row) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Color selectedColor = button.getBackground(); // Get the selected color
                        if (players.currentPlayerIndex == 0) {
                            selectedColor1 = selectedColor;
                            // setTokenColor(players.playersList.get(0), getColorName(selectedColor1)); //
                            // Update token color for player1
                            setTokenColor(players.currentPlayer(), selectedColor); // Update token color for player1
                            // players.switchPlayer();
                            colorChoiceLabel.setText("Player 2 choose your token color: ");
                        } else {
                            selectedColor2 = selectedColor;
                            // setTokenColor(players.playersList.get(1), getColorName(selectedColor2)); //
                            // Update token color for player2
                            setTokenColor(players.currentPlayer(), selectedColor); // Update token color for player1
                            // players.switchPlayer(); // Switch back to player 1
                            colorChoiceLabel.setText("Player 1 choose your token color: ");
                            // Update only the buttons in the grid with the selected color for player 2
                            normalGridPanel.setButtonBackground(selectedColor2);
                        }
                        players.switchPlayer();
                        // normalGridPanel.currentPlayerLabel.setText("Current Player: " +
                        // players.currentPlayer().tokenColor.toString());
                    }
                });
            }
        }

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove the welcome label if it exists
                if (welcomeLabel != null) {
                    welcomePanel.remove(welcomeLabel);
                    welcomeLabel = null; // Reset the welcome label
                }

                colorChoicesPanel.remove(color1Button);
                colorChoicesPanel.remove(color2Button);
                colorChoicesPanel.remove(color3Button);
                colorChoicesPanel.remove(color4Button);
                colorChoicesPanel.remove(color5Button);
                colorChoicesPanel.remove(color6Button);
                colorChoicesPanel.remove(colorChoiceLabel);

                modePanel.remove(timerModeButton);
                modePanel.remove(biggerGridButton);

                // Ensure normalGridPanelContainer is initialized before using it
                if (normalGridPanelContainer == null) {
                    normalGridPanelContainer = new JPanel(new BorderLayout());
                }

                // Create the game grid panel
                normalGridPanel = new GridPanel(players, colorButtons, selectedColor1, selectedColor2, endGamePanelContainer, welcomePanel);
                normalGridPanelContainer.removeAll();
                normalGridPanelContainer.add(normalGridPanel, BorderLayout.CENTER);

                // Clear the panel and add the grid panel
                welcomePanel.removeAll();
                welcomePanel.add(normalGridPanelContainer, BorderLayout.CENTER); // Add the grid panel to the main panel

                endButton.setFont(new Font("Roboto Condensed", Font.BOLD, 10)); //adding end button
                endButton.setPreferredSize(new Dimension(42, 42));
                setColor(endButton, Color.RED);

                headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                headerPanel.add(endButton);
                frame.add(headerPanel, BorderLayout.NORTH);

                // Revalidate and repaint the panel to reflect the changes
                welcomePanel.revalidate();
                welcomePanel.repaint();
            }
        });

        timerModeButton.addActionListener(e -> {
            welcomePanel.removeAll();

            colorChoicesPanel.remove(color1Button);
            colorChoicesPanel.remove(color2Button);
            colorChoicesPanel.remove(color3Button);
            colorChoicesPanel.remove(color4Button);
            colorChoicesPanel.remove(color5Button);
            colorChoicesPanel.remove(color6Button);
            colorChoicesPanel.remove(colorChoiceLabel);

            modePanel.remove(timerModeButton);
            modePanel.remove(biggerGridButton);

            // Ensure TimerPanelbigContainer is initialized before using it
            if (timerGridPanelContainer == null) {
                timerGridPanelContainer = new JPanel(new BorderLayout());
            }

            timerGridPanel = new GridPanel3(players, colorButtons, selectedColor1, selectedColor2, welcomePanel);

            timerGridPanelContainer.removeAll();
            timerGridPanelContainer.add(timerGridPanel, BorderLayout.CENTER);
            endButton.setFont(new Font("Roboto Condensed", Font.BOLD, 10));
            endButton.setPreferredSize(new Dimension(42, 42));
            setColor(endButton, Color.RED);
            headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            headerPanel.add(endButton); //adding end button
            frame.add(headerPanel, BorderLayout.NORTH);

            welcomePanel.add(timerGridPanelContainer, BorderLayout.CENTER);
            welcomePanel.revalidate();
            welcomePanel.repaint();

        });

        biggerGridButton.addActionListener(e -> {
            welcomePanel.removeAll();

            colorChoicesPanel.remove(color1Button);
            colorChoicesPanel.remove(color2Button);
            colorChoicesPanel.remove(color3Button);
            colorChoicesPanel.remove(color4Button);
            colorChoicesPanel.remove(color5Button);
            colorChoicesPanel.remove(color6Button);
            colorChoicesPanel.remove(colorChoiceLabel);

            modePanel.remove(timerModeButton);
            modePanel.remove(biggerGridButton);

            // Ensure bigGridPanelContainer is initialized before using it
            if (bigGridPanelContainer == null) {
                bigGridPanelContainer = new JPanel(new BorderLayout());
            }

            bigGridPanel = new GridPanel2(players, colorButtons, selectedColor1, selectedColor2, welcomePanel);

            bigGridPanelContainer.removeAll();
            bigGridPanelContainer.add(bigGridPanel, BorderLayout.CENTER);
            endButton.setFont(new Font("Roboto Condensed", Font.BOLD, 10));
            endButton.setPreferredSize(new Dimension(42, 42));
            setColor(endButton, Color.RED);
            headerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            headerPanel.add(endButton); //adding end button
            frame.add(headerPanel, BorderLayout.NORTH);
            welcomePanel.add(bigGridPanelContainer, BorderLayout.CENTER);
            welcomePanel.revalidate();
            welcomePanel.repaint();

        });

        //Start to end button action listener
        // Inside the action listener for the end button in IntroScreen class
        endButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Remove all components from the welcome panel
                welcomePanel.removeAll();

                // Remove the header panel
                headerPanel.removeAll();

                // Create a new end screen panel
                EndScreen endGamePanel;

                endGamePanel = new EndScreen(null, null); // Pass null parameters
                endGamePanel.setupNoWinnerScreen();

                // Create a new end game panel container if it's not already initialized
                if (endGamePanelContainer == null) {
                    endGamePanelContainer = new JPanel(new BorderLayout());
                }

                // Remove all components from the end game panel container
                endGamePanelContainer.removeAll();

                // Add the end screen panel to the end game panel container
                endGamePanelContainer.add(endGamePanel, BorderLayout.CENTER);

                // Add the end game panel container to the welcome panel
                welcomePanel.add(endGamePanelContainer, BorderLayout.CENTER);

                // Get the screen size
                Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

                // Set the size of the end game panel container to match the screen size
                endGamePanelContainer.setPreferredSize(screenSize);

                // Revalidate and repaint the welcome panel to reflect the changes
                welcomePanel.revalidate();
                welcomePanel.repaint();
            }
        });


        welcomePanel.add(startButton);
        colorChoicesPanel.add(color1Button);
        colorChoicesPanel.add(color2Button);
        colorChoicesPanel.add(color3Button);
        colorChoicesPanel.add(color4Button);
        colorChoicesPanel.add(color5Button);
        colorChoicesPanel.add(color6Button);

        modePanel.add(timerModeButton);
        modePanel.add(biggerGridButton);

        frame.add(welcomePanel, BorderLayout.CENTER);
        frame.add(colorChoicesPanel, BorderLayout.SOUTH);
        frame.add(modePanel, BorderLayout.NORTH);
        frame.setSize(800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void setFrameVisibility() {
        frame.setVisible(true);
    }

    public void setTokenColor(Player player, Color color) {
        player.tokenColor = color;
    }

    // sets the color for the button
    private void setColor(JButton button, Color buttonColor) {
        button.setBackground(buttonColor);
        button.setOpaque(true);
        // changes button text color based on color brightness
        if (isBright(buttonColor)) {
            button.setForeground(Color.BLACK);
        } else {
            button.setForeground(Color.WHITE);
        }
    }

    // checks color brightness
    private boolean isBright(Color color) {
        double brightness = (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue()) / 255;
        return brightness > 0.5;
    }
}