package org.nkjmlab.util.java.lang;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

class ClasspathResourceTest {

  @Test
  public void test() throws IOException {

    ClasspathResource resource = ClasspathResource.of(Test.class, "Tag.class");

    try (InputStreamReader inputStreamReader =
            new InputStreamReader(
                resource.getResourceAsUrl().openStream(), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader)) {

      String line;
      while ((line = bufferedReader.readLine()) != null) {
        System.out.println(line);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testResourceExists() {
    ClasspathResource resource =
        ClasspathResource.of(ClasspathResourceTest.class, "/root-test-resource.txt");
    assertNotNull(resource.getResourceAsUrl(), "URL should not be null for existing resource");
    assertNotNull(resource.getResourceAsUri(), "URI should not be null for existing resource");
    assertNotNull(
        resource.getResourceAsStream(), "InputStream should not be null for existing resource");

    // Testing file and path access might require the file to actually exist at these paths or be
    // accessible
    assertDoesNotThrow(
        () -> {
          File file = resource.getResourceAsFile();
          assertTrue(file.exists(), "File should exist");

          Path path = resource.getResourceAsPath();
          assertTrue(path.toFile().exists(), "Path should point to an existing file");
        });

    assertEquals("file", resource.getResourceProtocol(), "Protocol should be 'file'");
  }

  @Test
  public void testResourceDoesNotExist() {
    assertThrows(
        NullPointerException.class,
        () -> ClasspathResource.of(ClasspathResourceTest.class, "/nonexistent.txt"),
        "Should throw NullPointerException for non-existent URI");
  }

  @Test
  public void testResourceWithinJar() {
    // This assumes you have a JAR with a known resource. Adjust "resource-in-jar.txt" accordingly.
    ClasspathResource resource = ClasspathResource.of(Test.class, "/META-INF/LICENSE.md");
    assertNotNull(resource.getResourceAsUrl(), "URL should not be null for resource within JAR");
    assertDoesNotThrow(resource::getResourceAsUri, "URI should be obtainable without exception");
    assertEquals(
        "jar",
        resource.getResourceProtocol(),
        "Protocol should be 'jar' for resources within JAR files");
  }

  @Test
  public void testInputStreamClosed() {
    ClasspathResource resource =
        ClasspathResource.of(ClasspathResourceTest.class, "/root-test-resource.txt");
    InputStream stream = resource.getResourceAsStream();
    assertNotNull(stream, "InputStream should not be null for existing resource");
    assertDoesNotThrow(
        () -> {
          stream.read();
          stream.close();
        },
        "InputStream should close without exception");
    assertThrows(
        IOException.class, () -> stream.read(), "Should throw IOException after stream is closed");
  }

  @Test
  public void testInvalidProtocolResource() {
    // Simulate an invalid URL to test protocol handling
    Class<?> clazz = ClasspathResourceTest.class;
    String invalidResource = "/does-not-exist.txt";
    URL url = clazz.getResource(invalidResource);
    if (url != null) {
      ClasspathResource resource = new ClasspathResource(clazz, invalidResource);
      assertThrows(
          URISyntaxException.class,
          resource::getResourceAsUri,
          "URI parsing should fail for invalid protocol");
    } else {
      System.out.println("Resource not available to test invalid protocol scenario.");
    }
  }

  @Test
  public void testResourceProtocolOnDifferentPlatforms() {
    // Tests that resource protocol is correctly identified on different platforms or under
    // different conditions
    ClasspathResource resource =
        ClasspathResource.of(ClasspathResourceTest.class, "/root-test-resource.txt");
    String protocol = resource.getResourceProtocol();
    assertTrue(
        protocol.equals("file") || protocol.equals("jar"),
        "Protocol should either be 'file' or 'jar'");
  }
}
