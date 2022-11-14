laraImport("lara.Io");
laraImport("lara.Strings");
laraImport("Mutators");
laraImport("weaver.Query");

/*const outputPath = laraArgs.outputPath;
const filePath = laraArgs.filePath;
const outputFolder = laraArgs.outputFolder;
const traditionalMutation = laraArgs.traditionalMutation;
const projectPath = laraArgs.projectPath.trim();
const debugMessages = laraArgs.debugMessages;
const fileName = filePath.substring(
  filePath.lastIndexOf(Io.getSeparator()) + 1
);*/

main();

function main() {
  //Shows aditional prints
  /*if (debugMessages) {
    setDebug(true);
  }

  //If no mutatans were selected
  if (Mutators.length === 0) {
    println("No mutators selected");
    return;
  }

  println("Traditional Mutation: " + traditionalMutation);

  if (traditionalMutation) {
    println("Going through AST for file " + fileName);
    //Goes to each node and stores the mutatation point
    runTreeAndGetMutantsTraditionaly();

    println("Generating Mutants for file " + fileName);
    //Goes to each stored mutation point and applies the mutation
    applyTraditionalMutation();
  } else {
    runTreeAndApplyMetaMutant();
  }*/
  print("Teste!!!!!!!!!");
}

function runTreeAndGetMutantsTraditionaly() {
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
        debug(mutator);
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

function applyTraditionalMutation() {
  for (mutator of Mutators) {
    while (mutator.hasMutations()) {
      //Aplies the mutation
      mutator.mutate();

      //Saves to a file
      saveFileNew(mutator.getName());
    }
  }
}

function runTreeAndApplyMetaMutant() {
  for (var $jp of Query.root().descendants) {
    var $call = $jp.ancestor("call");

    // Ignore nodes that are children of $call with the name <init>
    if ($call !== undefined && $call.name === "<init>") continue;

    // Ignore nodes inside variable declarations
    //if ($jp.ancestor("localVariable") !== undefined) {
    //  continue;
    //}

    let mutationPoints = 0;
    let needElseIf = false;
    let firstTime = true;
    for (mutator of Mutators) {
      if (mutator.addJp($jp)) {
        mutationPoints++;
        debug(mutator);
      }
    }

    if (mutationPoints >= 2) {
      needElseIf = true;
    }

    for (mutator of Mutators) {
      while (mutator.hasMutations()) {
        // Mutate
        mutator.mutate();

        var mutated = mutator.getMutationPoint().isStatement
          ? mutator.getMutationPoint()
          : mutator.getMutationPoint().ancestor("statement");

        let mutantId =
          mutator.getName() +
          "_" +
          fileName.replace(".java", "") +
          "_" +
          Strings.uuid();

        if (needElseIf) {
          if (mutationPoints > 1) {
            if (firstTime) {
              mutated.insertBefore(
                'if(System.getProperty("MUID") == "' +
                  mutantId +
                  '"){\n' +
                  mutated.srcCode +
                  ";\n}"
              );

              firstTime = false;
            } else {
              mutated.insertBefore(
                'else if(System.getProperty("MUID") == "' +
                  mutantId +
                  '"){\n' +
                  mutated.srcCode +
                  ";\n}"
              );
            }
            mutationPoints--;
          } else {
            mutated.insertBefore(
              'else if (System.getProperty("MUID") == "' +
                mutantId +
                '"){\n' +
                mutated.srcCode +
                ";\n}else{\n\t"
            );
            mutated.insertAfter("}");
          }
        } else {
          mutated.insertBefore(
            'if (System.getProperty("MUID") == "' +
              mutantId +
              '"){\n' +
              mutated.srcCode +
              ";\n}else{\n\t"
          );
          mutated.insertAfter("}");
        }
      }
    }
  }
  saveFileSimple();
}

function saveFileNew(mutatorName) {
  let relativePath = Io.getRelativePath(filePath, projectPath);

  let newFolder =
    outputPath +
    Io.getSeparator() +
    mutatorName +
    Io.getSeparator() +
    fileName.replace(".java", "") +
    "_" +
    Strings.uuid();

  Io.copyFolder(projectPath, newFolder, true);

  Io.writeFile(
    newFolder +
      Io.getSeparator() +
      relativePath.replace("/", Io.getSeparator()),
    Query.root().code
  );
}

function saveFileSimple() {
  let relativePath = Io.getRelativePath(filePath, projectPath);

  Io.copyFolder(projectPath, outputPath, true);

  Io.writeFile(
    outputPath +
      Io.getSeparator() +
      relativePath.replace("/", Io.getSeparator()),
    Query.root().code
  );
}
