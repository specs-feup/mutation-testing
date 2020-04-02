import lara.mutation.Mutator;

/**
 *  @param {$joinpoint} $joinpoint - Joinpoint used as starting point to search for methods whose return value will be mutated.
 *  Return value mutations:
 *  - If method type is primitive INT, SHORT, LONG, CHAR, FLOAT or DOUBLE, return value is replaced with 0.
 *  - If method type is primitive or boxed BOOLEAN, return value is replaced by true.
 */
var ReturnValueMutator = function() {
	//Parent constructor
    Mutator.call(this);

	// Instance variables
	this.toMutate = [];
	this.currentIndex = 0;

	this.originalReturnExpression = undefined;
	this.$returnExpression = undefined;
};
// Inheritance
ReturnValueMutator.prototype = Object.create(Mutator.prototype);

ReturnValueMutator.prototype.getType = function(){
	return "ReturnValueMutator";
}
/*** IMPLEMENTATION OF INSTANCE METHODS ***/

/* Analyze method nodes available for Return Value mutation and store their return statements */
ReturnValueMutator.prototype.addJp = function($joinpoint) {
	var methodZeroTypes = ['int', 'short', 'long', 'char', 'float', 'double']; // Types whose return value will changed to 0.
	var methodTrueTypes = ['boolean', 'Boolean']; // Types whose return value will changed to true.
	
	if($joinpoint.instanceOf('method')) {
		// Check it is a method capable of being mutated
		var mutationValue;
		if(methodZeroTypes.contains($joinpoint.returnType)) {
			mutationValue = '0';
		} else if(methodTrueTypes.contains($joinpoint.returnType)) {
			mutationValue = 'true';
		} else {
			return false;
		}
		
		// Store return statement for later modification
		var methodReturn = WeaverJps.searchFrom($joinpoint, 'return').first();
		this.toMutate.push([methodReturn, mutationValue]);
		return true;
	}
	return false;
}

ReturnValueMutator.prototype.hasMutations = function() {
	return this.currentIndex < this.toMutate.length;
}


ReturnValueMutator.prototype._mutatePrivate = function() {
	var mutationInfo = this.toMutate[this.currentIndex++];

	this.$returnExpression = mutationInfo[0];
	var mutationValue = mutationInfo[1];
	
	this.originalReturnExpression = this.$returnExpression.copy();
	
	var mutatedReturn = 'return ' + mutationValue + ';';
	this.$returnExpression = this.$returnExpression.insertReplace(mutatedReturn);

	println("/*--------------------------------------*/");
	println("Mutating operator n."+ this.currentIndex + ": " + this.originalReturnExpression
		+ " to "+ this.$returnExpression);
	println("/*--------------------------------------*/");
}

ReturnValueMutator.prototype._restorePrivate = function() {
	this.$returnExpression = this.$returnExpression.insertReplace(this.originalReturnExpression);
	
	this.originalReturnExpression = undefined;
	this.$returnExpression = undefined;
}

ReturnValueMutator.prototype.getMutationPoint = function() {
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