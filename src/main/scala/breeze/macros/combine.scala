package breeze.macros

import scala.language.experimental.macros
import scala.reflect.macros.whitebox.Context

/**
 * breeze-macros
 * 7/20/14
 * @author Gabriel Schubiner <gabeos@cs.washington.edu>
 *
 *
 */
object CombineSpaceImplicits {

  def combine[MA, MB](spaceA: MA, spaceB: MB) = macro combineImpl[MA, MB]

  def combineImpl[MA: c.WeakTypeTag, MB: c.WeakTypeTag](c: Context)(spaceA: c.Expr[MA], spaceB: c.Expr[MB]): c.Expr[Any] = {
    import c.{universe => cuni}
    import reflect.runtime.{universe => uni}
    val rMirr = uni.runtimeMirror(uni.getClass.getClassLoader)

    rMirr.reflectClass(uni.symbolOf[MA].asClass)
    val aTPars = rMirr.classSymbol(rMirr.runtimeClass(uni.symbolOf[MA].asClass)).typeParams
    val aMemb = rMirr.weakTypeOf[MA].members.filter(m => m.isImplicit && m.isMethod).map(_.asMethod)
    val dds = aMemb.map { m =>
      val tPars = m.typeParams.map(t => t.asType)
      val parLists = m.paramLists.map(p => p.map(ps => {
//        val pName = ps.name.toTermName //uni.reify(ps.name.toTermName)
//        val pTypeSignature = ps.typeSignature // uni.reify(ps.typeSignature)
//        val typeSigTree = c.parse(s"val $pName: $pTypeSignature") //uni.ValDef(uni.Modifiers(uni.Flag.PARAM), pName.splice, pTypeSignature.splice, uni.EmptyTree)
        val pName = uni.reify(ps.name.toTermName)
        val pTypeSignature = uni.(ps.typeSignature)
        val typeSigTree = c.parse(s"val $pName: $pTypeSignature") //uni.ValDef(uni.Modifiers(uni.Flag.PARAM), pName.splice, pTypeSignature.splice, uni.EmptyTree)
      }))
      val returnType = m.returnType
      val mName = m.name
      val callParams = m.paramLists.map(p => p.map(ps => {
        val pName = ps.name.toTermName
      }))
//      c.parse()
    }

      //        q"$pName"
      //      }))
      //      q""" implicit def ${c.freshName(s"${m.name}_A")}[..$tPars](...$parLists): ${m.returnType} = ${rMirr.reflect(spaceA.value).reflectMethod(m).receiver}"""}

      //    val aTT = implicitly[c.WeakTypeTag[MA]]
      //    c.symbolOf[MA]
      //    val aMem = aTT.tpe.members.filter(m => m.isImplicit && m.isMethod).map(_.asMethod) //.map(m => c.universe.Apply(m))
      //    val bMem = implicitly[c.WeakTypeTag[MB]].tpe.members.filter(m => m.isImplicit && m.isMethod).map(_.asMethod) //.map(m => c.universe.Apply(m))
      //    val mirr = reflect.runtime.universe.runtimeMirror(this.getClass.getClassLoader)
      //    println(s"aMem: $aMem")
      //    println(s"bMem: $bMem")
      //    println(s"psts: ${tq"${aMem.head.paramLists.head.head.typeSignature}"}")
      //
      //
      //
      //    val dds = aMem.map { m => {
      //      val fName = c.freshName(m.name)
      //      val tPars = m.typeParams.map(t => tq"$t")
      //      val pLists: List[List[Tree]] = m.paramLists.map(p => p.map(ps => {
      //        val pName = ps.name.toTermName
      //        val pTypeSignature: Type = ps.typeSignature
      //        val typeSigTree: Tree = tq"$pTypeSignature"
      //        q"val $pName: $typeSigTree"
      //      }))
      //      val returnType: Type = m.returnType
      //      val mName: TermName = m.name
      //      val callParams: List[List[Tree]] = m.paramLists.map(p => p.map(ps => {
      //        val pName: TermName = ps.name.toTermName
      //        q"$pName"
      //      }))
      //      println(s"fName: $fName")
      //      println(s"tPars: $tPars")
      //      println(s"pLists: $pLists")
      //      println(s"returnType: $returnType")
      //      println(s"mName: $mName")
      //      println(s"callParams: $callParams")
      //      val aTerm = spaceA.tree.symbol.asTerm
      //      q"""implicit def $fName[..$tPars](...$pLists): $returnType = $aTerm.$mName(...$callParams)"""
      //    }
      //      //      case mdef@DefDef(mods, name, tparams, params, tpt, rhs) =>
      //      //            reify(DefDef(mods, s"${c.freshName(s"${name}_A)}")}", tparams, params, tpt, rhs)).tree
      //    }
      //    ++ bMem.map(m => reify(m).tree).map {
      //      case mdef@DefDef(mods, name, tparams, params, tpt, rhs) =>
      //        reify(DefDef(mods, s"${c.freshName(s"${name}_A)}")}", tparams, params, tpt, rhs)).tree
      //    }
      //    println(s"ImplMethods: ${aT.members.filter(m => m.isImplicit && m.isMethod).map(_.asMethod)}")
      //    val dds = aT.members.filter(m => m.isImplicit && m.isMethod).map(_.asMethod).map({ case DefDef(mods, name, tparams, params, tpt, rhs) =>
      //      reify(DefDef(mods, s"${c.freshName(s"${name}_A)}")}", tparams, params, tpt, rhs)).tree
      //    }).toList ++
      //      bT.members.filter(m => m.isImplicit && m.isMethod).map(_.asMethod).map({ case DefDef(mods, name, tparams, params, tpt, rhs) =>
      //        reify(DefDef(mods, s"${c.freshName(s"${name}_A)}")}", tparams, params, tpt, rhs)).tree
      //      })
      //
      //    dds.foreach(dd => println(showRaw(dd)))

      //    val t = q"""new {..$dds}"""
      //    println(showRaw(t))
      //    println(showCode(t))
      //    c.Expr(t)
      spaceA
    }
    //
    //  def getRuntimeMethodDetails(cls: Expr[MA], c: Context) {
    //
    //  }
  }
