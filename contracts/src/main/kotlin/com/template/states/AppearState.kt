package com.template.states

import com.template.contracts.AppearContract
import net.corda.core.contracts.BelongsToContract
import net.corda.core.contracts.ContractState
import net.corda.core.identity.AbstractParty
import net.corda.core.identity.Party

// *********
// * State *
// *********
@BelongsToContract(AppearContract::class)
data class AppearState(val appliccant : Party, val administration: Party,val appear:String):ContractState{
    override val  participants get() = listOf(appliccant,administration)
}
