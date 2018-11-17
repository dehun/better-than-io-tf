package space.dehun

import cats.effect.IO

class IOLogAlg(logger:Logger) extends LogAlg[IO] {
  override def log(msg: String): IO[Unit] = IO { logger.log(msg) }
}

object IOLogAlg {
  def impl(logger:Logger) = new IOLogAlg(logger)
}
