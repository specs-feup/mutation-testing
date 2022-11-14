laraImport("lara.Io");
laraImport("lara.Strings");
laraImport("weaver.Query");
laraImport("Arguments");

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
  let array_teste = new Arguments(
    "C:\\Users\\david\\Desktop\\Output".trim(),
    "C:\\Users\\david\\git\\mutation-testing\\MutatorV2\\javascript_src_2\\Main.js".trim(),
    "args:'none'",
    "C:\\Users\\david\\git\\mutation-testing\\MutatorV2\\javascript_src_2".trim(),
    "C:\\Users\\david\\Desktop\\TestProject\\src\\main\\java\\org\\test\\project\\operations\\DivideOperation.java".trim()
  ).getList();

  println(array_teste.toString());

  let result = Weaver.runParallel([array_teste], 1);
}
