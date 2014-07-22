package breeze.macros

import scala.language.experimental.macros
import scala.reflect.macros.whitebox.Context
import scala.annotation.{StaticAnnotation, Annotation}

/**
 * breeze-macros
 * 7/16/14
 * @author Gabriel Schubiner <gabeos@cs.washington.edu>
 *
 *
 */
class companion extends Annotation with StaticAnnotation {
  def macroTransform(annottees: Any*) = macro companion.generateCompanion
}

object companion {
  def generateCompanion(c: Context)(annottees: c.Expr[Any]*):c.Expr[Any] = {
    import c.mirror.universe._
    annottees.head.tree match {
      case tree@ClassDef(mods,name,tparams,impl) => ???
        // Get super-types
        // Get inherited defs
        // Get hasOps type

        // generate companion object
        // generate make method with generic types,
        // inherited types as implicit args,
        // constructor for trait (new X[GA] = {}
        // implicit defs for all args
      case _ => ???
    }
  }
}
