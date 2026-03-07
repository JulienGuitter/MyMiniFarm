package fr.eseo.e3a.myminifarm.view.UI.modules;

import fr.eseo.e3a.myminifarm.controller.GameLoop;
import fr.eseo.e3a.myminifarm.controller.UIController;
import fr.eseo.e3a.myminifarm.model.Player;
import fr.eseo.e3a.myminifarm.model.farmElement.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ShopItemUI {
    @FXML private ImageView itemImage;
    @FXML private Label nameLabel;
    @FXML private Label buyPriceLabel;
    @FXML private Label sellPriceLabel;
    @FXML private Pane buyContainer;
    @FXML private Pane sellContainer;

    private Item item;

    public void setData(Item item, boolean canBuy) {
        this.item = item;
        nameLabel.setText(item.getName());
        buyPriceLabel.setText(String.valueOf(item.getCost())+" $ ");
        sellPriceLabel.setText(String.valueOf(item.getResalePrice())+" $ ");

        if(!canBuy){
            buyContainer.setVisible(canBuy);
            buyContainer.getChildren().clear();
        }

        int texureId = item.getTextureId();
        Image img = UIController.getInstance().getItemView().getItemImage(texureId, item.getItemType()).getImage();
        itemImage.setImage(img);
    }

    @FXML
    private void buyProduct(){
        Player player = GameLoop.getInstance().getPlayer();
        if (player.getMoney() >= item.getCost()){
            if(player.getInventory().add(item.clone())){
                player.deductMoney(item.getCost());
            }
        }
    }

    @FXML
    private void sellProduct(){
        Player player = GameLoop.getInstance().getPlayer();
        int index = player.getInventory().indexOfByName(item.getName());
        if(index == -1){
            return;
        }

        Item itemInv = player.getInventory().getItem(index);
        boolean isEmpty = itemInv.deductQuantity(1);
        player.addMoney(itemInv.getResalePrice());
        if (isEmpty){
            player.getInventory().remove(index);
        }
        player.getInventory().setChanged(true);
    }
}
