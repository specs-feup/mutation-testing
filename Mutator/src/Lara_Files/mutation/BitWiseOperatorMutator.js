import lara.mutation.Mutator;
import weaver.WeaverJps;

/**
 *  @param {String} bitwiseOperator - The target operator to find and mutate.
 *  @param {String[] | String...} newOperators - Operators that will be used to mutate the bitwise expressions found.
 */
var BitWiseOperatorMutator = function(bitwiseOperator) {

	//Parent constructor
    Mutator.call(this);

	// Instance variables
	this.type = "BitWiseOperatorMutator";
	this.bitwiseOperator = bitwiseOperator;
	this.newOperators = arrayFromArgs(arguments, 1);
	
	this.toMutate = [];
	this.totalMutations = -1;
	this.currentIndex = 0;
	this.newOperatorIndex = 0;

	this.$originalExpression = undefined;
	this.$bitwiseExpression = undefined;

	var unaryOperators = ["~", ""];
	var binaryOperators = ["|", "&", "^"];

	var validMutations = [];
	
	// Checks
	if(this.newOperators.length === 0)
		throw "Expected mutations but received none";

	// Check if it is a bitwise operator
	if(binaryOperators.contains(bitwiseOperator)) {
		this.validMutations = Object.assign([], binaryOperators);
		this.validMutations.push("lhs", "rhs");
	}
	else if(unaryOperators.contains(bitwiseOperator)) {
		this.validMutations = Object.assign([], unaryOperators);	
	}
	else {
		throw "Expected a bitwise operator, received " + bitwiseOperator;
	}

	
	var index = this.validMutations.indexOf(bitwiseOperator);
	this.validMutations.splice(index, 1);


	//init $bitwiseExpression and $originalExpression for 1st iteration of mutation 

	
	for(var i = 0; i < this.newOperators.length; i++) {
		if(!this.validMutations.contains(this.newOperators[i])) {
			var appendMessage = "";
			var invalidOperator = this.newOperators[i] === "" ? "\"\"" : this.newOperators[i];
			if(this.$bitwiseExpression.instanceOf('unaryExpression'))
				appendMessage = ", expected \"\"";
			else
				appendMessage = ", expected at least one of " + this.validMutations.toString();
			
			throw "Received invalid mutation " + invalidOperator + appendMessage;
		}
	}

};


// Inheritance
BitWiseOperatorMutator.prototype = Object.create(Mutator.prototype);

BitWiseOperatorMutator.prototype.getType = function(){
	return this.type;
}

BitWiseOperatorMutator.prototype.addJp = function($joinpoint){
	if($joinpoint.instanceOf("expression") && $joinpoint.operator === this.bitwiseOperator) {
		this.mutationPoints.push($joinpoint);
		return true;
	}
	return false;
}

/*** IMPLEMENTATION OF INSTANCE METHODS ***/

BitWiseOperatorMutator.prototype.hasMutations = function() {
	return this.currentIndex < this.totalMutations;
}


BitWiseOperatorMutator.prototype._mutatePrivate = function() {
	// Obtain new operator, increment index
	var newOp = this.newOperators[this.newOperatorIndex++];
	
	// Set new operator
	if(newOp === "")
		this.unaryMutate();
	else
		this.binaryMutate(newOp);

	println("/*--------------------------------------*/");
	println("Mutating operator n."+ this.currentIndex + ": "+ this.newOp
		+" to "+ this.$bitwiseExpression);
	println("/*--------------------------------------*/");

}

BitWiseOperatorMutator.prototype._restorePrivate = function() {
	// Restore operator
	this.$bitwiseExpression = this.$bitwiseExpression.insertReplace(this.$originalExpression.copy());

	if(this.newOperatorIndex >= this.newOperators.length) {
		this.newOperatorIndex = 0;
		this.currentIndex++;
		if(this.currentIndex < this.totalMutations) {
			this.$bitwiseExpression = this.toMutate[this.currentIndex];
			this.$originalExpression = this.toMutate[this.currentIndex].copy();
		}
	}
	
}

BitWiseOperatorMutator.prototype.unaryMutate = function() {
	var toReplace = this.$bitwiseExpression.operand.copy();
	this.$bitwiseExpression = this.$bitwiseExpression.insertReplace(toReplace);
}

BitWiseOperatorMutator.prototype.binaryMutate = function(newOp) {
	if(newOp === "lhs") {
		var lhs = this.$bitwiseExpression.lhs.copy();
		this.$bitwiseExpression = this.$bitwiseExpression.insertReplace(lhs);
	}
	else if(newOp === "rhs") {
		var rhs = this.$bitwiseExpression.rhs.copy();
		this.$bitwiseExpression = this.$bitwiseExpression.insertReplace(rhs);
	}
	else
		this.$bitwiseExpression.operator = newOp;
}

BitWiseOperatorMutator.prototype.getMutationPoint = function() {
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
