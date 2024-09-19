package edu.gonzaga;

/**
 * Final project main driver class
 *
 * Project Description: Two player Connect 4 game with settings to change the colors, grid, and allotted time.
 *
 * Contributors: Lindsey Bodenbender, Izzy May, Zobe Murray
 *
 * Copyright: 2024
 */

public class MainGame {
    public static void main(String[] args) {

        Players players = new Players();
        players.createPlayersAddToList();
        IntroScreen introScreen = new IntroScreen(players);
        introScreen.setFrameVisibility();
    }
}