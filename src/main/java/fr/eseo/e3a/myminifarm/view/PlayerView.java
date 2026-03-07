package fr.eseo.e3a.myminifarm.view;

import fr.eseo.e3a.myminifarm.MyMiniFarm;
import fr.eseo.e3a.myminifarm.controller.UIController;
import fr.eseo.e3a.myminifarm.model.Player;
import fr.eseo.e3a.myminifarm.utils.Vector2;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * PlayerView is responsible for displaying the player on the screen,
 * including handling the player's animations and position.
 */
public class PlayerView {
    private UIController uiController;

    private SpriteSheet sprites;
    private Player player;

    private int animIndex = 0;
    private long animTime;
    private boolean hasMoved = false;

    private StackPane playerSP;
    private ImageView playerIV;

    /**
     * Constructs a PlayerView for the given player and sprite path.
     *
     * @param player the Player object
     * @param spritePath the path to the sprite sheet
     */
    public PlayerView(Player player, String spritePath){
        uiController = UIController.getInstance();

        this.player = player;
        this.sprites = new SpriteSheet(MyMiniFarm.class.getResourceAsStream(spritePath), 4, 4);
        playerSP = new StackPane();
        playerIV = new ImageView(this.sprites.getSprite(player.getDirection().getIndex()));
        playerIV.setFitWidth(UIController.IMAGE_SIZE * UIController.IMAGE_RATIO);
        playerIV.setFitHeight(UIController.IMAGE_SIZE*2 * UIController.IMAGE_RATIO);
        playerSP.getChildren().add(playerIV);

        animTime = System.currentTimeMillis();
    }

    /**
     * Updates the player's position and animation on the screen.
     */
    public void update(){
        double posX = player.getPos().x() * 16 * UIController.IMAGE_RATIO;
        double posY = player.getPos().y() * 16 * UIController.IMAGE_RATIO;

        double minX = uiController.getWindowWidth()/2;
        double minY = uiController.getWindowHeight()/2;
        double maxX = uiController.getMapHeight() - uiController.getWindowWidth()/2;
        double maxY = uiController.getMapHeight() - uiController.getWindowHeight()/2;

        double offsetX = (double) -UIController.IMAGE_SIZE /2 * UIController.IMAGE_RATIO;
        double offsetY = -UIController.IMAGE_SIZE*2 * UIController.IMAGE_RATIO;

        if(posX < minX){
            playerIV.setTranslateX(posX + offsetX);
        }else if (posX > maxX) {
            playerIV.setTranslateX(posX - maxX + uiController.getWindowWidth()/2 + offsetX);
        }else{
            playerIV.setTranslateX(offsetX+uiController.getWindowWidth()/2);
        }


        if(posY < minY){
            playerIV.setTranslateY(posY + offsetY);
        }else if(posY > maxY){
            playerIV.setTranslateY(posY - maxY + uiController.getWindowHeight()/2 + offsetY);
        }else{
            playerIV.setTranslateY(offsetY+uiController.getWindowHeight()/2);
        }

        if(player.isMoving()) {
            updateAnimation();
        }else if(hasMoved){
            playerIV.setImage(sprites.getSprite(player.getDirection().getIndex()));
        }
    }

    /**
     * Updates the player's animation frame if moving.
     */
    private void updateAnimation(){
        if(System.currentTimeMillis() - animTime >= UIController.ANIMATION_SPEED){
            animIndex++;

            if(animIndex >= 4){
                animIndex = 0;
            }

            playerIV.setImage(sprites.getSprite(player.getDirection().getIndex() + animIndex));

            animTime = System.currentTimeMillis();
            hasMoved = true;
        }
    }

    /**
     * Returns the StackPane containing the player's ImageView.
     *
     * @return the StackPane for the player
     */
    public StackPane getStackPane(){
        return playerSP;
    }
}
