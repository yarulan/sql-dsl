package sql.dsl.codegen

import java.io.{File, OutputStream}
import java.nio.file.{Files, Path, StandardOpenOption}

trait Writer {
  def init()
  def getStream(fileName: String): OutputStream
}

class FileSystemWriter(dir: Path) extends Writer {
  private def clean(dir: File): Unit = {
    val content = dir.listFiles()
    content.filter(_.isDirectory).foreach(clean)
    content.foreach(file => Files.delete(file.toPath))
  }

  override def init(): Unit = {
    if (Files.exists(dir)) {
      clean(dir.toFile)
    } else {
      Files.createDirectory(dir)
    }
  }

  override def getStream(fileName: String): OutputStream = {
    Files.newOutputStream(dir.resolve(fileName), StandardOpenOption.CREATE_NEW)
  }
}

object ConsoleWriter extends Writer {
  override def init(): Unit = {}

  override def getStream(fileName: String): OutputStream = {
    System.out
  }
}
