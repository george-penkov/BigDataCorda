package com.template.flows

import co.paralleluniverse.fibers.Suspendable
import com.template.contracts.AppearContract
import com.template.states.AppearState
import net.corda.core.contracts.Command
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker

// *********
// * Flows *
// *********
@InitiatingFlow
@StartableByRPC
class Initiator(val administration:Party,val appear :String) : FlowLogic<Unit>() {
    override val progressTracker = ProgressTracker()

    @Suspendable
    override fun call() {
        val notary = serviceHub.networkMapCache.notaryIdentities[0]
        val state = AppearState(ourIdentity,administration,appear)
        val command = Command(AppearContract.Commands.Action(),ourIdentity.owningKey)
        val txBuilder = TransactionBuilder(notary=notary).addOutputState(state,AppearContract.ID).addCommand(command)
        val signTx = serviceHub.signInitialTransaction(txBuilder)
        val responderSession =initiateFlow(administration)
        subFlow(FinalityFlow(signTx,responderSession))
    }
}

@InitiatedBy(Initiator::class)
class Responder(val counterpartySession: FlowSession) : FlowLogic<Unit>() {
    @Suspendable
    override fun call() {
        subFlow(ReceiveFinalityFlow(counterpartySession))
    }
}
