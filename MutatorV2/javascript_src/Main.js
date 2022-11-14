laraImport("lara.Io");
laraImport("lara.Strings");
laraImport("weaver.Query");
laraImport("Arguments");

const outputPath = laraArgs.outputPath;
const traditionalMutation = laraArgs.traditionalMutation;
const projectPath = laraArgs.projectPath;
const debugMessages = laraArgs.debugMessages;
const folderToIgnore = laraArgs.folderToIgnore;

main();

function main() {
  //Shows aditional prints
  if (debugMessages) {
    setDebug(true);
  }

  let allJavaFiles = Io.getFiles(projectPath, "*.java", true);
  let javaFilesToRemove = Io.getFiles(folderToIgnore, "*.java", true);

  let filesToUse = allJavaFiles.filter(function (el) {
    return javaFilesToRemove.indexOf(el) < 0;
  });

  //Makes already the copy of the projetc
  if (!traditionalMutation) {
    Io.copyFolder(
      projectPath,
      outputPath + Io.getSeparator() + "MetaMutante",
      true
    );
  }

  //Kadabra Parallel execution
  args_final = [];
  for (i in filesToUse) {
    println(filesToUse[i]);

    let args = new Arguments(
      outputPath.trim(),
      "C:\\Users\\david\\git\\mutation-testing\\MutatorV2\\javascript_src_2\\Main.js".trim(),
      "args:'none'",
      "C:\\Users\\david\\git\\mutation-testing\\MutatorV2\\javascript_src_2".trim(),
      filesToUse[i]
    ).getList();

    args_final.push(args);
  }

  let result = Weaver.runParallel(args_final, args_final.length);
}
