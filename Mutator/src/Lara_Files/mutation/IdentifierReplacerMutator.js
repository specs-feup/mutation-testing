laraImport("lara.mutation.Mutator");
laraImport("kadabra.KadabraNodes");
laraImport("weaver.WeaverJps");
laraImport("weaver.Query");

class IdentifierReplacerMutator extends Mutator {
    constructor($result, $original) {
        //Parent constructor
        super("IdentifierReplacerMutator");

        this.$expr = $result;

        this.mutationPoints = [];

        //To store the identifiers according to the package
        this.identifiersMap = new Map();

        this.currentIndex = 0;
        this.mutationPoint = undefined;
        this.previousValue = undefined;


    }

    static _isCompatible = function (type1, type2) {
        return type1 === type2;
    }

    /*** IMPLEMENTATION OF INSTANCE METHODS ***/


    addJp($joinpoint) {

        if ($joinpoint.instanceOf("expression"))
            if ($joinpoint.code.match(/^R\..*/)) {
                let tempList = this.identifiersMap.get($joinpoint.typeReference.package);

                if (tempList === undefined) { //Creating or updating the entry
                    tempList = [$joinpoint];
                } else {
                    if (tempList.length === 1) //To add the first to the mutation points
                        this.mutationPoints.push(tempList[0]);

                    tempList.push($joinpoint);

                    this.mutationPoints.push($joinpoint);
                }

                this.identifiersMap.set($joinpoint.typeReference.package, tempList); //Saving the entry

                return true;
            }

        return false;
    }

    hasMutations() {
        return this.currentIndex < this.mutationPoints.length;
    }

    getMutationPoint() {
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

    _mutatePrivate() {

        this.mutationPoint = this.mutationPoints[this.currentIndex];
        this.currentIndex++;

        this.previousValue = this.mutationPoint.copy();


        let tempList = this.identifiersMap.get(this.mutationPoint.typeReference.package);

        let indexOfMutationPoint = tempList.indexOf(this.mutationPoint);

        //TODO: CHECK IF IT IS THE PROPER WAY TO DO IT
        if (indexOfMutationPoint < tempList.length - 1)
            this.mutationPoint = this.mutationPoint.insertReplace(tempList[indexOfMutationPoint + 1].copy());
        else
            this.mutationPoint = this.mutationPoint.insertReplace(tempList[0].copy());


        println("/*--------------------------------------*/");
        println("Mutating operator n." + this.currentIndex + ": " + this.previousValue + " to " + this.mutationPoint);
        println("/*--------------------------------------*/");

    }

    _restorePrivate() {
        // Restore operator
        println("Restoring: " + this.mutationPoint + " to " + this.previousValue);
        this.mutationPoint = this.mutationPoint.insertReplace(this.previousValue).copy();
        this.previousValue = undefined;
        this.mutationPoint = undefined;
    }
}
