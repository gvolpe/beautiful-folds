package com.github.gvolpe.folds

import cats.effect.IO
import com.github.gvolpe.folds.benchmarks.RangeTop
import fs2.Stream
import org.atnos.origami.Fold
import org.atnos.origami.addon.fs2.stream._
import org.atnos.origami.folds._

object BeautifulFolds extends IOApp {

  type StatsResult = ((Int, Int), Double)

  val stats: Fold[IO, Int, StatsResult] =
    (minimumOr(0) <*> maximumOr(Int.MaxValue) <*> averageDouble).into[IO]

  val source: Stream[IO, Int] = Stream.range(0, RangeTop).covary[IO]

  override def start(args: List[String]): IO[Unit] = {
    source.foldWith(stats).flatMap(x => putStrLn(x.toString()))
  }

}