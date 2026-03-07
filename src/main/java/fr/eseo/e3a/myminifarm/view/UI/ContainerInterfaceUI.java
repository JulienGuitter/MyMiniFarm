package fr.eseo.e3a.myminifarm.view.UI;

import fr.eseo.e3a.myminifarm.controller.UIController;
import fr.eseo.e3a.myminifarm.model.farmElement.Container;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums;
import fr.eseo.e3a.myminifarm.model.farmElement.Item;
import fr.eseo.e3a.myminifarm.model.farmElement.Container;
import fr.eseo.e3a.myminifarm.view.ItemView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class ContainerInterfaceUI {
    @FXML private Label titleLabel;
    @FXML private HBox inventoryContent;

    private Pane root;
    private Container container;

    @FXML
    private void initialize() {
    }

    public void setContainer(Container container) {
        this.container = container;
        titleLabel.setText(container.getName());

        inventoryContent.getChildren().clear();
        for (int i = 0; i < container.getInventory().getSize(); i++) {
            StackPane cell = new StackPane();
            cell.getStyleClass().addAll("cell_wood", "inv_cell");
            cell.setMinSize(70, 70);
            inventoryContent.getChildren().add(cell);
        }
    }

    public void setGameUI(Pane gameUI) {
        this.root = gameUI;
    }

    public void update(){
        UIController.getInstance().setContainerInterfaceChanged(false);

        for (int i = 0; i < inventoryContent.getChildren().size(); i++) {
            StackPane cell = (StackPane) inventoryContent.getChildren().get(i);
            Item item = container.getInventory().getItem(i);
            if(item == null) {
                cell.getChildren().clear();
                cell.setStyle("-fx-background-color: transparent;");
            } else {
                int texureId = item.getTextureId();
                StackPane container = new StackPane();

                ImageView imageView = ItemView.getItemImage(texureId, item.getItemType());

                container.getChildren().add(imageView);

                Label label = new Label(String.valueOf(item.getQuantity()));
                label.setStyle("-fx-font-size: 12px; -fx-text-fill: white;");
                label.setTranslateX(20);
                label.setTranslateY(20);

                container.getChildren().add(label);

                cell.getChildren().clear();
                cell.getChildren().add(container);


                container.setOnDragDetected(event -> {
                    UIController.getInstance().onDragStart(root, inventoryContent, cell, container, this.container.getInventory(), FarmEnums.ItemType.CONTAINER, event);
                });
            }

            cell.setOnDragOver(event -> {
                UIController.getInstance().onDragOver(cell, event);
            });

            // gérer le drop
            cell.setOnDragDropped(event -> {
                UIController.getInstance().onDragEnd(inventoryContent, cell, container.getInventory(), FarmEnums.ItemType.CONTAINER, null, event);
            });
        }
    }

    public Container getContainer(){
        return container;
    }
}
