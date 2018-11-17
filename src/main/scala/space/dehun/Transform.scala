package space.dehun

import cats.~>
import cats.data.{EitherT, OptionT}
import cats.{Functor, Monad}

object Transform {
  class TransformIntoEitherT[L, F[_] : Functor] extends ~>[F, EitherT[F, L, ?]] {
    override def apply[A](fa: F[A]): EitherT[F, L, A] = EitherT.liftF[F, L, A](fa)
  }

  class TransformIntoOptionT[F[_] : Functor] extends ~>[F, OptionT[F, ?]] {
    override def apply[A](fa: F[A]): OptionT[F, A] = OptionT.liftF(fa)
  }
}

class NatOps[F[_]:Functor, A](a:F[A]) {
  def nat[G[_]:Functor]()(implicit n: F ~> G): G[A] = {
    n(a)
  }
}

object NatOps {
  implicit def toNatOps[F[_]:Functor, A](a:F[A]):NatOps[F, A] = new NatOps(a)
}