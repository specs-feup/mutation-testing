laraImport("lara.Io");
laraImport("lara.Strings");
laraImport("Mutators");
laraImport("weaver.Query");
laraImport("lara.util.ProcessExecutor");

/*const outputPath = laraArgs.outputPath;
const filePath = laraArgs.filePath;
const outputFolder = laraArgs.outputFolder;
const traditionalMutation = laraArgs.traditionalMutation;
const projectPath = laraArgs.projectPath.trim();
const debugMessages = laraArgs.debugMessages;
const fileName = filePath.substring(
  filePath.lastIndexOf(Io.getSeparator()) + 1
);*/

class Arguments {
  constructor(
    outputFolder,
    javascriptFile,
    args,
    includesFolder,
    workspaceFolder
  ) {
    this.writeCode = false;
    this.outputFolder = outputFolder;
    this.javascriptFile = javascriptFile;
    this.stackTrace = true;
    this.externalDependencies = [];
    this.debugMode = true;
    this.args = args;
    this.fullyQualifiedNames = true;
    this.includesFolder = includesFolder;
    this.workspaceFolder = workspaceFolder;
  }

  addDependencies(url) {
    externalDependencies.push(url);
  }

  getList() {
    //Add entry point
    let strAux = [];

    //strAux.push("clava ");
    strAux.push(this.javascriptFile);

    //Clava with stack strace
    if (this.stackTrace) {
      strAux.push("-s");
    }

    //Writes the code by it selfy
    if (this.writeCode) {
      strAux.push("WR");
    }

    //Adds the output path
    strAux.push("-o");
    strAux.push(this.outputFolder);

    if (this.debugMode) {
      strAux.push("-d");
    }

    //Adds the dependencies (pode ser preciso remover o ultimo; )
    if (this.externalDependencies.length > 0) {
      strAux.push("-dep ");

      let aux = "";
      for (i in this.externalDependencies) {
        aux += i + ";";
      }
      strAux.push(aux);
    }

    //add args
    strAux.push("-av");
    strAux.push("{" + this.args + "}");

    //Fully qualified names
    if (this.fullyQualifiedNames) {
      strAux.push("-Q");
    }

    //includes folder
    strAux.push("-i");
    strAux.push(this.includesFolder);

    // files
    strAux.push("-p");
    strAux.push(this.workspaceFolder);

    strAux.push("-X");

    return strAux;
  }
}

main();

function main() {
  //let aux = new ProcessExecutor();

  //aux.execute("dir");

  //let teste = Io.getFiles(projectPath, "*.java", true);

  let array = new Arguments(
    "C:\\Users\\david\\Desktop\\Output",
    "C:\\Users\\david\\git\\mutation-testing\\MutatorV2\\javascript_src\\Main.js",
    "args:'none',teste:'david'",
    "C:\\Users\\david\\git\\mutation-testing\\MutatorV2\\javascript_src",
    "C:\\Users\\david\\Desktop\\TestProject\\src\\main\\java\\org\\test\\project\\operations\\DivideOperation.java"
  ).getList();

  //array = array.map((arg) => arg.trim());

  for (i in array) {
    println(array[i]);
  }

  let result = Weaver.runParallel([array], 1);

  //println("teste: " + teste);
}
