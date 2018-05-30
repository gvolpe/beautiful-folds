package com.github.gvolpe.folds.benchmarks

import java.util.concurrent.TimeUnit

import cats.effect.IO
import cats.instances.list._
import cats.syntax.parallel._
import fs2.Stream
import org.atnos.origami.addon.fs2.stream._
import org.atnos.origami.folds.{averageDouble, maximumOr, minimumOr}
import org.atnos.origami.syntax.foldable._
import org.openjdk.jmh.annotations._

import scala.concurrent.ExecutionContext.Implicits.global

@State(Scope.Thread)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
class Fs2StreamFoldingBenchmark {

  @Benchmark
  def computeStats(): StatsResult = {
    Stream.range(0, RangeTop).covary[IO].foldWith(stats).unsafeRunSync()
  }

  @Benchmark
  def computeStatsConcurrently(): StatsResult = {
    val top = RangeTop / 5

    val s1: IO[StatsResult] = Stream.range(0, top).covary[IO].foldWith(stats)
    val s2: IO[StatsResult] = Stream.range(top, top * 2).covary[IO].foldWith(stats)
    val s3: IO[StatsResult] = Stream.range(top * 2, top * 3).covary[IO].foldWith(stats)
    val s4: IO[StatsResult] = Stream.range(top * 3, top * 4).covary[IO].foldWith(stats)
    val s5: IO[StatsResult] = Stream.range(top * 4, RangeTop).covary[IO].foldWith(stats)

    val ioa: IO[List[((Int, Int), Double)]] = List(s1, s2, s3, s4, s5).parSequence

    val result = ioa.flatMap { list =>
      val mins = list.map { case ((x, _), _) => x }
      val maxs = list.map { case ((_, x), _) => x }
      val avgs = list.map { case ((_, _), x) => x }

      val min = mins.foldWith(minimumOr(0))
      val max = maxs.foldWith(maximumOr(Int.MaxValue))
      val avg = avgs.foldWith(averageDouble)

      IO.pure(((min, max), avg))
    }

    result.unsafeRunSync()
  }

}
