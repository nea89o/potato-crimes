package moe.nea.potatocrime

import moe.nea.potatocrime.registry.PotatoRegistry
import moe.nea.potatocrime.registry.PotatoTranslations
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Models
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class PotatoCrimeDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()
        pack.addProvider(::NameProvider)
        pack.addProvider(::RecipeProvider)
        pack.addProvider(::DefaultModels)
    }
}

class RecipeProvider(
    output: FabricDataOutput?,
    registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>?
) : FabricRecipeProvider(output, registriesFuture) {
    override fun generate(exporter: RecipeExporter) {
        ShapelessRecipeJsonBuilder.create(RecipeCategory.TOOLS, PotatoRegistry.contraband)
            .input(Items.PAPER, 3)
            .input(Items.BROWN_DYE)
            .input(Items.CARROT)
            .criterion(hasItem(Items.PAPER), conditionsFromItem(Items.PAPER))
            .criterion(hasItem(Items.CARROT), conditionsFromItem(Items.CARROT))
            .offerTo(exporter)
    }
}

class DefaultModels(output: FabricDataOutput?) : FabricModelProvider(output) {
    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {

    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        itemModelGenerator.register(PotatoRegistry.contraband, Models.GENERATED)
    }

}


class NameProvider(dataOutput: FabricDataOutput, registryLookup: CompletableFuture<RegistryWrapper.WrapperLookup>) :
    FabricLanguageProvider(dataOutput, registryLookup) {
    override fun generateTranslations(
        registryLookup: RegistryWrapper.WrapperLookup,
        translationBuilder: TranslationBuilder
    ) {
        translationBuilder.add(PotatoRegistry.contraband, "Contraband")
        PotatoTranslations.allTranslations.forEach {
            translationBuilder.add(it.translationKey, it.default)
        }
    }
}