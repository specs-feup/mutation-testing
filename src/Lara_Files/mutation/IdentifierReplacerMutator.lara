import lara.mutation.Mutator;
import kadabra.KadabraNodes;
import weaver.WeaverJps;
import weaver.Weaver;

var IdentifierReplacerMutator = function($result, $original) {
    //Parent constructor
    Mutator.call(this);

    this.$expr = $result;

    this.mutationPoints = [];

    //To store the identifiers according to the package
    this.identifiersMap = new Map();

    this.currentIndex = 0;
    this.mutationPoint = undefined;
    this.previousValue = undefined;


};
// Inheritance
IdentifierReplacerMutator.prototype = Object.create(Mutator.prototype);

IdentifierReplacerMutator._isCompatible = function(type1, type2) {
    return type1 === type2;
}

/*** IMPLEMENTATION OF INSTANCE METHODS ***/
IdentifierReplacerMutator.prototype.getType = function(){
    return "IdentifierReplacerMutator";
}

IdentifierReplacerMutator.prototype.addJp = function($joinpoint){

    if($joinpoint.instanceOf("expression"))
        if($joinpoint.code.match(/^R\..*/)){
            var tempList = this.identifiersMap.get($joinpoint.typeReference.package);

            if(tempList === undefined){ //Creating or updating the entry
                tempList = [$joinpoint];
            }else{
                if(tempList.length  === 1) //To add the first to the mutation points
                    this.mutationPoints.push(tempList[0]);

                tempList.push($joinpoint);

                this.mutationPoints.push($joinpoint);
            }

            this.identifiersMap.set($joinpoint.typeReference.package, tempList); //Saving the entry

            return true;
        }

    return false;
}

IdentifierReplacerMutator.prototype.hasMutations = function() {
    return this.currentIndex < this.mutationPoints.length;
}

IdentifierReplacerMutator.prototype.getMutationPoint = function() {
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

IdentifierReplacerMutator.prototype._mutatePrivate = function() {

    this.mutationPoint = this.mutationPoints[this.currentIndex];
    this.currentIndex++;

    this.previousValue = this.mutationPoint.copy();


    var tempList = this.identifiersMap.get(this.mutationPoint.typeReference.package);

    var indexOfMutationPoint = tempList.indexOf(this.mutationPoint);

    //TODO: CHECK IF IT IS THE PROPER WAY TO DO IT
    if(indexOfMutationPoint < tempList.length-1)
        this.mutationPoint = this.mutationPoint.insertReplace(tempList[indexOfMutationPoint + 1].copy());
    else
        this.mutationPoint = this.mutationPoint.insertReplace(tempList[0].copy());


    println("/*--------------------------------------*/");
    println("Mutating operator n." + this.currentIndex + ": " + this.previousValue + " to " + this.mutationPoint);
    println("/*--------------------------------------*/");

}

IdentifierReplacerMutator.prototype._restorePrivate = function() {
    // Restore operator
    println("Restoring: " + this.mutationPoint + " to " + this.previousValue);
    this.mutationPoint = this.mutationPoint.insertReplace(this.previousValue).copy();
    this.previousValue = undefined;
    this.mutationPoint = undefined;
}