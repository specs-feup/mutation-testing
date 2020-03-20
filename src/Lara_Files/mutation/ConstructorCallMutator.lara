import lara.mutation.Mutator;

/**
 *  @param {$joinPoint} $joinPoint - A join point to use as startpoint to search for constructor calls to replace with null.
 */
var ConstructorCallMutator = function($joinPoint) {
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
	
	this.$referenceParent = undefined;
	this.$originalParent = undefined;
	

	// Checks
	if(this.extraArgs.length != 0)
		throw "Expected only 1 argument but received " + (this.extraArgs.length + 1);

};

// Inheritance
ConstructorCallMutator.prototype = Object.create(Mutator.prototype);


/*** IMPLEMENTATION OF INSTANCE METHODS ***/
ConstructorCallMutator.prototype.getType = function(){
	return "ConstructorCallMutator";
}

ConstructorCallMutator.prototype.addJp = function($joinpoint){
	if($joinpoint.instanceOf("reference") && $joinpoint.name === "<init>" && $joinpoint.type === "Executable" && $joinpoint.parent.srcCode !== "super()"){
		this.toMutate.push($joinpoint);
		this.totalMutations = this.toMutate.length;
		return true;
	}

	return false;
}


ConstructorCallMutator.prototype.hasMutations = function() {
	return this.currentIndex < this.totalMutations;
}


ConstructorCallMutator.prototype._mutatePrivate = function() {
	this.$referenceParent = this.toMutate[this.currentIndex++].parent;

	this.$originalParent = this.$referenceParent.copy();
	this.$referenceParent = this.$referenceParent.insertReplace("null");

	println("/*--------------------------------------*/");
	println("Mutating operator n."+ this.currentIndex + ": "+ this.$originalParent
		+" to "+ this.$referenceParent);
	println("/*--------------------------------------*/");

}

ConstructorCallMutator.prototype._restorePrivate = function() {
	this.$referenceParent = this.$referenceParent.insertReplace(this.$originalParent);
	this.$originalParent = undefined;
	this.$referenceParent = undefined;
}

ConstructorCallMutator.prototype.getMutationPoint = function() {
	if (this.isMutated) {
		return this.$referenceParent;
	} else {
		if (this.currentIndex < this.toMutate.length) {
			return this.toMutate[this.currentIndex];
		} else {
			return undefined;
		}
	}
}