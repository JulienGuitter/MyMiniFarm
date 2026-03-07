package fr.eseo.e3a.myminifarm.view.UI.modules;

import fr.eseo.e3a.myminifarm.controller.GameController;
import fr.eseo.e3a.myminifarm.controller.GameLoop;
import fr.eseo.e3a.myminifarm.controller.UIController;
import fr.eseo.e3a.myminifarm.model.farmElement.Item;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class InventoryBarUI {
    @FXML private HBox inventoryBar;
    @FXML private Label itemLabel;

    private Pane root;

    private int actualIndex = 0;

    @FXML
    private void initialize(){
        GameLoop.getInstance().setInventoryBarUI(this);
        selectCell(actualIndex);
    }

    public void setRoot(Pane root){
        this.root = root;
    }

    public int getSelectCell() {
        return actualIndex;
    }

    public void selectCell(int index){
        if (index >= 0 && index < inventoryBar.getChildren().size()){
            deselectCell(actualIndex);
            inventoryBar.getChildren().get(index).getStyleClass().add("selected_inv_cell");
            actualIndex = index;

            Item item = GameLoop.getInstance().getPlayer().getInventory().getItem(index);
            if(item != null) {
                itemLabel.setText(item.getName());
            }else{
                itemLabel.setText("");
            }
        }
    }

    private void deselectCell(int index) {
        if (index >= 0 && index < inventoryBar.getChildren().size()) {
            inventoryBar.getChildren().get(index).getStyleClass().remove("selected_inv_cell");
        }
    }

    public void updateInventoryBar() {
        selectCell(actualIndex);
        for (int i = 0; i < inventoryBar.getChildren().size(); i++) {
            StackPane cell = (StackPane) inventoryBar.getChildren().get(i);
            Item item = GameLoop.getInstance().getPlayer().getInventory().getItem(i);
            if (item == null) {
                cell.getChildren().clear();
                cell.setStyle("-fx-background-color: transparent;");
            }else{
                int texureId = item.getTextureId();
                StackPane container = new StackPane();

                ImageView imageView = UIController.getInstance().getItemView().getItemImage(texureId, item.getItemType());

                container.getChildren().add(imageView);

                Label label = new Label(String.valueOf(item.getQuantity()));
                label.setStyle("-fx-font-size: 12px; -fx-text-fill: white;");
                label.setTranslateX(20);
                label.setTranslateY(20);

                container.getChildren().add(label);

                cell.getChildren().clear();
                cell.getChildren().add(container);



                container.setOnDragDetected(event -> {
                    UIController.getInstance().onDragStart(root, inventoryBar, cell, container, GameController.getInstance().getPlayer().getInventory(), null, event);
                });
            }

            cell.setOnMouseClicked(event -> {
                GameLoop.getInstance().getPlayer().setInvBarIndex(inventoryBar.getChildren().indexOf(cell));
                selectCell(inventoryBar.getChildren().indexOf(cell));
            });

            cell.setOnDragOver(event -> {
                UIController.getInstance().onDragOver(cell, event);
            });

            // gérer le drop
            cell.setOnDragDropped(event -> {
                UIController.getInstance().onDragEnd(inventoryBar, cell, GameController.getInstance().getPlayer().getInventory(), null, null, event);
            });
        }
    }
}
