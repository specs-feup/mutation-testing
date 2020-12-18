import lara.mutation.Mutator;
import kadabra.KadabraNodes;

/**
 *  @param {$joinpoint} $joinpoint - Joinpoint used as starting point to search for if clauses whose condition will be replaced by true.
 */

var RemoveConditionalMutator = function() {
//Parent constructor
    Mutator.call(this);

	// Instance variables
	this.toMutate = [];
	this.currentIndex = 0;
	
	this.originalConditionalClause = undefined;
	this.$conditionalClause = undefined;

};
// Inheritance
RemoveConditionalMutator.prototype = Object.create(Mutator.prototype);


/*** IMPLEMENTATION OF INSTANCE METHODS ***/


RemoveConditionalMutator.prototype.getType = function (){
	return "RemoveConditionalMutator";
}

RemoveConditionalMutator.prototype.addJp = function($joinpoint){

	// A conditional can be either an if or a ternary operator
	if($joinpoint.instanceOf('if') || $joinpoint.instanceOf( 'ternary') || $joinpoint.instanceOf('loop')) {
		this.toMutate.push($joinpoint.cond);
		return true;
	}
	return false;
}

RemoveConditionalMutator.prototype.hasMutations = function() {
	return this.currentIndex < this.toMutate.length;
}


RemoveConditionalMutator.prototype._mutatePrivate = function() {
	this.$conditionalClause = this.toMutate[this.currentIndex++];
	this.originalConditionalClause = this.$conditionalClause.copy();

	var mutatedCondition = 'true';
	
	this.$conditionalClause = this.$conditionalClause.insertReplace(mutatedCondition);

	println("/*--------------------------------------*/");
	println("Mutating operator n."+ this.currentIndex + ": " + this.originalConditionalClause
		+ " to "+ this.$conditionalClause);
	println("/*--------------------------------------*/");
	
}

RemoveConditionalMutator.prototype._restorePrivate = function() {
	this.$conditionalClause = this.$conditionalClause.insertReplace(this.originalConditionalClause);
	
	this.originalConditionalClause = undefined;
	this.$conditionalClause = undefined;
}

RemoveConditionalMutator.prototype.getMutationPoint = function() {
	if (this.isMutated && this.$conditionalClause !== null) {
		return this.$conditionalClause;
	} else {
		if (this.currentIndex < this.toMutate.length) {
			return this.toMutate[this.currentIndex];
		} else {
			return undefined;
		}
	}
}