package breeze.macros

import org.scalatest.FunSuite

/**
 * breeze-macros
 * 7/21/14
 * @author Gabriel Schubiner <gabeos@cs.washington.edu>
 *
 *
 */
class combineTest extends FunSuite {

  trait Base[BASE] {
    implicit def baseMethod(a: BASE)(implicit num: Numeric[BASE]): Int
  }

  trait subA[A] extends Base[A] {
    implicit def aMethod(a: A): (A,A) => A
  }

  trait subB[B] extends Base[B] {
    implicit def bMethod(implicit num: Numeric[B]) = num.times _
    override implicit def baseMethod(b: B)(implicit num: Numeric[B]): Int
  }

  object subA {
    def make[A](implicit num: Numeric[A]) = new subA[A] {
      implicit def aMethod(a: A): (A,A) => A = num.times

      implicit def baseMethod(a: A)(implicit num: Numeric[A]): Int = num.toInt(a)
    }
  }

  object subB {
    def make[B](implicit num: Numeric[B]) = new subB[B] {
      override implicit def baseMethod(b: B)(implicit num: Numeric[B]): Int = num.toInt(b) + 1
    }
  }

  test("Can Combine Traits") {
    val aTest = subA.make[Double]
    val bTest = subB.make[Float]
    val c = CombineSpaceImplicits.combine[subA[Double],subB[Float]](aTest,bTest)
  }
}
