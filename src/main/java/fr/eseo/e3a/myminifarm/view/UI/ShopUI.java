package fr.eseo.e3a.myminifarm.view.UI;

import fr.eseo.e3a.myminifarm.MyMiniFarm;
import fr.eseo.e3a.myminifarm.model.farmElement.*;
import fr.eseo.e3a.myminifarm.view.UI.modules.ShopItemUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;

public class ShopUI {
    @FXML
    private VBox itemContainer;

    private VBox seedContainer;
    private VBox animalContainer;
    private VBox machineContainer;
    private VBox toolsContainer;
    private VBox ProductContainer;

    @FXML
    private void initialize() {
        seedContainer = new VBox();
        seedContainer.setSpacing(10);
        animalContainer = new VBox();
        animalContainer.setSpacing(10);
        machineContainer = new VBox();
        machineContainer.setSpacing(10);
        toolsContainer = new VBox();
        toolsContainer.setSpacing(10);
        ProductContainer = new VBox();
        ProductContainer.setSpacing(10);

        List<Item> seedItems = List.of(
                new Product(FarmEnums.Seed.WHEAT_SEED),
                new Product(FarmEnums.Seed.POTATO_SEED),
                new Product(FarmEnums.Seed.CORN_SEED),
                new Product(FarmEnums.Seed.TOMATO_SEED),
                new Product(FarmEnums.Seed.CABBAGE_SEED),
                new Product(FarmEnums.Seed.SUNFLOWER_SEED)
        );

        List<Item> animalItems = List.of(
                new Animal(FarmEnums.Animals.COW),
                new Animal(FarmEnums.Animals.PIG),
                new Animal(FarmEnums.Animals.CHICKEN),
                new Animal(FarmEnums.Animals.SHEEP)
        );

        List<Item> machineItems = List.of(
                new Container(FarmEnums.Containers.CHEST),
                new Container(FarmEnums.Containers.TRASH),
                new Machine(FarmEnums.Machines.OVEN),
                new Machine(FarmEnums.Machines.SMOKER),
                new Machine(FarmEnums.Machines.WINDMILL),
                new Machine(FarmEnums.Machines.LOOM),
                new Machine(FarmEnums.Machines.MILKPROCESSOR)
        );

        List<Item> toolsItems = List.of(
                new Tool(FarmEnums.ToolType.HOE),
                new Tool(FarmEnums.ToolType.AXE),
                new Tool(FarmEnums.ToolType.PICKAXE),
                new Tool(FarmEnums.ToolType.WATERING_CAN)
        );

        List<Item> productItems = List.of(
                new Product(FarmEnums.CultureProductType.WHEAT),
                new Product(FarmEnums.CultureProductType.POTATO),
                new Product(FarmEnums.CultureProductType.CORN),
                new Product(FarmEnums.CultureProductType.TOMATO),
                new Product(FarmEnums.CultureProductType.CABBAGE),
                new Product(FarmEnums.CultureProductType.SUNFLOWER),
                new Product(FarmEnums.MachineProductType.FLOUR),
                new Product(FarmEnums.MachineProductType.BREAD),
                new Product(FarmEnums.MachineProductType.CHEESE),
                new Product(FarmEnums.AnimalProductType.MILK),
                new Product(FarmEnums.AnimalProductType.EGG),
                new Product(FarmEnums.AnimalProductType.WOOL),
                new Product(FarmEnums.AnimalProductType.BACON)
        );

        loadItemsList(seedItems, seedContainer, true);
        loadItemsList(animalItems, animalContainer, true);
        loadItemsList(machineItems, machineContainer, true);
        loadItemsList(toolsItems, toolsContainer, true);
        loadItemsList(productItems, ProductContainer, false);

        showContainer(0);
    }

    private void loadItemsList(List<Item> items, VBox container, boolean canBuy) {
        items.forEach(item -> {
            try {
                FXMLLoader loader = new FXMLLoader(MyMiniFarm.class.getResource("UI/modules/shopItem.fxml"));
                Node node = loader.load();
                ShopItemUI ctrl = loader.getController();
                ctrl.setData(item, canBuy);
                container.getChildren().add(node);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void showContainer(int index) {
        itemContainer.getChildren().clear();
        switch (index) {
            case 0 -> itemContainer.getChildren().add(seedContainer);
            case 1 -> itemContainer.getChildren().add(animalContainer);
            case 2 -> itemContainer.getChildren().add(machineContainer);
            case 3 -> itemContainer.getChildren().add(toolsContainer);
            case 4 -> itemContainer.getChildren().add(ProductContainer);
        }
    }

    @FXML
    private void onChangeShopContainer(ActionEvent e) {
        Button src = (Button) e.getSource();

        showContainer(Integer.parseInt((String) src.getUserData()));
    }
}
