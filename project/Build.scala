// See the LICENCE.txt file distributed with this work for additional
// Copyright (C) 2011-2012 the original author or authors.
// information regarding copyright ownership.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

import sbt.Keys._
import sbt._

//import sbtassembly.Plugin._
//import AssemblyKeys._

object Build extends Build {

  lazy val root = Project("root", file("."), settings = rootSettings) aggregate(core, examples)

  lazy val core = Project("core", file("core"), settings = coreSettings)

  lazy val examples = Project("examples", file("examples"), settings = examplesSettings) dependsOn (core)

  def sharedSettings = Defaults.defaultSettings ++ Seq(
    organization := "com.isjay",
    version := "0.0.1",
    scalaVersion := "2.11.6",
    unmanagedJars in Compile <<= baseDirectory map { base => (base / "lib" ** "*.jar").classpath },
    retrieveManaged := true,

    libraryDependencies ++= Seq(
    ),

    parallelExecution := false,

    /* Workaround for issue #206 (fixed after SBT 0.11.0) */
    watchTransitiveSources <<= Defaults.inDependencies[Task[Seq[File]]](watchSources.task,
      const(std.TaskExtra.constant(Nil)), aggregate = true, includeRoot = true) apply {
      _.join.map(_.flatten)
    }

  )


  def rootSettings = sharedSettings ++ Seq(
    publish := {}
  )

  val sparkVersion = "1.6.0"
  val scalaVersionPrefix = "_2.11"

  def coreSettings = sharedSettings ++ Seq(
    name := "core",
    resolvers ++= Seq(
      "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
      "Cloudera Repository" at "http://repository.cloudera.com/artifactory/cloudera-repos/"
    ),
    libraryDependencies ++= Seq(
      "com.google.guava" % "guava" % "11.0.1"
      , "com.nativelibs4java" % "javacl" % "1.0.0-RC3"
      , "org.apache.spark" % ("spark-core" + scalaVersionPrefix) % sparkVersion
      , "org.apache.spark" % ("spark-streaming" + scalaVersionPrefix) % sparkVersion
      , "org.apache.spark" % ("spark-sql" + scalaVersionPrefix) % sparkVersion
      , "org.apache.spark" % ("spark-hive" + scalaVersionPrefix) % sparkVersion
      , "org.apache.spark" % ("spark-mllib" + scalaVersionPrefix) % sparkVersion
      , "org.bytedeco" % "javacv" % "1.2"
    )
  )

  //++ extraAssemblySettings


  def examplesSettings = sharedSettings ++ Seq(
    name := "examples"
  )

  /*
   def extraAssemblySettings() = Seq(test in assembly := {}) ++ Seq(
    mergeStrategy in assembly := {
      case m if m.toLowerCase.endsWith("manifest.mf") => MergeStrategy.discard
      case "reference.conf" => MergeStrategy.concat
      case _ => MergeStrategy.first
    }
  )*/

}

