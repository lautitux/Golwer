package com.lautiux.golwer.datagen

import com.lautiux.golwer.block.MBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider
import net.minecraft.block.Blocks
import net.minecraft.data.server.recipe.RecipeExporter
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder
import net.minecraft.data.server.recipe.ShapelessRecipeJsonBuilder
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.book.RecipeCategory
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.registry.tag.ItemTags
import net.minecraft.registry.tag.TagKey
import java.util.concurrent.CompletableFuture

class MRecipieProvider(
    generator: FabricDataOutput,
    registryLookup: CompletableFuture<WrapperLookup>
) : FabricRecipeProvider(generator, registryLookup) {
    override fun generate(exporter: RecipeExporter?) {
        // FLOWERED_CARVED_PUMPKIN_BLOCK recipe
        ShapelessRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, MBlocks.FLOWERED_CARVED_PUMPKIN_BLOCK)
            .input(ItemTags.FLOWERS)
            .input(Blocks.CARVED_PUMPKIN)
            .criterion(
                FabricRecipeProvider.hasItem(Blocks.CARVED_PUMPKIN),
                FabricRecipeProvider.conditionsFromItem(Blocks.CARVED_PUMPKIN)
            )
            .offerTo(exporter)

        // FLOWER_BLOCK recipe
        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, MBlocks.FLOWER_BLOCK)
            .pattern("fff")
            .pattern("fdf")
            .pattern("fff")
            .input('f', ItemTags.FLOWERS)
            .input('d', ItemTags.DIRT)
            .criterion(
                FabricRecipeProvider.hasItem(Items.DANDELION),
                FabricRecipeProvider.conditionsFromTag(ItemTags.FLOWERS)
            )
            .criterion(
                FabricRecipeProvider.hasItem(Items.DIRT),
                FabricRecipeProvider.conditionsFromTag(ItemTags.DIRT)
            )
            .offerTo(exporter)
    }
}