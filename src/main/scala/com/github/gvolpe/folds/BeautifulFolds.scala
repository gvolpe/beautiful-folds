package com.github.gvolpe.folds

import cats.effect.IO
import cats.{Id, ~>}
import fs2.Stream
import org.atnos.origami.Fold
import org.atnos.origami.addon.fs2.stream._
import org.atnos.origami.folds._

object BeautifulFolds extends App {

  def putStrLn(str: String): IO[Unit] = IO(println(str))

  implicit val idToIO = new (Id ~> IO) {
    override def apply[A](fa: Id[A]): IO[A] = IO.pure(fa)
  }

  type StatsResult = ((Int, Int), Double)

  val stats: Fold[IO, Int, StatsResult] =
    (minimumOr(0) <*> maximumOr(Int.MaxValue) <*> averageDouble).into[IO]

  val source = Stream.emits((1 to 10).toList).covary[IO]

  val program = source.foldWith(stats).flatMap(x => putStrLn(x.toString()))

  program.unsafeRunSync()

}
