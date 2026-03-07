package fr.eseo.e3a.myminifarm.model.farmElement;

import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums.ToolType;

/**
 * Represents a tool item in the farm system.
 * <p>
 * Tools are items that can be used by the player to perform various actions in the farm,
 * such as tilling soil, watering crops, or harvesting. Each tool has a specific type
 * defined in the ToolType enumeration, which determines its function and appearance.
 * </p>
 */
public class Tool extends Item implements Cloneable {

    /**
     * The specific type of this tool from the ToolType enumeration.
     */
    private final ToolType typeTools;

    /**
     * Creates a new tool with the specified tool type.
     * The tool inherits its name, cost, and texture ID from the tool type.
     *
     * @param typeTools The specific type of tool to create
     */
    public Tool(ToolType typeTools) {
        super(FarmEnums.ItemType.TOOL, typeTools.getName(), typeTools.getCost(), typeTools.getResalePrice(), typeTools.getTextureId());
        this.typeTools = typeTools;
    }

    /**
     * Gets the specific type of this tool.
     *
     * @return The tool type from the ToolType enumeration
     */
    public ToolType getTypeTools() {
        return typeTools;
    }

    /**
     * Returns a string representation of this tool.
     * The string includes the tool's type.
     *
     * @return A string representation of this tool
     */
    @Override
    public String toString() {
        return "Tool{" +
                "type=" + typeTools +
                '}';
    }
}
