package space.dehun

import cats._
import cats.effect._
import cats.implicits._

class Logger {
  def log(msg:String):Unit = Console.println(s"log: ${msg}")
}

trait LogAlg[F[_]] {
  def log(msg:String):F[Unit]
}

object LogAlg {
  def apply[F[_]](implicit logAlg:LogAlg[F]) = logAlg
}


