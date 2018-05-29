package com.github.gvolpe.folds.benchmarks

import java.util.concurrent.TimeUnit

import cats.effect.IO
import fs2.Stream
import org.atnos.origami.addon.fs2.stream._
import org.openjdk.jmh.annotations._

@State(Scope.Thread)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
class Fs2StreamFoldingBenchmark {

  @Benchmark
  def computeStats(): StatsResult = {
    Stream.range(0, RangeTop).covary[IO].foldWith(stats).unsafeRunSync()
  }

}
