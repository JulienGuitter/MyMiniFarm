package fr.eseo.e3a.myminifarm.view.utils;

import fr.eseo.e3a.myminifarm.model.farmElement.FarmEnums;

public class DragContext {
    public static Object sourceContainer = null;
    public static FarmEnums.ItemType containerType = null;
    public static int sourceIndex = -1;

    public static void set(Object sourceContainer, FarmEnums.ItemType containerType, int sourceIndex) {
        DragContext.sourceContainer = sourceContainer;
        DragContext.containerType = containerType;
        DragContext.sourceIndex = sourceIndex;
    }

    public static void clear() {
        DragContext.sourceContainer = null;
        DragContext.containerType = null;
        DragContext.sourceIndex = -1;
    }
}
