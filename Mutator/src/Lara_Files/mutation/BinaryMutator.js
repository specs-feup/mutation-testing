laraImport("lara.mutation.Mutator");
laraImport("kadabra.KadabraNodes");
laraImport("weaver.WeaverJps");
laraImport("weaver.Weaver");

class BinaryMutator extends Mutator {
  constructor($result, $original) {
    super("BinaryMutator");

    this.$expr = $result;
    this.$original = $original;
    this.mutationPoints = [];
    this.currentIndex = 0;
    this.mutationPoint = undefined;
    this.previousValue = undefined;
  }

  static _isCompatible(type1, type2) {
    return type1 === type2;
  }

  /*** IMPLEMENTATION OF INSTANCE METHODS ***/
  addJp($joinpoint) {
    if (
      $joinpoint.instanceOf("binaryExpression") &&
      BinaryMutator._isCompatible($joinpoint.operator, this.$original)
    ) {
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

  _mutatePrivate() {
    this.mutationPoint = this.mutationPoints[this.currentIndex];
    this.currentIndex++;

    if (isFunction(this.$expr)) {
      //TODO: Change
      var temp = this.$expr(this.mutationPoint);
      this.mutationPoint = this.mutationPoint.insertReplace(temp);
    } else {
      this.previousValue = this.mutationPoint.copy;
      this.mutationPoint.operator = this.$expr;
    }

    println("/*--------------------------------------*/");
    println(
      "Mutating operator n." +
        this.currentIndex +
        ": " +
        this.previousValue +
        " to " +
        this.mutationPoint
    );
    println("/*--------------------------------------*/");
  }

  _restorePrivate() {
    // Restore operator
    println(
      "Restoring: " +
        this.mutationPoint.operator +
        " to " +
        this.previousValue.operator
    );
    this.mutationPoint.operator = this.previousValue.operator;
    this.previousValue = undefined;
    this.mutationPoint = undefined;
  }
}
