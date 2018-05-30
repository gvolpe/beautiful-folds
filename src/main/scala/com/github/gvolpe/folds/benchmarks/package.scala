package com.github.gvolpe.folds

import cats.{Id, ~>}
import cats.effect.IO
import org.atnos.origami.Fold
import org.atnos.origami.folds._

package object benchmarks {

  type StatsResult = ((Int, Int), Double)

  /**
    * 10^6 == one million
    * 10^7 == ten million
    * 10^8 == hundred million
    * */
  val RangeTop: Int = 1 * math.pow(10, 6).toInt

  implicit val idToIO: (Id ~> IO) = new (Id ~> IO) {
    override def apply[A](fa: Id[A]): IO[A] = IO.pure(fa)
  }

  val stats: Fold[IO, Int, StatsResult] =
    (minimumOr(0) <*> maximumOr(Int.MaxValue) <*> averageDouble).into[IO]

}
