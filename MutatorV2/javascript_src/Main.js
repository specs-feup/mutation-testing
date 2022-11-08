laraImport("lara.Io");
laraImport("lara.Strings");
laraImport("Mutators");
laraImport("weaver.Query");

const outputPath = laraArgs.outputPath;
const fileName = laraArgs.fileName;
const outputFolder = laraArgs.outputFolder;
const traditionalMutation = laraArgs.traditionalMutation;
const projectPath = laraArgs.projectPath;

main(outputPath, fileName, outputFolder, traditionalMutation, projectPath);

function main(outputPath, fileName, outputFolder, traditionalMutation) {
  print(fileName);
  if (Mutators.length === 0) {
    println("No mutators selected");
    return;
  }
  println("Traditional Mutation: " + traditionalMutation);
  if (traditionalMutation) {
    //Goes to each node and stores the mutatation point
    runTreeAndGetMutants();

    applyTraditionalMutation(outputPath, fileName);

    //Goes to each stored mutation point and

    /*
    println(Query.root().code);*/
  } else {
    var counter = 0;
    var identifiersList = new Object();

    identifiersList.identifiers = [];

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
            fileName +
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
}

function runTreeAndGetMutants() {
  for (var $jp of Query.root().descendants) {
    for (mutator of Mutators) {
      if (mutator.addJp($jp)) {
        println(mutator);
      }
    }
  }
}

function printMutationPoints() {
  for (mutator of Mutators) {
    println(
      `${mutator.getName()} ---> Mutation Points: ${mutator.mutationPoints}`
    );
  }
}

function applyTraditionalMutation(outputPath, fileName) {
  for (mutator of Mutators) {
    while (mutator.hasMutations()) {
      mutator.mutate();

      saveFileNew(outputPath, fileName, mutator.getName());
    }
  }
}

function saveFileNew(outputPath, fileName, mutatorName) {
  let newFolder =
    outputPath + Io.getSeparator() + mutatorName + Io.getSeparator();
  /*Io.getSeparator() +
    fileName +
    "_" +
    Strings.uuid();*/
  println("NewFolder: " + newFolder);
  println("ProjectPath: " + projectPath);
  Io.copyFolder(projectPath, newFolder, true);
  //println(Query.root().code);

  //p.replace(regex, "ferret");

  // Write modified code
  //Weaver.writeCode($outputFolder);
}

function saveFile() {
  var $outputFolder = Io.mkdir(outputFolder + "\\src\\main\\java\\");
  //Io.deleteFolderContents(outputFolder);

  // Write modified code
  //Weaver.writeCode($outputFolder);

  // Print contents
  // 	for(var mutatedFile of Io.getFiles(outputFolder, "*.java") ) {
  // 		println("<File '" + mutatedFile.getName() + "'>");
  // 		println(Io.readFile(mutatedFile));
  // 	}
  /*if (identifiersList.identifiers.length > 0)
    Io.writeJson(
      outputPath +
        Io.getSeparator() +
        "mutantsIdentifiers" +
        Io.getSeparator() +
        fileName +
        ".json",
      identifiersList
    );*/
}
//end
