package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.model.Inventory;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.*;

public class Container extends Item{
    private Containers typeContainer;
    private boolean keepOnClose;

    /**
     * The inventory associated with this container
     */
    private final Inventory inventory;

    /**
     * Constructs a new Container with the specified type.
     * The container's attributes (name, cost, textureId) are derived from the container type.
     * An inventory with the size corresponding to the container type is created.
     *
     * @param typeContainer The type of container to create
     */
    public Container(Containers typeContainer) {
        super(ItemType.CONTAINER, typeContainer.getName(), typeContainer.getCost(), typeContainer.getResalePrice(), typeContainer.getTextureId());
        this.typeContainer = typeContainer;
        this.inventory = new Inventory(typeContainer.getSize());
        this.keepOnClose = typeContainer.isKeepOnClose();
    }

    public void close(){
        if(!keepOnClose){
            inventory.clear();
        }
    }

    /**
     * Gets the inventory associated with this container.
     *
     * @return The inventory of this container
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the type of this container.
     *
     * @return The container type as defined in the Containers enum
     */
    public Containers getTypeContainer() {
        return typeContainer;
    }
}
