package fr.eseo.e3a.myminifarm.model.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * The MapJson class represents the JSON structure of a map.
 * It contains information about layers, tilesets, dimensions, and object IDs.
 * This class is used to deserialize JSON data into Java objects using Jackson library.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MapJson {
    private List<LayerJson> layers;
    private List<TilesetJson> tilesets;
    private int width;
    private int height;
    private int tilewidth;
    private int tileheight;
    private int nextobjectid;
    private int nextlayerid;

    /**
     * Getter of Layers
     *
     * @return layers
     */
    public List<LayerJson> getLayers() {
        return layers;
    }

    /**
     * Setter of Layers
     *
     * @param layers layers
     */
    public void setLayers(List<LayerJson> layers) {
        this.layers = layers;
    }

    /**
     * Getter of Tilesets
     *
     * @return tilesets
     */
    public List<TilesetJson> getTilesets() {
        return tilesets;
    }

    /**
     * Setter of Tilesets
     *
     * @param tilesets tilesets
     */
    public void setTilesets(List<TilesetJson> tilesets) {
        this.tilesets = tilesets;
    }

    /**
     * Getter of Width
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Setter of Width
     *
     * @param width width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Getter of Height
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter of Height
     *
     * @param height height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Getter of Tilewidth
     *
     * @return tilewidth
     */
    public int getTilewidth() {
        return tilewidth;
    }

    /**
     * Setter of Tilewidth
     *
     * @param tilewidth tilewidth
     */
    public void setTilewidth(int tilewidth) {
        this.tilewidth = tilewidth;
    }

    /**
     * Getter of Tileheight
     *
     * @return tileheight
     */
    public int getTileheight() {
        return tileheight;
    }

    /**
     * Setter of Tileheight
     *
     * @param tileheight tileheight
     */
    public void setTileheight(int tileheight) {
        this.tileheight = tileheight;
    }

    /**
     * Getter of Nextobjectid
     *
     * @return nextobjectid
     */
    public int getNextobjectid() {
        return nextobjectid;
    }

    /**
     * Setter of Nextobjectid
     *
     * @param nextobjectid nextobjectid
     */
    public void setNextobjectid(int nextobjectid) {
        this.nextobjectid = nextobjectid;
    }

    /**
     * Getter of Nextlayerid
     *
     * @return nextlayerid
     */
    public int getNextlayerid() {
        return nextlayerid;
    }

    /**
     * Setter of Nextlayerid
     *
     * @param nextlayerid nextlayerid
     */
    public void setNextlayerid(int nextlayerid) {
        this.nextlayerid = nextlayerid;
    }

    /**
     * This method is used to convert the MapJson object to a string representation.
     * It includes all the fields of the MapJson class.
     *
     * @return String representation of the MapJson object
     */
    public String toString() {
        return "MapJson{ \n" +
                "layers=" + layers + ", \n" +
                "tilesets=" + tilesets + ", \n" +
                "width=" + width + ", \n" +
                "height=" + height + ", \n" +
                "tilewidth=" + tilewidth + ", \n" +
                "tileheight=" + tileheight + ", \n" +
                "nextobjectid=" + nextobjectid + ", \n" +
                "nextlayerid=" + nextlayerid + "\n" +
                '}';
    }
}
