laraImport("lara.mutation.Mutator");
laraImport("kadabra.KadabraNodes");
laraImport("weaver.WeaverJps");
laraImport("weaver.Query");

class UnaryMutator extends Mutator {
    constructor($expr, $original) {
        //Parent constructor
        super("UnaryMutator");

        this.$expr = $expr;
        this.$original = $original;

        this.newValue = undefined;

        this.mutationPoints = [];

        this.currentIndex = 0;
        this.previousValue = undefined;

    }



    static _isCompatible(type1, type2) {
        return type1 === type2;
    }

    /*** IMPLEMENTATION OF INSTANCE METHODS ***/
    addJp($joinpoint) {
        if ($joinpoint.instanceOf("unaryExpression") && UnaryMutator._isCompatible($joinpoint.operator, this.$original)) {
            this.mutationPoints.push($joinpoint);
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

    //TODO: Refactor for the undefined
    _mutatePrivate() {

        this.mutationPoint = this.mutationPoints[this.currentIndex];
        println(this.mutationPoint);
        this.currentIndex++;

        if (isFunction(this.$expr)) {
            //TODO: Change
            let temp = this.$expr(this.mutationPoint);
            this.newValue = this.newValue.insertReplace(temp);
        } else {
            if (this.$expr === "undefined") {
                this.previousValue = this.mutationPoint.copy;
                println(this.previousValue.operator);
                this.mutationPoint = this.mutationPoint.insertReplace(this.previousValue.operand);
            } else {
                this.previousValue = this.mutationPoint.copy;
                this.mutationPoint.operator = this.$expr;
            }
            //this.newValue = this.$expr;
        }

        println("/*--------------------------------------*/");
        println("Mutating operator n." + this.currentIndex + ": " + this.previousValue + " to " + this.mutationPoint);
        println("/*--------------------------------------*/");

    }

    _restorePrivate() {
        // Restore operator
        println("Restoring " + this.mutationPoint + " to " + this.previousValue);

        if (this.$expr === "undefined") {
            this.mutationPoint = this.mutationPoint.insertReplace(this.previousValue);
        } else {
            this.mutationPoint.operator = this.previousValue.operator;
        }

        this.previousValue = undefined;
        this.mutationPoint = undefined;
    }
}