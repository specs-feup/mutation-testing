import lara.mutation.Mutator;
import kadabra.KadabraNodes;
import weaver.WeaverJps;
import weaver.Weaver;

var FindViewByIdDeletionMutator = function() {
  //Parent constructor
    Mutator.call(this);

    this.mutationPoints = [];

    this.currentIndex = 0;
    this.mutationPoint = undefined;
    this.previousValue = undefined;

};

// Inheritance
FindViewByIdDeletionMutator.prototype = Object.create(Mutator.prototype);

FindViewByIdDeletionMutator.prototype.getType = function(){
    return "FindViewByIdDeletionMutator";
}

FindViewByIdDeletionMutator.prototype.addJp = function($joinpoint){
    if($joinpoint.instanceOf('call') && $joinpoint.toString().includes("findViewById")){
        this.mutationPoints.push($joinpoint);
        return true;
    }
    return false;
}

FindViewByIdDeletionMutator._isCompatible = function(type1, type2) {
    return type1 === type2;
}

/*** IMPLEMENTATION OF INSTANCE METHODS ***/
FindViewByIdDeletionMutator.prototype.hasMutations = function() {
    return this.currentIndex < this.mutationPoints.length;
}

FindViewByIdDeletionMutator.prototype.getMutationPoint = function() {
    if (this.currentIndex < this.mutationPoints.length) {
        return this.mutationPoints[this.currentIndex];
    } else {
        return undefined;
    }
}

FindViewByIdDeletionMutator.prototype._mutatePrivate = function() {

    this.mutationPoint = this.mutationPoints[this.currentIndex];

    this.previousValue = this.mutationPoint.copy;
    this.mutationPoint = this.mutationPoint.insertReplace("null");

    println("/*--------------------------------------*/");
    println("Mutating operator n." + this.currentIndex + ": " + this.previousValue + " to " + this.mutationPoint);
    println("/*--------------------------------------*/");

}

FindViewByIdDeletionMutator.prototype._restorePrivate = function() {
    // Restore operator
    println("Restoring: " + this.mutationPoint + " to " + this.previousValue);
    this.mutationPoint.insertReplace(this.previousValue);
    this.currentIndex++;
    this.previousValue = undefined;
    this.mutationPoint = undefined;
}