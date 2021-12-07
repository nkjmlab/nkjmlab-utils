package org.nkjmlab.util.javax.imageio;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import org.nkjmlab.util.java.function.Try;

public class ImageIoUtils {

  public static BufferedImage read(File inFile) {
    try {
      return ImageIO.read(inFile);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }


  public static void write(BufferedImage image, String formatName, File outFile) {
    try {
      ImageIO.write(image, formatName, outFile);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static void write(JComponent component, String formatName, File outFile) {
    BufferedImage image = new BufferedImage(component.getSize().width, component.getSize().height,
        BufferedImage.TYPE_INT_ARGB);
    Graphics g = image.createGraphics();
    component.paint(g);
    g.dispose();
    write(image, formatName, outFile);
  }
}
