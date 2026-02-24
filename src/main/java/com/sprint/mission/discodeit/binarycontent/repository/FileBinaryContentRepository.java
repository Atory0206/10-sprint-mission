package com.sprint.mission.discodeit.binarycontent.repository;

import com.sprint.mission.discodeit.binarycontent.entity.BinaryContent;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;


public class FileBinaryContentRepository implements BinaryContentRepository {

  private final Path DIRECTORY;
  private final String EXTENSION = ".ser";

  public FileBinaryContentRepository(String baseDir) {
    this.DIRECTORY = Paths.get(System.getProperty("user.dir"), baseDir,
        BinaryContent.class.getSimpleName());
    if (Files.notExists(DIRECTORY)) {
      try {
        Files.createDirectories(DIRECTORY);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  private Path resolvePath(UUID id) {
    return DIRECTORY.resolve(id + EXTENSION);
  }


  @Override
  public BinaryContent save(BinaryContent binaryContent) {
    Path path = resolvePath(binaryContent.getId());
    try (
        FileOutputStream fos = new FileOutputStream(path.toFile());
        ObjectOutputStream oos = new ObjectOutputStream(fos)
    ) {
      oos.writeObject(binaryContent);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return binaryContent;
  }

  @Override
  public Optional<BinaryContent> findById(UUID id) {
    BinaryContent binaryContentNullable = null;
    Path path = resolvePath(id);
    if (Files.exists(path)) {
      try (
          FileInputStream fis = new FileInputStream(path.toFile());
          ObjectInputStream ois = new ObjectInputStream(fis)
      ) {
        binaryContentNullable = (BinaryContent) ois.readObject();
      } catch (IOException | ClassNotFoundException e) {
        throw new RuntimeException(e);
      }
    }
    return Optional.ofNullable(binaryContentNullable);
  }

  @Override
  public List<BinaryContent> findAllByIdIn(List<UUID> ids) {
    return ids.stream()
        .map(this::resolvePath)
        .filter(Files::exists)
        .map(path -> {
          try (
              FileInputStream fis = new FileInputStream(path.toFile());
              ObjectInputStream ois = new ObjectInputStream(fis)
          ) {
            return (BinaryContent) ois.readObject();
          } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("파일 읽기 실패: " + path, e);
          }
        })
        .toList();
  }


  @Override
  public void deleteById(UUID id) {
    Path path = resolvePath(id);
    try {
      Files.delete(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
