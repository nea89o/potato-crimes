package moe.nea.potatocrime

import moe.nea.potatocrime.registry.PotatoRegistry
import moe.nea.potatocrime.registry.PotatoTranslations
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.data.client.BlockStateModelGenerator
import net.minecraft.data.client.ItemModelGenerator
import net.minecraft.data.client.Models
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.Items
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryWrapper
import java.util.concurrent.CompletableFuture

class PotatoCrimeDataGenerator : DataGeneratorEntrypoint {
    override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
        val pack = fabricDataGenerator.createPack()
        pack.addProvider(::NameProvider)
        pack.addProvider(::RecipeProvider)
        pack.addProvider(::DefaultModels)
        pack.addProvider(::TagProvider)
    }
}

class TagProvider(
    output: FabricDataOutput?, registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>?,
) : FabricTagProvider<Item>(output, RegistryKeys.ITEM, registriesFuture) {
    override fun configure(wrapperLookup: RegistryWrapper.WrapperLookup) {
        getOrCreateTagBuilder(PotatoRegistry.carrotIshItems)
            .add(Items.CARROT)
            .add(Items.CARROT_ON_A_STICK)
            .add(Items.GOLDEN_CARROT)
            .add(Items.ORANGE_DYE)
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
        translationBuilder.add(PotatoRegistry.potatoGuard, "Potato Guard")
        translationBuilder.add(PotatoRegistry.contraband, "Smugglers Brown Paper Bag")
        PotatoTranslations.allTranslations.forEach {
            translationBuilder.add(it.translationKey, it.default)
        }
    }
}