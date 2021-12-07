package org.nkjmlab.util.java;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.nkjmlab.util.java.function.Try;

public class Base64Utils {

  public static String decode(String base64EncodedData) {
    byte[] decoded =
        Base64.getDecoder().decode(base64EncodedData.replaceFirst("data:.*?base64,", ""));
    try (
        InputStreamReader in =
            new InputStreamReader(new ByteArrayInputStream(decoded), StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(in)) {
      String result = "";
      String line;
      while ((line = br.readLine()) != null) {
        result += line + System.lineSeparator();
      }
      return result;
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static BufferedImage decodeToImage(String encodedImage, String formatName) {
    byte[] decoded = Base64.getDecoder().decode(encodedImage.replaceFirst("data:.*?base64,", ""));
    try (InputStream in = new ByteArrayInputStream(decoded)) {
      BufferedImage image = ImageIO.read(in);
      return image;
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static String encode(RenderedImage image, String formatName) {
    try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(baos);) {
      ImageIO.write(image, formatName, bos);
      bos.flush();
      return Base64.getEncoder().encodeToString(baos.toByteArray());
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }


}
