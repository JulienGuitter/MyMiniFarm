package fr.eseo.e3a.myminifarm.view;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.io.InputStream;
import java.util.HashMap;

/**
 * The SpriteSheet class is used to manage a collection of sprites (images) that can be used in a game or application.
 * It allows for loading a sprite sheet from an image file, extracting individual sprites, and managing them by ID.
 */
public class SpriteSheet {
    private final HashMap<Integer, Image> sprites;
    private int columns;
    private int rows;

    /**
     * Default constructor for the SpriteSheet class.
     * Initializes an empty sprite collection.
     */
    public SpriteSheet() {
        this.sprites = new HashMap<>();
    }

    /**
     * Constructor used to load a sprite sheet from an InputStream.
     * Loads the sprite sheet and extracts individual sprites based on columns and rows.
     *
     * @param spritePath the InputStream of the sprite sheet image
     * @param columns    the number of columns in the sprite sheet
     * @param rows       the number of rows in the sprite sheet
     */
    public SpriteSheet(InputStream spritePath, int columns, int rows) {
        this();
        this.columns = columns;
        this.rows = rows;
        Image spriteSheet = new Image(spritePath);
        int tileWidth = (int) spriteSheet.getWidth() / columns;
        int tileHeight = (int) spriteSheet.getHeight() / rows;

        PixelReader pixelReader = spriteSheet.getPixelReader();

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < columns; x++) {
                int startX = x * tileWidth;
                int startY = y * tileHeight;

                if (!isFullyTransparent(pixelReader, startX, startY, tileWidth, tileHeight)) {
                    WritableImage tile = new WritableImage(pixelReader, x * tileWidth, y * tileHeight, tileWidth, tileHeight);
                    this.sprites.put(y * columns + x, tile);
                }
            }
        }

    }

    /**
     * Checks if a given area of the sprite sheet is fully transparent.
     * Prevents loading empty tiles into the sprite sheet.
     *
     * @param reader the PixelReader of the sprite sheet
     * @param x      the x-coordinate of the top-left corner of the area
     * @param y      the y-coordinate of the top-left corner of the area
     * @param w      the width of the area
     * @param h      the height of the area
     * @return true if the area is fully transparent, false otherwise
     */
    private static boolean isFullyTransparent(PixelReader reader, int x, int y, int w, int h) {
        for (int i = x; i < x + w; i++) {
            for (int j = y; j < y + h; j++) {
                Color color = reader.getColor(i, j);
                if (color.getOpacity() > 0.01) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Adds an external sprite to the sprite sheet.
     *
     * @param id    the ID of the sprite
     * @param sprite the Image object representing the sprite
     */
    public void addSprite(int id, Image sprite) {
        this.sprites.put(id, sprite);
    }

    /**
     * Retrieves a sprite from the sprite sheet by its ID.
     *
     * @param id the ID of the sprite
     * @return the Image object representing the sprite
     */
    public Image getSprite(int id) {
        return this.sprites.get(id);
    }

    /**
     * Retrieves all sprites in the sprite sheet.
     *
     * @return a HashMap containing all sprites with their IDs
     */
    public HashMap<Integer, Image> getSprites() {
        return this.sprites;
    }

    /**
     * Removes a sprite from the sprite sheet by its ID.
     *
     * @param id the ID of the sprite to remove
     */
    public void removeSprite(int id) {
        this.sprites.remove(id);
    }

    /**
     * Clears all sprites from the sprite sheet.
     */
    public void clear() {
        this.sprites.clear();
    }

    /**
     * Checks if the sprite sheet is empty.
     *
     * @return true if the sprite sheet is empty, false otherwise
     */
    public boolean isEmpty() {
        return this.sprites.isEmpty();
    }

    /**
     * Returns the number of columns in the sprite sheet.
     *
     * @return the number of columns
     */
    public int getColumns() {
        return columns;
    }

    /**
     * Returns the number of rows in the sprite sheet.
     *
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }


}
