package org.nkjmlab.util.java.io;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class FileUtilsTest {
  @Test
  public void testListFiles(@TempDir Path tempDir) throws Exception {
    Files.createFile(tempDir.resolve("file1.txt"));
    Files.createDirectory(tempDir.resolve("subdir"));
    Files.createFile(tempDir.resolve("subdir/file2.txt"));
    List<Path> files = FileUtils.listFiles(tempDir);
    assertEquals(4, files.size());
  }

  @Test
  public void testNewBufferedReader(@TempDir Path tempDir) throws Exception {
    Path file = tempDir.resolve("test.txt");
    Files.writeString(file, "Hello", StandardCharsets.UTF_8);
    try (var br = FileUtils.newBufferedReader(file)) {
      assertEquals("Hello", br.readLine());
    }
  }

  @Test
  public void testNewBufferedWriter(@TempDir Path tempDir) throws Exception {
    Path file = tempDir.resolve("test.txt");
    try (var bw = FileUtils.newBufferedWriter(file)) {
      bw.write("Hello");
    }
    assertEquals("Hello", Files.readString(file));
  }

  @Test
  public void testNewFileReader(@TempDir Path tempDir) throws Exception {
    Path file = tempDir.resolve("test.txt");
    Files.writeString(file, "Hello", StandardCharsets.UTF_8);
    try (var fr = FileUtils.newFileReader(file.toFile())) {
      assertEquals('H', fr.read());
    }
  }

  @Test
  public void testNewFileWriter(@TempDir Path tempDir) throws Exception {
    Path file = tempDir.resolve("test.txt");
    try (var fw = FileUtils.newFileWriter(file.toFile())) {
      fw.write("Hello");
    }
    assertEquals("Hello", Files.readString(file));
  }

  @Test
  public void testReadAllBytes(@TempDir Path tempDir) throws Exception {
    Path file = tempDir.resolve("test.txt");
    String content = "Hello";
    Files.writeString(file, content, StandardCharsets.UTF_8);
    byte[] readBytes = FileUtils.readAllBytes(file);
    assertEquals(content, new String(readBytes, StandardCharsets.UTF_8));
  }

  @Test
  public void testReadAllLines(@TempDir Path tempDir) throws Exception {
    Path file = tempDir.resolve("test.txt");
    String content = "Hello\nWorld";
    Files.writeString(file, content, StandardCharsets.UTF_8);
    List<String> lines = FileUtils.readAllLines(file);
    assertArrayEquals(new String[] {"Hello", "World"}, lines.toArray(new String[0]));
  }

  @Test
  public void testWriteBytes(@TempDir Path tempDir) throws Exception {
    Path file = tempDir.resolve("test.txt");
    String content = "Hello";
    FileUtils.write(file, content.getBytes(StandardCharsets.UTF_8));
    assertEquals(content, Files.readString(file));
  }

  @Test
  public void testWriteLines(@TempDir Path tempDir) throws Exception {
    Path file = tempDir.resolve("test.txt");
    List<String> content = List.of("Hello", "World");
    FileUtils.write(file, content);
    assertEquals(content, Files.readAllLines(file));
  }

  @Test
  public void testWriteString(@TempDir Path tempDir) throws Exception {
    Path file = tempDir.resolve("test.txt");
    String content = "Hello World";
    FileUtils.write(file, content);
    assertEquals(content, Files.readString(file).trim());
  }
}
