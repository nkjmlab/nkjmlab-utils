package org.nkjmlab.util.java.lang;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ResourceUtilsTest {

  @Test
  void test() {
    assertThat(ResourceUtils.getRootCodeSourceLocation(ResourceUtils.class).toString())
        .contains("nkjmlab-utils/nkjmlab-utils/nkjmlab-utils-core/target/classes/");
    assertThat(ResourceUtils.getCodeSourceLocation(ResourceUtils.class).toString())
        .contains(
            "nkjmlab-utils/nkjmlab-utils/nkjmlab-utils-core/target/classes/org/nkjmlab/util/java/lang/");
  }

  @Test
  public void testReadAllLines() {
    List<String> lines =
        ResourceUtils.readAllLines(ResourceUtilsTest.class, "/root-test-resource.txt");
    assertFalse(lines.isEmpty());
    assertTrue(lines.contains("Hello World"));
  }

  @Test
  public void testReadAllLinesWithCharset() {
    List<String> lines =
        ResourceUtils.readAllLinesFromResource(
            ResourceUtilsTest.class, "/root-test-resource.txt", StandardCharsets.UTF_8);
    assertFalse(lines.isEmpty());
    assertTrue(lines.contains("Hello World"));
  }

  @Test
  public void testCopyResourceToTempDir(@TempDir File tempDir) throws Exception {
    File copiedFile =
        ResourceUtils.copyResourceToTempDir(ResourceUtilsTest.class, "/root-test-resource.txt");
    assertTrue(copiedFile.exists());
    assertTrue(copiedFile.isFile());
  }

  @Test
  public void testReadAllByteFromResource() {
    byte[] data =
        ResourceUtils.readAllByteFromResource(ResourceUtilsTest.class, "/root-test-resource.txt");
    assertNotNull(data);
    assertTrue(data.length > 0);
  }

  @Test
  public void testGetResourceAsFile() {
    File resourceFile =
        ResourceUtils.getResourceAsFile(ResourceUtilsTest.class, "/root-test-resource.txt");
    assertTrue(resourceFile.exists());
  }

  @Test
  public void testGetResourceAsUri() throws Exception {
    URI resourceUri =
        ResourceUtils.getResourceAsUri(ResourceUtilsTest.class, "/root-test-resource.txt");
    assertNotNull(resourceUri);
  }

  @Test
  public void testGetResourceRootAsFile() {
    File rootResource = ResourceUtils.getResourceRootAsFile();
    assertTrue(rootResource.exists());
  }

  @Test
  public void testGetCodeSourceLocation() throws URISyntaxException {
    URI codeSource = ResourceUtils.getCodeSourceLocation(ResourceUtilsTest.class).toURI();
    assertNotNull(codeSource);
    assertTrue(codeSource.toString().contains("/"));
  }

  @Test
  public void testGetRootCodeSourceLocation() throws URISyntaxException {
    URI rootCodeSource = ResourceUtils.getRootCodeSourceLocation(ResourceUtilsTest.class).toURI();
    assertNotNull(rootCodeSource);
    assertTrue(rootCodeSource.toString().contains("/"));
  }
}
