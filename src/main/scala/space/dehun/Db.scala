package space.dehun

import cats._
import cats.implicits._
import cats.effect._
import space.dehun.DB.User

object DB {
  case class User(userId:String, nickName:String, age:Int)
}

class DB {
  import DB._
  def queryUser(userId:String):IO[User] = IO {
    Console.println(s"db:querying user ${userId}")
    User("123", "mr. abc", 32)
  }

  def storeUser(newUser:User):IO[Unit] = IO {
    Console.println(s"db:storing user ${newUser}")
  }
}

trait DbAlg[F[_]] {
  def queryUser(userId:String):F[User]
  def queryUserEither(userId:String):F[Either[String, User]]
  def storeUser(user:User):F[Unit]
}

object DbAlg {
  def apply[F[_]](implicit dbAlg:DbAlg[F]) = dbAlg
}
