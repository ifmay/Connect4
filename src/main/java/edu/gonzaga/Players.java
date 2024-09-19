// Class to keep track of list of players, current player

package edu.gonzaga;

import java.util.ArrayList;
import java.util.List;

public class Players {

    public

    Integer currentPlayerIndex = 0;
    Integer indexToMod = 0;
    Integer numPlayers = 2;

    List<Player> playersList = new ArrayList<Player>();

    public Player createPlayer() {
        Player player = new Player();
        return player;
    }

    public void addPlayerToList(Player addThisPlayer) {
        playersList.add(addThisPlayer);
    }

    public void createPlayersAddToList() {
        for (int i = 0; i < numPlayers; i++) {
            addPlayerToList(createPlayer());
        }
    }

    public Player currentPlayer() {
        return playersList.get(currentPlayerIndex);
    }

    public Player nextPlayer() {
        return playersList.get(currentPlayerIndex + 1);
    }


    // switch to the next player
    public void switchPlayer() {
        indexToMod++;
        currentPlayerIndex = indexToMod % playersList.size();
    }
}