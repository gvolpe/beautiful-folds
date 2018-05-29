package com.github.gvolpe.folds.benchmarks

import java.util.concurrent.TimeUnit

import cats.instances.list._
import org.atnos.origami.syntax.foldable._
import org.openjdk.jmh.annotations._

@State(Scope.Thread)
@BenchmarkMode(Array(Mode.AverageTime))
@OutputTimeUnit(TimeUnit.MILLISECONDS)
class ListFoldingBenchmark {

  @Benchmark
  def computeStats(): StatsResult = {
    (0 to RangeTop).toList.foldWith(stats).unsafeRunSync()
  }

}
