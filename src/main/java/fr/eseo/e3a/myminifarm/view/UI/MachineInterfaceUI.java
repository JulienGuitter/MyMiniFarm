package fr.eseo.e3a.myminifarm.view.UI;

import fr.eseo.e3a.myminifarm.controller.UIController;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums;
import fr.eseo.e3a.myminifarm.model.farmElement.Item;
import fr.eseo.e3a.myminifarm.model.farmElement.Machine;
import fr.eseo.e3a.myminifarm.view.ItemView;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class MachineInterfaceUI {
    @FXML private Label titleLabel;
    @FXML private Label timeLeftLabel;
    @FXML private HBox inventoryContent;

    private Pane root;
    private Machine machine;

    @FXML
    private void initialize() {
    }

    public void setMachine(Machine machine) {
        titleLabel.setText(machine.getName());
        this.machine = machine;
    }

    public void setGameUI(Pane gameUI) {
        this.root = gameUI;
    }

    public void update(){
        UIController.getInstance().setMachineInterfaceChanged(false);

        if (machine.isInUse()) {
            timeLeftLabel.setText("Time left: " + machine.getTimeRemaining());
        } else {
            timeLeftLabel.setText("Ready");
        }

        for (int i = 0; i < inventoryContent.getChildren().size(); i++) {
            StackPane cell = (StackPane) inventoryContent.getChildren().get(i);
            Item item = machine.getInventory().getItem(i);
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
                    UIController.getInstance().onDragStart(root, inventoryContent, cell, container, machine.getInventory(), FarmEnums.ItemType.MACHINE, event);
                });
            }

            cell.setOnDragOver(event -> {
                UIController.getInstance().onDragOver(cell, event);
            });

            // gérer le drop
            cell.setOnDragDropped(event -> {
                UIController.getInstance().onDragEnd(inventoryContent, cell, machine.getInventory(), FarmEnums.ItemType.MACHINE, machine.getTypeMachine(), event);
            });
        }
    }
}
