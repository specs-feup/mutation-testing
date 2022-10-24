laraImport("lara.mutation.Mutator");

/**
 *  @param {$joinpoint} $joinpoint - Joinpoint used as starting point to search for methods whose return value will be mutated.
 *  Return value mutations:
 *  - If method type is primitive INT, SHORT, LONG, CHAR, FLOAT or DOUBLE, return value is replaced with 0.
 *  - If method type is primitive or boxed BOOLEAN, return value is replaced by true.
 */
class ReturnValueMutator extends Mutator {

	constructor() {
		//Parent constructor
		super("ReturnValueMutator")

		// Instance variables
		this.toMutate = [];
		this.currentIndex = 0;

		this.originalReturnExpression = undefined;
		this.$returnExpression = undefined;
	}

	/*** IMPLEMENTATION OF INSTANCE METHODS ***/

	/* Analyze method nodes available for Return Value mutation and store their return statements */
	addJp($joinpoint) {
		let methodZeroTypes = ['int', 'short', 'long', 'char', 'float', 'double']; // Types whose return value will changed to 0.
		let methodTrueTypes = ['boolean', 'Boolean']; // Types whose return value will changed to true.

		if ($joinpoint.instanceOf('method')) {
			// Check it is a method capable of being mutated
			let mutationValue;
			if (methodZeroTypes.contains($joinpoint.returnType)) {
				mutationValue = '0';
			} else if (methodTrueTypes.contains($joinpoint.returnType)) {
				mutationValue = 'true';
			} else {
				return false;
			}

			// Store return statement for later modification
			let methodReturn = WeaverJps.searchFrom($joinpoint, 'return').first();
			this.toMutate.push([methodReturn, mutationValue]);
			return true;
		}
		return false;
	}

	hasMutations() {
		return this.currentIndex < this.toMutate.length;
	}


	_mutatePrivate() {
		let mutationInfo = this.toMutate[this.currentIndex++];

		this.$returnExpression = mutationInfo[0];
		let mutationValue = mutationInfo[1];

		this.originalReturnExpression = this.$returnExpression.copy();

		let mutatedReturn = 'return ' + mutationValue + ';';
		this.$returnExpression = this.$returnExpression.insertReplace(mutatedReturn);

		println("/*--------------------------------------*/");
		println("Mutating operator n." + this.currentIndex + ": " + this.originalReturnExpression
			+ " to " + this.$returnExpression);
		println("/*--------------------------------------*/");
	}

	_restorePrivate() {
		this.$returnExpression = this.$returnExpression.insertReplace(this.originalReturnExpression);

		this.originalReturnExpression = undefined;
		this.$returnExpression = undefined;
	}

	getMutationPoint() {
		if (this.isMutated && this.$returnExpression !== null) {
			return this.$returnExpression;
		} else {
			if (this.currentIndex < this.toMutate.length) {
				return this.toMutate[this.currentIndex];
			} else {
				return undefined;
			}
		}
	}
}
