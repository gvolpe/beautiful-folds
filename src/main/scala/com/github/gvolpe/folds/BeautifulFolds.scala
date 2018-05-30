package com.github.gvolpe.folds

import java.nio.file.Paths

import cats.effect.IO
import cats.instances.list._
import cats.syntax.parallel._
import fs2.{Pipe, Stream, io, text}
import org.atnos.origami.Fold
import org.atnos.origami.addon.fs2.stream._
import org.atnos.origami.folds._
import org.atnos.origami.syntax.foldable._

import scala.concurrent.ExecutionContext.Implicits.global

object BeautifulFolds extends IOApp {

  type StatsResult = ((Int, Int), Double)

  val stats: Fold[IO, Int, StatsResult] =
    (minimumOr(0) <*> maximumOr(Int.MaxValue) <*> averageDouble).into[IO]

  val intParser: Pipe[IO, String, Int] = _.evalMap { x =>
    IO(x.toInt).handleErrorWith(_ => IO.pure(1))
  }

  private val fileReader = (n: Int) => {
    io.file.readAll[IO](Paths.get(s"src/main/resources/file_$n"), 4096)
      .through(text.utf8Decode)
      .through(text.lines)
      .dropLast
      .map(_.toInt)
  }

  val source: Stream[IO, Int] = Stream(
    fileReader(1),
    fileReader(2),
    fileReader(3),
    fileReader(4),
    fileReader(5)
  ).join(5)

  val parallelStatsComputation: IO[Unit] = {
    val s1 = fileReader(1).foldWith(stats)
    val s2 = fileReader(2).foldWith(stats)
    val s3 = fileReader(3).foldWith(stats)
    val s4 = fileReader(4).foldWith(stats)
    val s5 = fileReader(5).foldWith(stats)

    val ioa: IO[List[StatsResult]] = List(s1, s2, s3, s4, s5).parSequence

    val iob: IO[StatsResult] = ioa.flatMap { list =>
      val minimum = list.map { case ((min, _), _) => min }
      val maximum = list.map { case ((_, max), _) => max }
      val average = list.map { case ((_, _), avg) => avg }

      val x = minimum.foldWith(minimumOr(0))
      val y = maximum.foldWith(maximumOr(Int.MaxValue))
      val z = average.foldWith(averageDouble)

      IO.pure(((x, y), z))
    }

    iob.flatMap(x => putStrLn(x.toString()))
  }

  // Result should be: ((0,999),499.41132)
  override def start(args: List[String]): IO[Unit] = {
//    source.foldWith(stats).flatMap(x => putStrLn(x.toString()))
    parallelStatsComputation
  }

}