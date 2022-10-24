laraImport("lara.mutation.Mutator");
laraImport("kadabra.KadabraNodes");

/**
 *  @param {$joinpoint} $joinpoint - Joinpoint used as starting point to search for constants to be deleted from binary expressions.
 *  @param {String} targetConstant - Target constant to be removed from binary expressions.
 */

class ConstantDeletionMutator extends Mutator {

	constructor($joinpoint, targetConstant) {
		//Parent constructor

		super("ConstantDeletionMutator");

		if ($joinpoint === undefined) {
			$joinpoint = WeaverJps.root();
		}

		// Instance variables
		this.toMutate = [];
		this.currentIndex = 0;

		this.targetConstant = [targetConstant, '(' + targetConstant + ')'];
		this.$originalNode = undefined;
		this.$node = undefined;

		// Checks
		let extraArgs = arrayFromArgs(arguments, 2);
		if (extraArgs.length != 0)
			throw new Error("Expected only 2 argument but received " + (this.extraArgs.length + 2));

	}

	/*** IMPLEMENTATION OF INSTANCE METHODS ***/

	/* Analyze method binaryExpressions available for Constant Deletion mutation and store them */
	addJp($joinpoint) {
		if ($joinpoint.instanceOf('binaryExpression')) {

			var $lhs = $joinpoint.lhs;
			var $rhs = $joinpoint.rhs;
			if (((this.targetConstant.contains($lhs.srcCode) && $lhs.isFinal)
				|| (this.targetConstant.contains($rhs.srcCode) && $rhs.isFinal))
				&& $joinpoint.type !== 'boolean') {

				this.toMutate.push($joinpoint);
			}
		}
	}

	hasMutations() {
		return this.currentIndex < this.toMutate.length;
	}


	_mutatePrivate() {
		this.$node = this.toMutate[this.currentIndex++];

		this.$originalNode = this.$node.copy();

		if (this.targetConstant.contains(this.$node.lhs.srcCode)) {
			this.$node = this.$node.insertReplace(this.$node.rhs);
		} else if (this.targetConstant.contains(this.$node.rhs.srcCode)) {
			this.$node = this.$node.insertReplace(this.$node.lhs);
		}

		println("/*--------------------------------------*/");
		println("Mutating operator n." + this.currentIndex + ": " + this.$originalNode
			+ " to " + this.$node);
		println("/*--------------------------------------*/");

	}

	_restorePrivate() {
		this.$node = this.$node.insertReplace(this.$originalNode);

		this.$originalNode = undefined;
		this.$node = undefined;
	}

	getMutationPoint() {
		if (this.isMutated) {
			return this.$node;
		} else {
			if (this.currentIndex < this.toMutate.length) {
				return this.toMutate[this.currentIndex];
			} else {
				return undefined;
			}
		}
	}
}