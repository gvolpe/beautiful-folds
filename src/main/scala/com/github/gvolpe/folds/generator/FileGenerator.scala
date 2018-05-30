package com.github.gvolpe.folds.generator

import java.nio.file.Paths

import cats.effect.IO
import com.github.gvolpe.folds.IOApp
import fs2._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.Random

object FileGenerator extends IOApp {

  override def start(args: List[String]): IO[Unit] = {
    val source = Stream.eval(IO(Random.nextInt(1000).toString  ++ "\n")).repeat.take(10000)
    val sink = (n: Int) => io.file.writeAll[IO](Paths.get(s"src/main/resources/file_$n"))

    Stream(
      source.through(text.utf8Encode).to(sink(1)),
      source.through(text.utf8Encode).to(sink(2)),
      source.through(text.utf8Encode).to(sink(3)),
      source.through(text.utf8Encode).to(sink(4)),
      source.through(text.utf8Encode).to(sink(5))
    ).join(5).compile.drain
  }
}
