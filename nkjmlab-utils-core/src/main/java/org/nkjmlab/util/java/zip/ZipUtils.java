package org.nkjmlab.util.java.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;
import org.nkjmlab.util.java.function.Try;

public class ZipUtils {

  public static boolean zip(File srcDir, File toFile) {
    try (FileOutputStream os = new FileOutputStream(toFile);
        ZipOutputStream zos = new ZipOutputStream(os)) {
      encode(srcDir.getParent(), zos, new File[] {srcDir});
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
    return true;
  }


  private static void encode(String prefix, ZipOutputStream zos, File[] files) throws IOException {
    for (File f : files) {
      if (f.isDirectory()) {
        encode(prefix, zos, f.listFiles());
      } else {
        encodeFile(prefix, zos, f);
      }
    }
  }

  private static void encodeFile(String prefix, ZipOutputStream zos, File file) throws IOException {
    ZipEntry entry = new ZipEntry(file.getPath().replace(prefix, "").replace('\\', '/'));
    zos.putNextEntry(entry);
    try (InputStream fs = new FileInputStream(file); InputStream is = new BufferedInputStream(fs)) {
      byte[] buf = new byte[1024];
      for (;;) {
        int len = is.read(buf);
        if (len < 0)
          break;
        zos.write(buf, 0, len);
      }
    }
  }


  public static boolean unzip(File srcFile, File toDir) {
    try (ZipFile zipFile = new ZipFile(srcFile)) {
      Enumeration<? extends ZipEntry> enumZip = zipFile.entries();
      while (enumZip.hasMoreElements()) {
        ZipEntry zipEntry = enumZip.nextElement();
        File outFile = new File(toDir, new File(zipEntry.getName()).getName());
        unzipZipEntry(zipFile, zipEntry, outFile);
      }
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
    return true;
  }


  private static void unzipZipEntry(ZipFile zipFile, ZipEntry zipEntry, File outFile)
      throws IOException {

    if (zipEntry.isDirectory()) {
      outFile.mkdirs();
      return;
    }

    try (InputStream is = zipFile.getInputStream(zipEntry);
        BufferedInputStream in = new BufferedInputStream(is)) {

      if (!outFile.getParentFile().exists()) {
        outFile.getParentFile().mkdirs();
      }

      try (OutputStream os = new FileOutputStream(outFile);
          BufferedOutputStream out = new BufferedOutputStream(os)) {
        byte[] buffer = new byte[1024];
        int readSize = 0;
        while ((readSize = in.read(buffer)) != -1) {
          out.write(buffer, 0, readSize);
        }
      }
    }

  }
}
