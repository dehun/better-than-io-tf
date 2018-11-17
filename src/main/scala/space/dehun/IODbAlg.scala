package space.dehun

import cats.effect.IO

class IODbAlg(db:DB) extends DbAlg[IO] {
  override def queryUser(userId: String): IO[DB.User] = db.queryUser(userId)
  override def storeUser(user: DB.User): IO[Unit] = db.storeUser(user)
  override def queryUserEither(userId: String): IO[Either[String, DB.User]] = db.queryUser(userId).map(Right[String, DB.User])
}

object IODbAlg {
  def impl(db:DB) = new IODbAlg(db)
}
