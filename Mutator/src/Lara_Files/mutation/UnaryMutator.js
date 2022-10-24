import lara.mutation.Mutator;
import kadabra.KadabraNodes;
import weaver.WeaverJps;
import weaver.Weaver;

var UnaryMutator = function($expr, $original) {
  //Parent constructor
    Mutator.call(this);

    this.type = "UnaryMutator";

    this.$expr = $expr;
    this.$original = $original;

    this.newValue = undefined;

    this.mutationPoints = [];

    this.currentIndex = 0;
    this.previousValue = undefined;

};

// Inheritance
UnaryMutator.prototype = Object.create(Mutator.prototype);

UnaryMutator.prototype.getType = function(){
    return this.type;
}

UnaryMutator._isCompatible = function(type1, type2) {
    return type1 === type2;
}

/*** IMPLEMENTATION OF INSTANCE METHODS ***/
UnaryMutator.prototype.addJp = function($joinpoint){
    if($joinpoint.instanceOf("unaryExpression")  &&  UnaryMutator._isCompatible($joinpoint.operator, this.$original)) {
        this.mutationPoints.push($joinpoint);
        return true;
    }
    return false;
}

UnaryMutator.prototype.hasMutations = function() {
    return this.currentIndex < this.mutationPoints.length;
}

UnaryMutator.prototype.getMutationPoint = function() {
    if (this.isMutated) {
        return this.mutationPoint;
    } else {
        if (this.currentIndex < this.mutationPoints.length) {
            return this.mutationPoints[this.currentIndex];
        } else {
            return undefined;
        }
    }
}

//TODO: Refactor for the undefined
UnaryMutator.prototype._mutatePrivate = function() {

    this.mutationPoint = this.mutationPoints[this.currentIndex];
    println(this.mutationPoint);
    this.currentIndex++;

    if(isFunction(this.$expr)){
        //TODO: Change
        var temp= this.$expr(this.mutationPoint);
        this.newValue = this.newValue.insertReplace(temp);
    }else{
        if(this.$expr === "undefined"){
            this.previousValue = this.mutationPoint.copy;
            println(this.previousValue.operator);
            this.mutationPoint = this.mutationPoint.insertReplace(this.previousValue.operand);
        }else{
            this.previousValue = this.mutationPoint.copy;
            this.mutationPoint.operator = this.$expr;
        }
        //this.newValue = this.$expr;
    }

    println("/*--------------------------------------*/");
    println("Mutating operator n."+ this.currentIndex + ": "+ this.previousValue +" to "+ this.mutationPoint);
    println("/*--------------------------------------*/");

}

UnaryMutator.prototype._restorePrivate = function() {
    // Restore operator
    println("Restoring " + this.mutationPoint + " to " + this.previousValue);

    if(this.$expr === "undefined") {
        this.mutationPoint = this.mutationPoint.insertReplace(this.previousValue);
    }else {
        this.mutationPoint.operator = this.previousValue.operator;
    }

    this.previousValue = undefined;
    this.mutationPoint = undefined;
}