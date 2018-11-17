package space.dehun

import cats.Monad
import cats._
import cats.implicits._
import cats.effect._

class Net {
  def notifyUserChange(user:DB.User):IO[Unit] = IO {
    Console.println(s"net:notifying user change ${user}")
  }
}

trait NetAlg[F[_]] {
  def notifyUserChange(user:DB.User):F[Unit]
}

object NetAlg {
  def apply[F[_]](implicit netAlg:NetAlg[F]) = netAlg
}
