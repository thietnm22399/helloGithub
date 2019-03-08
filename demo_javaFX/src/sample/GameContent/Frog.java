package sample.GameContent;

import javafx.animation.AnimationTimer;
import javafx.scene.image.ImageView;

public class Frog extends Object {
    boolean dead = false;
    private int moveCount = 0;

    public Frog(String filename) throws Exception {
        super(filename);
    }

    public boolean getDead() {
        return this.dead;
    }

    public void moveUp() {
        this.getImageView().setTranslateY(this.getImageView().getTranslateY() - 50);
    }

}
