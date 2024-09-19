package edu.gonzaga;

enum TokenStatusEnum {
    ISNOTINGRID,
    ISPLAYERONE,
    ISPLAYERTWO
}

public class Token {
    // X to indicate is not in grid
    String image = " X ";
    String color = "";
    Boolean isInGrid = false;

    public TokenStatusEnum tokenStatus = TokenStatusEnum.ISNOTINGRID;

    private int imageIndex = -1;

    void printToken() {
        System.out.println(this.image);
    }

    public int getImageIndex() {
        return imageIndex;
    }

    public void setImageIndex(int imageIndex) {
        this.imageIndex = imageIndex;
    }
}
