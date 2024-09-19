package edu.gonzaga;

import javax.swing.*;
import java.awt.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class GridPanelTest {

    // Test Color Recognition
    @Test
    public void testColorRecognition() {
        GridPanel gridPanel = new GridPanel();
        assertEquals("Red", gridPanel.getColorName(Color.RED));
    }

    @Test
    public void testColorName() {
        GridPanel gridPanel = new GridPanel();
        assertEquals("Red", gridPanel.getColorName(Color.RED));
        assertEquals("Yellow", gridPanel.getColorName(Color.YELLOW));
        assertEquals("Green", gridPanel.getColorName(Color.GREEN));
        assertEquals("Cyan", gridPanel.getColorName(Color.CYAN));
        assertEquals("Magenta", gridPanel.getColorName(Color.MAGENTA));
        assertEquals("Orange", gridPanel.getColorName(Color.ORANGE));
        assertEquals("Unknown", gridPanel.getColorName(Color.WHITE)); // Test unknown color
    }

    // Test Four-in-a-Row Detection
    @Test
    public void testCheckSequence() {
        GridPanel gridPanel = new GridPanel();
        Token token1 = new Token();
        Token token2 = new Token();
        Token token3 = new Token();
        Token token4 = new Token();
        token1.tokenStatus = TokenStatusEnum.ISPLAYERONE;
        token2.tokenStatus = TokenStatusEnum.ISPLAYERONE;
        token3.tokenStatus = TokenStatusEnum.ISPLAYERONE;
        token4.tokenStatus = TokenStatusEnum.ISPLAYERONE;
        assertTrue(gridPanel.checkSequence(token1, token2, token3, token4)); // Test four consecutive tokens
    }
}