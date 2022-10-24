laraImport("lara.mutation.Mutator");
laraImport("weaver.WeaverJps");
/**
 *  @param {String} bitwiseOperator - The target operator to find and mutate.
 *  @param {String[] | String...} newOperators - Operators that will be used to mutate the bitwise expressions found.
 */
class BitWiseOperatorMutator extends Mutator {

	constructor(bitwiseOperator) {
		//Parent constructor
		super("BitWiseOperatorMutator");

		// Instance variables
		this.bitwiseOperator = bitwiseOperator;
		this.newOperators = arrayFromArgs(arguments, 1);
		this.toMutate = [];
		this.totalMutations = -1;
		this.currentIndex = 0;
		this.newOperatorIndex = 0;
		this.$originalExpression = undefined;
		this.$bitwiseExpression = undefined;

		let unaryOperators = ["~", ""];
		let binaryOperators = ["|", "&", "^"];

		let validMutations = [];

		// Checks
		if (this.newOperators.length === 0) {
			throw new Error("Expected mutations but received none");
		}
		// Check if it is a bitwise operator
		if (binaryOperators.contains(bitwiseOperator)) {
			this.validMutations = Object.assign([], binaryOperators);
			this.validMutations.push("lhs", "rhs");
		}
		else if (unaryOperators.contains(bitwiseOperator)) {
			this.validMutations = Object.assign([], unaryOperators);
		}
		else {
			throw new Error("Expected a bitwise operator, received " + bitwiseOperator);
		}


		let index = this.validMutations.indexOf(bitwiseOperator);
		this.validMutations.splice(index, 1);


		//init $bitwiseExpression and $originalExpression for 1st iteration of mutation 


		for (let i = 0; i < this.newOperators.length; i++) {
			if (!this.validMutations.contains(this.newOperators[i])) {
				var appendMessage = "";
				var invalidOperator = this.newOperators[i] === "" ? "\"\"" : this.newOperators[i];
				if (this.$bitwiseExpression.instanceOf('unaryExpression'))
					appendMessage = ", expected \"\"";
				else
					appendMessage = ", expected at least one of " + this.validMutations.toString();

				throw new Error("Received invalid mutation " + invalidOperator + appendMessage);
			}
		}


	}


	addJp($joinpoint) {
		if ($joinpoint.instanceOf("expression") && $joinpoint.operator === this.bitwiseOperator) {
			this.mutationPoints.push($joinpoint);
			return true;
		}
		return false;
	}

	/*** IMPLEMENTATION OF INSTANCE METHODS ***/
	hasMutations() {
		return this.currentIndex < this.totalMutations;
	}


	_mutatePrivate() {
		// Obtain new operator, increment index
		let newOp = this.newOperators[this.newOperatorIndex++];

		// Set new operator
		if (newOp === "") {
			this.unaryMutate();
		} else {
			this.binaryMutate(newOp);
		}
		println("/*--------------------------------------*/");
		println("Mutating operator n." + this.currentIndex + ": " + this.newOp
			+ " to " + this.$bitwiseExpression);
		println("/*--------------------------------------*/");

	}

	_restorePrivate() {
		// Restore operator
		this.$bitwiseExpression = this.$bitwiseExpression.insertReplace(this.$originalExpression.copy());

		if (this.newOperatorIndex >= this.newOperators.length) {
			this.newOperatorIndex = 0;
			this.currentIndex++;
			if (this.currentIndex < this.totalMutations) {
				this.$bitwiseExpression = this.toMutate[this.currentIndex];
				this.$originalExpression = this.toMutate[this.currentIndex].copy();
			}
		}

	}
	unaryMutate() {
		let toReplace = this.$bitwiseExpression.operand.copy();
		this.$bitwiseExpression = this.$bitwiseExpression.insertReplace(toReplace);
	}

	binaryMutate(newOp) {
		if (newOp === "lhs") {
			let lhs = this.$bitwiseExpression.lhs.copy();
			this.$bitwiseExpression = this.$bitwiseExpression.insertReplace(lhs);
		}
		else if (newOp === "rhs") {
			let rhs = this.$bitwiseExpression.rhs.copy();
			this.$bitwiseExpression = this.$bitwiseExpression.insertReplace(rhs);
		}
		else
			this.$bitwiseExpression.operator = newOp;
	}

	getMutationPoint() {
		if (this.isMutated) {
			return this.$bitwiseExpression;
		} else {
			if (this.newOperatorIndex < this.newOperators.length) {
				return this.newOperators[this.newOperatorIndex];
			} else {
				return undefined;
			}
		}
	}

}