package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.controller.GameLoop;
import fr.eseo.e3a.myminifarm.controller.UIController;
import fr.eseo.e3a.myminifarm.model.Inventory;
import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.*;
import fr.eseo.e3a.myminifarm.utils.GameTime;

/**
 * Abstract class representing a processing machine on the farm.
 * <p>
 * A machine must be supplied with the right material before it can process and produce an output item.
 * Each machine has a specific type, inventory for input and output items, processing time, and state.
 * Machines follow recipes defined in the ReceipeType enumeration to transform input materials into
 * processed products.
 * </p>
 */
public class Machine extends Item {

    /**
     * Indicates whether the machine is currently processing an input material.
     */
    private boolean isInUse;

    /**
     * The recipe that the machine is currently using or will use for processing.
     */
    private ReceipeType receipeType;

    /**
     * The specific type of this machine from the Machines enumeration.
     */
    private final Machines typeMachine;

    /**
     * The inventory containing input and output items for this machine.
     */
    private final Inventory inventory;

    /**
     * The game time when the current processing will complete.
     */
    private GameTime processDelay;

    /**
     * The time remaining until the current processing completes.
     */
    private GameTime timeRemaining;

    /**
     * Creates a new machine of the specified type.
     * The machine is initialized with an empty inventory of size 2 (for input and output)
     * and is not in use initially.
     *
     * @param typeMachines The type of the machine from the Machines enumeration.
     */
    public Machine(Machines typeMachines) {
        super(ItemType.MACHINE, typeMachines.getName(), typeMachines.getCost(), typeMachines.getResalePrice(), typeMachines.getTextureId());
        isInUse = false;
        typeMachine = typeMachines;
        inventory = new Inventory(2);
        this.processDelay = GameLoop.getInstance().getGameTime().clone();
    }

    /**
     * Checks if the provided animal product material is suitable for this machine.
     * Different machines can process different types of animal products.
     *
     * @param material The animal product material to check.
     * @return {@code true} if the material is appropriate for this machine, {@code false} otherwise.
     */
    public boolean isSuitable(AnimalProductType material) {
        return switch (material) {
            case MILK -> this.typeMachine == Machines.MILKPROCESSOR;
            case EGG -> this.typeMachine == Machines.OVEN;
            case BACON -> this.typeMachine == Machines.SMOKER;
            case WOOL -> this.typeMachine == Machines.LOOM;
        };
    }

    /**
     * Checks if the machine is ready to process an input item.
     * A machine is ready to process if it is not currently in use and
     * has a suitable input item in its inventory.
     *
     * @return {@code true} if the machine is ready to process, {@code false} otherwise.
     */
    public boolean isReadyToProcess() {
        /* Can produce if there is a suitable input and is free */
        if (this.isInUse) return false;
        if (!inventory.containItem()) return false;
        Item source = inventory.getItem(0);
        if (source == null) {
            return false;
        }
        this.receipeType = ReceipeType.getByName(source.getName());
        inventory.setChanged(true);
        return true;
    }

    /**
     * Updates the production state of the machine.
     * If the machine is in use, checks if processing is complete.
     * If the machine is not in use but ready to process, starts processing.
     *
     * @return {@code true} if the machine state changed (started or completed processing),
     * {@code false} otherwise.
     */
    public boolean updateProduction() {
        /* If in Use, only update Time */
        if (this.isInUse) {
            if (!GameLoop.getInstance().getGameTime().isTimePassed(processDelay)) {
                timeRemaining = GameLoop.getInstance().getGameTime().subtract(this.processDelay, GameLoop.getInstance().getGameTime());
                UIController.getInstance().setMachineInterfaceChanged(true);
            } else {
                produce();
                return true;
            }
        } else if (isReadyToProcess()) {
            this.isInUse = true;
            Item source = inventory.getItem(0);
            if (source.getQuantity() > 1) {
                source.setQuantity(source.getQuantity() - 1);
            } else {
                inventory.remove(source);
            }
            inventory.setChanged(true);
            this.processDelay = GameLoop.getInstance().getGameTime().clone();
            this.processDelay.addminute(this.typeMachine.getProductionDelay());
            return true;
        }
        return false;
    }

    /**
     * Produces an output item based on the current recipe.
     * This method is called when processing is complete.
     * The produced item is added to the machine's inventory.
     */
    private void produce() {
        this.isInUse = false;
        if (inventory.getItem(1) == null) {
            inventory.add(new Product((MachineProductType) this.receipeType.getOutput()), 1);
            if (this.receipeType.getQuantity() > 1)
                inventory.addQuantity(1, this.receipeType.getQuantity() - 1);

        } else {
            inventory.addQuantity(1, this.receipeType.getQuantity());
        }
    }

    /**
     * Gets the inventory of this machine.
     * The inventory contains both input and output items.
     *
     * @return The machine's inventory.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the type of this machine.
     *
     * @return The machine type from the Machines enumeration.
     */
    public Machines getTypeMachine() {
        return typeMachine;
    }

    /**
     * Gets the time remaining until the current processing completes.
     *
     * @return The time remaining as a GameTime object, or null if not processing.
     */
    public GameTime getTimeRemaining() {
        return timeRemaining;
    }

    /**
     * Checks if the machine is currently processing an item.
     *
     * @return {@code true} if the machine is in use, {@code false} otherwise.
     */
    public boolean isInUse() {
        return isInUse;
    }
}
