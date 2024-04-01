package moe.nea.potatocrime.entity

import moe.nea.potatocrime.registry.PotatoRegistry
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.goal.*
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.attribute.EntityAttributes
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.mob.PathAwareEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.world.World

class PotatoGuardEntity(entityType: EntityType<out PotatoGuardEntity>, world: World?) :
    PathAwareEntity(entityType, world) {
    companion object {
        fun createMobAttributes(): DefaultAttributeContainer.Builder {
            return HostileEntity.createHostileAttributes()
                .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 35.0)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 10.0)
                .add(EntityAttributes.GENERIC_ARMOR, 4.0)
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 40.0)
        }
    }


    override fun initGoals() {
        goalSelector.add(3, MeleeAttackGoal(this, 1.0, false))
        goalSelector.add(8, LookAroundGoal(this))
        goalSelector.add(7, WanderAroundFarGoal(this, 1.0))
        goalSelector.add(6, object : Goal() {
            override fun canStart(): Boolean {
                return this@PotatoGuardEntity.target == null && this@PotatoGuardEntity.getRandom().nextFloat() < 0.01
            }
        })
        targetSelector.add(
            1,
            ActiveTargetGoal(
                this,
                PlayerEntity::class.java,
                true
            ) { player ->
                (player as PlayerEntity)
                    .inventory
                    .getMatchingStacks { it.isIn(PotatoRegistry.carrotIshItems) }
                    .isNotEmpty()
            }
        )
    }

}
