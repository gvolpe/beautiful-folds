import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.example",
      scalaVersion := "2.12.6",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "beautiful-folds",
    scalacOptions := Seq(
      "-deprecation",
      "-encoding",
      "UTF-8",
      "-feature",
      "-language:existentials",
      "-language:higherKinds",
      "-language:implicitConversions",
      "-language:experimental.macros",
      "-unchecked",
      //"-Xfatal-warnings",
      "-Ypartial-unification",
      "-Xlint",
      "-Yno-adapted-args",
      "-Ywarn-dead-code",
      "-Ywarn-value-discard",
      "-Xfuture",
      "-Xlog-reflective-calls",
      "-Ywarn-inaccessible",
      "-Ypatmat-exhaust-depth",
      "20",
      "-Ydelambdafy:method",
      "-Xmax-classfile-name",
      "100"
    ),
    addCompilerPlugin(Libraries.kindProjector),
    resolvers += Resolver.sonatypeRepo("snapshots"),
    libraryDependencies ++= Seq(
      Libraries.cats,
      Libraries.catsEffect,
      Libraries.fs2Core,
      Libraries.fs2io,
      Libraries.origamiCore,
      Libraries.origamiLib,
      Libraries.origamiFs2,
      Libraries.scalaTest % Test
    )
  )

enablePlugins(JmhPlugin)
