package space.dehun

import cats.~>
import cats._
import cats.data.EitherT
import cats.implicits._
import cats.effect.{Effect, IO}
import NatOps._

object BetterThanIoMain extends App {
  def foo[F[_]:Monad:DbAlg:LogAlg](x:Int):F[DB.User] = for {
    user <- DbAlg[F].queryUser(x.toString)
    _ <- LogAlg[F].log(s"got user ${user}")
    _ <- DbAlg[F].storeUser(user.copy(age=user.age + 1))
  } yield user

  def bar[F[_]:Monad:NetAlg:LogAlg](user:DB.User):F[Int] = for {
    _ <- LogAlg[F].log("lets notify user change!")
    _ <- NetAlg[F].notifyUserChange(user)
  } yield user.age

  def xyz[F[_]:Monad:DbAlg:LogAlg](x:Int):F[Either[String, DB.User]] = {
    implicit val intoEitherT = new Transform.TransformIntoEitherT[String, F]
    for {
      _ <- LogAlg[F].log("hello world").nat[EitherT[F, String, ?]]
      user <- EitherT[F, String, DB.User](DbAlg[F].queryUserEither(x.toString))
      _ <- LogAlg[F].log("hello world again").nat[EitherT[F, String, ?]]
      _ <- DbAlg[F].storeUser(user.copy(age = user.age + 1)).nat[EitherT[F, String, ?]]
    } yield user
  }.value

  override def main(args: Array[String]): Unit = {
    val logger:Logger = new Logger()
    val db:DB = new DB()
    val net:Net = new Net()

    implicit val ioLogAlg = IOLogAlg.impl(logger)
    implicit val ioDbAlg = IODbAlg.impl(db)
    implicit val ioNetAlg = IONetAlg.impl(net)

    ( foo[IO](12) >>= bar[IO] >>= xyz[IO]).unsafeRunSync()
  }
}
