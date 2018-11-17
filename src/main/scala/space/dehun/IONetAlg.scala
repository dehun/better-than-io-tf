package space.dehun

import cats.effect.IO

class IONetAlg(net:Net) extends NetAlg[IO] {
  override def notifyUserChange(user: DB.User): IO[Unit] = net.notifyUserChange(user)
}

object IONetAlg {
  def impl(net:Net) = new IONetAlg(net)
}
