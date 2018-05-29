import sbt._

object Dependencies {

  object Version {
    val cats        = "1.1.0"
    val catsEffect  = "1.0.0-RC2-8ed6e71"
    val fs2         = "0.10.4"
    val origami     = "5.0.0"
    val scalaTest   = "3.0.5"
    val kindProjector = "0.9.5"
  }

  object Libraries {
    def origami(artifact: String): ModuleID = "org.atnos" %% s"origami-$artifact" % Version.origami

    lazy val origamiCore  = origami("core")
    lazy val origamiLib   = origami("lib")
    lazy val origamiFs2   = origami("fs2")

    lazy val cats       = "org.typelevel"         %% "cats-core"    % Version.cats
    lazy val catsEffect = "org.typelevel"         %% "cats-effect"  % Version.catsEffect
    lazy val fs2        = "co.fs2"                %% "fs2-core"     % Version.fs2
    lazy val scalaTest  = "org.scalatest"         %% "scalatest"    % Version.scalaTest

    lazy val kindProjector  = "org.spire-math"        %% "kind-projector"   % Version.kindProjector
  }

}
