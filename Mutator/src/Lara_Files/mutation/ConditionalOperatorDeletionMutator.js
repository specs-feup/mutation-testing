import lara.mutation.Mutator;

/**
 *  @param {$joinPoint} $joinPoint - A join point to use as starting point to search for conditionals to remove '!'.
 */
var ConditionalOperatorDeletionMutator = function($joinPoint) {
	//Parent constructor
    Mutator.call(this);

	if($joinPoint === undefined) {
		$joinPoint = WeaverJps.root();
	}

	// Instance variables
	this.$joinPoint = $joinPoint;
	this.extraArgs = arrayFromArgs(arguments, 1);
	
	this.toMutate = [];
	this.totalMutations = -1;
	this.currentIndex = 0;
	
	this.$conditional = undefined;
	this.$originalConditional = undefined;
	

	// Checks
	if(this.extraArgs.length != 0)
		throw "Expected only 1 argument but received " + (this.extraArgs.length + 1);

};
// Inheritance
ConditionalOperatorDeletionMutator.prototype = Object.create(Mutator.prototype);


/*** IMPLEMENTATION OF INSTANCE METHODS ***/

ConditionalOperatorDeletionMutator.prototype.getType = function(){
	return "ConditionalOperatorDeletionMutator";
}

ConditionalOperatorDeletionMutator.prototype.addJp = function($joinpoint){
	//println("JP -> " +$joinpoint);

	if($joinpoint.instanceOf('if') || $joinpoint.instanceOf('ternary') || $joinpoint.instanceOf('loop')) {

		if($joinpoint.cond.instanceOf('unaryExpression') && $joinpoint.cond.operator === '!')
			this.toMutate.push($joinpoint);
		else
			if($joinpoint.cond.instanceOf('unaryExpression') && $joinpoint.cond.operator === '!')
				this.toMutate.push($joinpoint);
			else
				if($joinpoint.cond.instanceOf('unaryExpression') && $joinpoint.cond.operator === '!')
					this.toMutate.push($joinpoint);

		this.totalMutations = this.toMutate.length;
		return true;
	}

	return false;

}

ConditionalOperatorDeletionMutator.prototype.hasMutations = function() {
	return this.currentIndex < this.totalMutations;
}


ConditionalOperatorDeletionMutator.prototype._mutatePrivate = function() {
	this.$conditional = this.toMutate[this.currentIndex++];

	this.$originalConditional = this.$conditional.copy();
	this.$conditional.cond.insertReplace(this.$conditional.cond.operand.copy());

	println("/*--------------------------------------*/");
	println("Mutating operator n."+ this.currentIndex + ": "+ this.$originalConditional
		+" to "+ this.$conditional);
	println("/*--------------------------------------*/");


}

ConditionalOperatorDeletionMutator.prototype._restorePrivate = function() {
	this.$conditional = this.$conditional.insertReplace(this.$originalConditional);
	this.$originalConditional = undefined;
	this.$conditional = undefined;
}


ConditionalOperatorDeletionMutator.prototype.getMutationPoint = function() {
	if (this.isMutated) {
		return this.$conditional;
	} else {
		if (this.currentIndex < this.toMutate.length) {
			return this.toMutate[this.currentIndex];
		} else {
			return undefined;
		}
	}
}