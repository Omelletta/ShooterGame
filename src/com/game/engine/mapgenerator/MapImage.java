package com.game.engine.mapgenerator;

import java.awt.*;
import java.awt.image.BufferedImage;

public class MapImage {

    private final int PIXEL_SCALE = 10;

    /**
     * Creates a 2D PNG Image from a two dimensional array.
     *
     * @param array
     */
    /*public void visualize(double[][] array) {
        createImage(array, "generatedMap");
    }
*/
    /**
     * Creates an amount of 2D PNG Images from a two dimensional array.
     *
     * @param array
     */
  /*  public void visualize(double[][] array, int amount) {
        for (int i = 0; i < amount; i++) {
            createImage(array, "generatedMap" + i);
        }
    }*/

    /**
     * Creates an amount of 2D PNG Images from a two dimensional array.
     *
     * @param array
     */
    public BufferedImage visualize(double[][] array) {
        BufferedImage bf = createImage(array);
        return bf;
    }

    /**
     * Private Method to create a Buffered Image and save the result in a file.
     *
     * @param array
     */
    private BufferedImage createImage(double[][] array) {

        System.out.println("Creating MapImage, please wait...");

        int IMAGE_HEIGHT = array.length * PIXEL_SCALE;
        int IMAGE_WIDTH = array[0].length * PIXEL_SCALE;

        System.out.println("Image Width: " + IMAGE_WIDTH + "px");
        System.out.println("Image Height: " + IMAGE_HEIGHT + "px");

        // Constructs a BufferedImage of one of the predefined image types.
        BufferedImage bufferedImage = new BufferedImage(IMAGE_WIDTH, IMAGE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        // Create a graphics which can be used to draw into the buffered image
        Graphics2D g2d = bufferedImage.createGraphics();

        // fill all the image with white
        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, IMAGE_WIDTH, IMAGE_HEIGHT);
        for (int x = 0; x < array.length; x++) {
            for (int y = 0; y < array[x].length; y++) {

                //Defining coloring rules for each value
                //You may also use enums with switch case here
                if (-0.8 < array[x][y] && array[x][y] > 0.1) { // fill with black
                    g2d.setColor(Color.BLACK);
                    g2d.fillRect(y * PIXEL_SCALE, x * PIXEL_SCALE, PIXEL_SCALE, PIXEL_SCALE);

                } else { // fill with white
                    g2d.setColor(Color.WHITE);
                    g2d.fillRect(y * PIXEL_SCALE, x * PIXEL_SCALE, PIXEL_SCALE, PIXEL_SCALE);
                }
            }
        }
        // Disposes of this graphics context and releases any system resources
        // that it is using.
        //resize
        Image tmp = bufferedImage.getScaledInstance(IMAGE_WIDTH / 100, IMAGE_HEIGHT / 100, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(IMAGE_WIDTH / 100, IMAGE_HEIGHT / 100, BufferedImage.TYPE_INT_ARGB);
        g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

       /* System.out.printf("Saving MapImage to Disk as %s.png ... \n", filename);
        // Save as PNG
        try {
            ImageIO.write(dimg, "png", new File("C:\\Users\\damia\\Desktop\\SOA\\lab1\\gameengine\\res\\lvl\\level2.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        System.out.println("Done! \n");
        return dimg;
    }
}
