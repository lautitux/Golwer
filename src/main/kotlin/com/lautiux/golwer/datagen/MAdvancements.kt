package com.lautiux.golwer.datagen

import com.lautiux.golwer.Golwer
import com.lautiux.golwer.block.MBlocks
import com.lautiux.golwer.entity.MEntities
import com.lautiux.golwer.item.MItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricAdvancementProvider
import net.minecraft.advancement.Advancement
import net.minecraft.advancement.AdvancementCriterion
import net.minecraft.advancement.AdvancementEntry
import net.minecraft.advancement.AdvancementFrame
import net.minecraft.advancement.criterion.ImpossibleCriterion
import net.minecraft.advancement.criterion.InventoryChangedCriterion
import net.minecraft.advancement.criterion.SummonedEntityCriterion
import net.minecraft.data.server.advancement.AdvancementTabGenerator
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.predicate.entity.EntityPredicate
import net.minecraft.predicate.item.ItemPredicate
import net.minecraft.registry.RegistryWrapper.WrapperLookup
import net.minecraft.registry.tag.ItemTags
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class MAdvancements(
    output: FabricDataOutput,
    registryLookup: CompletableFuture<WrapperLookup>
) : FabricAdvancementProvider(output, registryLookup) {
    override fun generateAdvancement(registryLookup: WrapperLookup?, consumer: Consumer<AdvancementEntry>?) {
        val rootAdvancement = Advancement.Builder.create()
            .display(
                Items.DANDELION,
                Text.literal("Golwer"),
                Text.translatable("advancement.golwer.thanks"),
                Identifier.of("textures/gui/advancements/backgrounds/adventure.png"),
                AdvancementFrame.TASK,
                false,
                false,
                false
            )
            .criterion(
                "installed_golwer",
                InventoryChangedCriterion.Conditions.items(ItemPredicate.Builder.create().tag(ItemTags.FLOWERS))
            )
            .build(consumer, "${Golwer.MOD_ID}:advancement_tab_icon")
        val summonGolem = Advancement.Builder.create()
            .parent(rootAdvancement)
            .display(
                MBlocks.FLOWERED_CARVED_PUMPKIN_BLOCK,
                Text.translatable("advancement.spawn_flower_golem.title"),
                Text.translatable("advancement.spawn_flower_golem.description"),
                Identifier.of("textures/gui/advancements/backgrounds/adventure.png"),
                AdvancementFrame.TASK,
                true,
                true,
                false
            )
            .criterion(
                "summoned_flower_golem",
                SummonedEntityCriterion.Conditions.create(
                    EntityPredicate.Builder.create()
                        .type(MEntities.FLOWER_GOLEM) // Replace with your entity type
                )
            )
            .build(consumer, "${Golwer.MOD_ID}:summon_golem")
//        Advancement.Builder.create()
//            .parent(summonGolem)
//            .display(
//                Items.BONE_MEAL,
//                Text.translatable("advancement.flower_golem_pollinated.title"),
//                Text.translatable("advancement.flower_golem_pollinated.description"),
//                Identifier.of("textures/gui/advancements/backgrounds/adventure.png"),
//                AdvancementFrame.TASK,
//                true,
//                true,
//                true
//            )
//            .criterion(
//                "watch_flower_golem_magic",
//                FlowerGolemPolinatedPlantCriterion.Conditions.create()
//            )
//            .build(consumer, "${Golwer.MOD_ID}:watch_flower_golem_magic")
    }
}