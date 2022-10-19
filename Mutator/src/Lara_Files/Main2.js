laraImport("weaver.Query");
laraImport("mutation.BinaryMutator");

println("Hello");

//println(Query.root().code);

const binMutator = new BinaryMutator("*", "+");

for (const $binOp of Query.search("binaryExpression")) {
  binMutator.addJp($binOp);
}

while (binMutator.hasMutations()) {
  binMutator.mutate();
}

println(Query.root().code);
