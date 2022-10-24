laraImport("lara.mutation.IterativeMutation");
laraImport("lara.mutation.MutationResult");


/**
 */
class ConditionalOperatorDeletionMutation extends IterativeMutation {
	constructor() {
		//Parent constructor
		super("ConditionalOperatorDeletionMutation");
	}

	/*** IMPLEMENTATION OF INSTANCE METHODS ***/

	isMutationPoint($jp) {

		if ($jp.instanceOf('if') || $jp.instanceOf('ternary') || $jp.instanceOf('loop')) {

			if ($jp.cond.instanceOf('unaryExpression') && $jp.cond.operator === '!') {
				return true;
			}
		}

		return false;
	}

	//function*
	mutate($jp) {

		let mutation = $jp.copy;

		mutation.cond.insertReplace(mutation.cond.operand.copy());

		debug("/*--------------------------------------*/");
		debug("Mutating operator: " + $jp + " to " + mutation);
		debug("/*--------------------------------------*/");

		return new MutationResult(mutation);

	}
}
