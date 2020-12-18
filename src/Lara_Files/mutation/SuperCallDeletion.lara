import lara.mutation.Mutator;

/**
 *  @param {$joinpoint} $joinpoint - Joinpoint used as starting point to search for super constructor calls to be removed.
 */
var SuperCallDeletion = function($joinpoint) {

	//Parent constructor
   	Mutator.call(this);

	// Instance variables
	this.toMutate = [];
	this.mutated = [];
	this.currentIndex = 0;

	this.originalSuperCall = undefined;
	this.$superCall = undefined;
};

// Inheritance
SuperCallDeletion.prototype = Object.create(Mutator.prototype);


/*** IMPLEMENTATION OF INSTANCE METHODS ***/

/* Store super constructor calls */
SuperCallDeletion.prototype.getType = function(){
	return "SuperCallDeletion";
}


SuperCallDeletion.prototype.addJp = function($joinpoint) {

	if($joinpoint.instanceOf('call') && ( $joinpoint.srcCode.startsWith("super") || $joinpoint.srcCode.startsWith("this") )){
		var mutationPoint = new Object();
		mutationPoint.original = $joinpoint.copy();
		mutationPoint.mutated = $joinpoint;
		this.toMutate.push(mutationPoint);
		return true;
	}

	return false;
}

SuperCallDeletion.prototype.hasMutations = function() {
	return this.currentIndex < this.toMutate.length;
}


SuperCallDeletion.prototype._mutatePrivate = function() {
	var mutationPoint = this.toMutate[this.currentIndex++];

	// Replace super constructor call by a comment
	mutationPoint.mutated = mutationPoint.mutated.insertReplace("// Super constructor call has been removed");

	this.mutated.push(mutationPoint);
	this.isMutated = true;
	println("/*--------------------------------------*/");
	println("Mutating operator n."+ this.currentIndex + ": "+ mutationPoint.original
		+" to "+ mutationPoint.mutated);
	println("/*--------------------------------------*/");

}

SuperCallDeletion.prototype._restorePrivate = function() {
	var mutationPoint = this.mutated.pop();
	println("Popping");

	this.$superCall = mutationPoint.mutated.insertReplace(mutationPoint.original.srcCode);
	println(mutationPoint.mutated.parent.srcCode);
	if(this.mutated.length > 0) {
		println(this.isMutated);
		this.isMutated = true;
	}else {
		println(this.isMutated);
		this.isMutated = false;
	}

}

SuperCallDeletion.prototype.getMutationPoint = function() {

	if(this.mutated.length > 0){
		return this.mutated[this.mutated.length-1];
	} else
		return undefined;

}

SuperCallDeletion.prototype.getAllMutationPoints =function() { return this.toMutate; }
SuperCallDeletion.prototype.getMutated = function() { return this.mutated; }
SuperCallDeletion.prototype.hasRestores = function() { return this.isMutated; }