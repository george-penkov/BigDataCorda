package com.template.contracts

import com.template.states.AppearState
import net.corda.core.contracts.CommandData
import net.corda.core.contracts.Contract
import net.corda.core.transactions.LedgerTransaction

// ************
// * Contract *
// ************

class AppearContract : Contract {
    companion object {
        // Used to identify our contract when building a transaction.
        const val ID = "com.template.contracts.TemplateContract"
    }

    // A transaction is valid if the verify() function of the contract of all the transaction's input and output states
    // does not throw an exception.
    override fun verify(tx: LedgerTransaction) {
        val out = tx.outputsOfType<AppearState>().single()
        require(out.administration != out.appliccant){"Error 1 : Administration equals applicant "}
        require(out.appear.isNotEmpty()){"Error 2 : No Message"}
    }

    // Used to indicate the transaction's intent.
    interface Commands : CommandData {
        class Action : Commands
    }
}