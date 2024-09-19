package edu.gonzaga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PlayersTest {

    private Players players;

    @BeforeEach
    void setUp() {
        players = new Players();
    }

    @Test
    void testCreatePlayer() {
        Player player = players.createPlayer();
        assertNotNull(player);
    }

    @Test
    void testCreatePlayersAddToList() {
        players.createPlayersAddToList();
        assertEquals(2, players.playersList.size());
    }

}
