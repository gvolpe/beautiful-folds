package com.github.gvolpe.folds

import cats.effect.IO
import cats.{Id, ~>}

trait IOApp {
  def start(args: List[String]): IO[Unit]
  def main(args: Array[String]): Unit = start(args.toList).unsafeRunSync()
  def putStrLn(str: String): IO[Unit] = IO(println(str))

  implicit val idToIO: (Id ~> IO) = new (Id ~> IO) {
    override def apply[A](fa: Id[A]): IO[A] = IO.pure(fa)
  }
}
