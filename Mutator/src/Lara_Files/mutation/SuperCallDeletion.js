laraImport("lara.mutation.Mutator");

/**
 *  @param {$joinpoint} $joinpoint - Joinpoint used as starting point to search for super constructor calls to be removed.
 */
class SuperCallDeletion extends Mutator {
	constructor($joinpoint) {

		//Parent constructor
		super("SuperCallDeletion");

		// Instance variables
		this.toMutate = [];
		this.mutated = [];
		this.currentIndex = 0;

		this.originalSuperCall = undefined;
		this.$superCall = undefined;
	}



	addJp($joinpoint) {

		if ($joinpoint.instanceOf('call') && ($joinpoint.srcCode.startsWith("super") || $joinpoint.srcCode.startsWith("this"))) {
			let mutationPoint = new Object();
			mutationPoint.original = $joinpoint.copy();
			mutationPoint.mutated = $joinpoint;
			this.toMutate.push(mutationPoint);
			return true;
		}

		return false;
	}

	hasMutations() {
		return this.currentIndex < this.toMutate.length;
	}


	_mutatePrivate() {
		let mutationPoint = this.toMutate[this.currentIndex++];

		// Replace super constructor call by a comment
		mutationPoint.mutated = mutationPoint.mutated.insertReplace("// Super constructor call has been removed");

		this.mutated.push(mutationPoint);
		this.isMutated = true;
		println("/*--------------------------------------*/");
		println("Mutating operator n." + this.currentIndex + ": " + mutationPoint.original
			+ " to " + mutationPoint.mutated);
		println("/*--------------------------------------*/");

	}

	_restorePrivate() {
		let mutationPoint = this.mutated.pop();
		println("Popping");

		this.$superCall = mutationPoint.mutated.insertReplace(mutationPoint.original.srcCode);
		println(mutationPoint.mutated.parent.srcCode);
		if (this.mutated.length > 0) {
			println(this.isMutated);
			this.isMutated = true;
		} else {
			println(this.isMutated);
			this.isMutated = false;
		}

	}

	getMutationPoint() {

		if (this.mutated.length > 0) {
			return this.mutated[this.mutated.length - 1];
		} else
			return undefined;

	}

	getAllMutationPoints() { return this.toMutate; }
	getMutated() { return this.mutated; }
	hasRestores() { return this.isMutated; }
}
