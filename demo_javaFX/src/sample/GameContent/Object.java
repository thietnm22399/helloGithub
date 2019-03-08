package sample.GameContent;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;

public class Object {
    ImageView imageView;

    public Object(String filename) throws Exception {
        FileInputStream fis = new FileInputStream(filename);
        Image img = new Image(fis);
        ImageView imgView = new ImageView(img);
        fis.close();
        this.imageView = imgView;
    }

    public void addToPane(Pane pane, double posX, double posY) {
        this.imageView.setTranslateX(posX);
        this.imageView.setTranslateY(posY);
        pane.getChildren().add(imageView);
    }

    public ImageView getImageView() {
        return imageView;
    }
}
