package fr.eseo.e3a.myminifarm.model.json;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * The LayerJson class represents a layer in the map JSON data.
 * It contains information about the layer's data, dimensions, name, opacity, visibility, and position.
 * This class is used to deserialize the map layer data from JSON format.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LayerJson {
    private List<Integer> data;
    private int height;
    private String name;
    private double opacity;
    private boolean visible;
    private int width;
    private int x;
    private int y;

    /**
     * Getter of data
     *
     * @return data
     */
    public List<Integer> getData() {
        return data;
    }

    /**
     * Setter of data
     *
     * @param data data
     */
    public void setData(List<Integer> data) {
        this.data = data;
    }

    /**
     * Getter of height
     *
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * Setter of height
     *
     * @param height height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Getter of name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of name
     *
     * @param name name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter of opacity
     *
     * @return opacity
     */
    public double getOpacity() {
        return opacity;
    }

    /**
     * Setter of opacity
     *
     * @param opacity opacity
     */
    public void setOpacity(double opacity) {
        this.opacity = opacity;
    }

    /**
     * Getter of visible
     *
     * @return visible
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Setter of visible
     *
     * @param visible visible
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Getter of width
     *
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * Setter of width
     *
     * @param width width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Getter of x
     *
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * Setter of x
     *
     * @param x x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Getter of y
     *
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * Setter of y
     *
     * @param y y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Used to convert the LayerJson object to a string representation.
     *
     * @return String representation of the LayerJson object.
     */
    public String toString() {
        return "Layer{ \n" +
                "data=" + data + ", \n" +
                "height=" + height + ", \n" +
                "name='" + name + '\'' + ", \n" +
                "opacity=" + opacity + ", \n" +
                "visible=" + visible + ", \n" +
                "width=" + width + ", \n" +
                "x=" + x + ", \n" +
                "y=" + y + "\n" +
                '}';
    }
}