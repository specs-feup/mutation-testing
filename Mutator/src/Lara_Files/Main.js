laraImport("lara.Io");
laraImport("Mutators");
laraImport("weaver.Query");

//aspectdef Test
//input outputPath, packageName, outputFolder end

const outputPath = laraArgs.outputPath;
const packageName = laraArgs.packageName;
const outputFolder = laraArgs.outputFolder;

main(outputPath, packageName, outputFolder);

function main(outputPath, packageName, outputFolder) {
  var counter = 0;
  var identifiersList = new Object();

  identifiersList.identifiers = [];

  if (Mutators.length === 0) {
    println("No mutators selected");
    return;
  }

  for (var $jp of Query.root().descendants) {
    var $call = $jp.ancestor("call");

    // Ignore nodes that are children of $call with the name <init>
    if ($call !== undefined && $call.name === "<init>") continue;

    // Ignore nodes inside variable declarations
    //if ($jp.ancestor("localVariable") !== undefined) {
    //  continue;
    //}

    for (mutator of Mutators) {
      if (mutator.addJp($jp)) {
        var fileName =
          $jp.ancestor("file") === undefined
            ? "NOFILENAME"
            : $jp.ancestor("file").name;
        try {
          println(
            "New mutation point of type " +
              mutator.getType() +
              " on: " +
              $jp +
              " file " +
              fileName +
              " line " +
              $jp.line
          );
        } catch (e) {
          try {
            println(
              "New mutation point of type " +
                mutator.getType() +
                " on: " +
                $jp.parent +
                " file " +
                fileName +
                " line " +
                $jp.parent.line
            );
          } catch (ee) {
            println(ee);
          }
        }
      }
    }
  }

  for (mutator of Mutators) {
    while (mutator.hasMutations()) {
      // Mutate
      mutator.mutate();
      // Print
      var identifier = new Object();

      var mutated = mutator.getMutationPoint().isStatement
        ? mutator.getMutationPoint()
        : mutator.getMutationPoint().ancestor("statement");

      println(
        "Ancestor ->  " + mutator.getMutationPoint().ancestor("statement")
      );

      try {
        identifier.file =
          mutated.ancestor("file") === undefined
            ? "NOFILENAME"
            : mutated.ancestor("file").path;
        identifier.line = mutated.line;
        identifier.id =
          packageName +
          "_" +
          mutator.getType() +
          "_" +
          identifier.line +
          "_" +
          counter;
        println(
          "New identifier! File -> " +
            identifier.file +
            " | Line -> " +
            identifier.line +
            " | id -> " +
            identifier.id
        );
      } catch (e) {
        println("ERROR generating ID!! " + e);
        continue;
      }
      try {
        mutated.insertBefore(
          'if(System.getProperty("MUID") == "' +
            identifier.id +
            '"){\n' +
            mutated.srcCode +
            "\n}else{\n"
        );
        mutator.getMutationPoint().insertAfter("}");
      } catch (e) {
        try {
          mutated = mutated.parent;

          mutated.insertBefore(
            'if(System.getProperty("MUID") == "' +
              identifier.id +
              '"){\n' +
              mutated.srcCode +
              "\n}else{\n"
          );
          mutated.insertAfter("}");
        } catch (ee) {
          println("ERROR MUTATING!!! -> " + ee);
          continue;
        }
      }

      identifiersList.identifiers.push(identifier);
      counter++;

      //println(mutator.getMutationPoint().parent.code);

      // Restore operator
      mutator.restore();
    }
  }
  //saveFile();
  println(
    "Finalized with " +
      identifiersList.identifiers.length +
      " mutants generated"
  );
}

function saveFile() {
  var $outputFolder = Io.mkdir(outputFolder + "\\src\\main\\java\\");
  //Io.deleteFolderContents(outputFolder);

  // Write modified code
  Weaver.writeCode($outputFolder);

  // Print contents
  // 	for(var mutatedFile of Io.getFiles(outputFolder, "*.java") ) {
  // 		println("<File '" + mutatedFile.getName() + "'>");
  // 		println(Io.readFile(mutatedFile));
  // 	}
  if (identifiersList.identifiers.length > 0)
    Io.writeJson(
      outputPath +
        Io.getSeparator() +
        "mutantsIdentifiers" +
        Io.getSeparator() +
        packageName +
        ".json",
      identifiersList
    );
}
//end
