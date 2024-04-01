package moe.nea.potatocrime.registry

import net.minecraft.text.MutableText
import net.minecraft.text.Text

object PotatoTranslations {
    val allTranslations = mutableListOf<PT>()

    data class PT(val name: String, val default: String) {
        fun format(vararg params: String): MutableText {
            return Text.translatable(translationKey, *params)
        }

        val translationKey = "potato-crime.text.$name"

        init {
            allTranslations.add(this)
        }
    }

    val noCarrotsToDeposit = PT("no-carrots", "No carrots to deposit.")
    val itemGroup = PT("item-group", "Potato Crimes")
    val contrabandFillText = PT("fill-level", "Hidden carrots: %s/1000.")

}