package org.nkjmlab.util.java.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.nkjmlab.util.java.function.Try;

public class FileUtils {

  public static Stream<String> lines(Path path) {
    return lines(path, StandardCharsets.UTF_8);
  }

  public static Stream<String> lines(Path path, Charset cs) {
    try {
      return Files.lines(path, cs);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static List<File> listFiles(File dir) {
    return listFiles(dir, Integer.MAX_VALUE, p -> true);
  }

  public static List<File> listFiles(File dir, int maxDepth, Predicate<Path> filter) {
    try (Stream<Path> stream = Files.walk(dir.toPath(), maxDepth)) {
      return stream.filter(p -> filter.test(p)).map(p -> p.toFile()).collect(Collectors.toList());
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static BufferedReader newBufferedReader(Path path) {
    return newBufferedReader(path, StandardCharsets.UTF_8);
  }

  public static BufferedReader newBufferedReader(Path path, Charset cs) {
    try {
      return Files.newBufferedReader(path, cs);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static BufferedWriter newBufferedWriter(Path path, Charset cs, OpenOption... options) {
    try {
      return Files.newBufferedWriter(path, cs, options);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static BufferedWriter newBufferedWriter(Path path, OpenOption... options) {
    return newBufferedWriter(path, StandardCharsets.UTF_8, options);
  }

  /**
   * Getting a file reader of {@code file}
   *
   * @param file
   * @return
   */
  public static FileReader newFileReader(File file) {
    try {
      return new FileReader(file);
    } catch (FileNotFoundException e) {
      throw Try.rethrow(e);
    }
  }

  /**
   * Getting a file reader of {@code fileName}
   *
   * @param fileName
   * @return
   */
  public static FileReader newFileReader(String fileName) {
    return newFileReader(new File(fileName));
  }

  /**
   * Getting a file writer of {@code file}
   *
   * @param file
   * @return
   */
  public static FileWriter newFileWriter(File file) {
    return newFileWriter(file, false);
  }

  /**
   * Getting a file writer of {@code file} with the option of {@code append}.
   *
   * @param file
   * @param append
   * @return
   */
  public static FileWriter newFileWriter(File file, boolean append) {
    try {
      return new FileWriter(file, append);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  /**
   * Getting a file writer of {@code fileName}
   *
   * @param fileName
   * @return
   */
  public static FileWriter newFileWriter(String fileName) {
    return newFileWriter(new File(fileName), false);
  }

  public static byte[] readAllBytes(Path path) {
    try {
      return Files.readAllBytes(path);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static List<String> readAllLines(Path path) {
    return readAllLines(path, StandardCharsets.UTF_8);
  }

  public static List<String> readAllLines(Path path, Charset cs) {
    try {
      return Files.readAllLines(path, cs);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static Path write(Path path, byte[] bytes, OpenOption... options) {
    try {
      return Files.write(path, bytes, options);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static Path write(Path path, Iterable<? extends CharSequence> lines, Charset cs,
      OpenOption... options) {
    try {
      return Files.write(path, lines, cs, options);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }

  public static Path write(Path path, Iterable<? extends CharSequence> lines,
      OpenOption... options) {
    return write(path, lines, StandardCharsets.UTF_8, options);
  }

  public static Path write(Path path, String line, Charset cs, OpenOption... options) {
    try {
      return Files.write(path, Arrays.asList(line), cs, options);
    } catch (IOException e) {
      throw Try.rethrow(e);
    }
  }


  public static Path write(Path path, String line, OpenOption... options) {
    return write(path, Arrays.asList(line), StandardCharsets.UTF_8, options);
  }


}
