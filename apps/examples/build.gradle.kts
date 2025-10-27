plugins { application }
dependencies { implementation(project(":libs:graph")) }
application { mainClass.set("edu.utexas.ece.examples.MainShortestPathDemo") }
