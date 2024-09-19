package edu.gonzaga;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class TokenImages {

    private ArrayList<ImageIcon> images;

    public TokenImages(String imagesPath) {
        images = new ArrayList<>(2);
        loadImages(imagesPath);
    }

    private void loadImages(String imagesPath) {
        try {
            String[] filenames = {"token01.png", "token02.png"};
            for (String filename : filenames) {
                System.out.println("Loading image: " + filename);
                BufferedImage currPicture = ImageIO.read(new File(imagesPath + filename));
                Image dimg = currPicture.getScaledInstance(90, 90, Image.SCALE_SMOOTH);
                ImageIcon scaledImage = new ImageIcon(dimg);
                images.add(scaledImage);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ImageIcon getTokenImage(int tokenValue) {
        if (tokenValue >= 1 && tokenValue <= images.size()) {
            return images.get(tokenValue - 1);
        } else {
            throw new IllegalArgumentException("Invalid token value: " + tokenValue);
        }
    }

}