
// Transformation Powder - Aliases: transformation, Transformation, transformationpowder, transformation_powder, TransformationPowder //
// Sheep to Cow transformation
mods.twilightforest.transformationpowder.addEntity(entity("minecraft:sheep"), entity("minecraft:cow"))

// Creeper to Spider transformation
mods.twilightforest.transformationpowder.addEntity(resource("minecraft", "creeper"), resource("minecraft", "spider"))

// Removes Cow to Deer transformation
mods.twilightforest.transformationpowder.removeEntity(entity("minecraft:cow"))

// Removes all entity transformations
mods.twilightforest.transformationpowder.removeAll()

// Adds any blockstates with "minecraft:stone" id to White Wool transformation
mods.twilightforest.transformationpowder.addBlock(block("minecraft:stone"), blockstate("minecraft:wool"))

// Adds White Wool to Stone transformation
mods.twilightforest.transformationpowder.addBlock(block("minecraft:wool"), s -> s.getBlock().getMetaFromState(s) == 0, blockstate("minecraft:stone"))

// Adds any Minecraft Planks to Spruce Planks transformation
mods.twilightforest.transformationpowder.addBlock(item("minecraft:planks:*"), item("minecraft:planks:1"))

// Uncrafting - Aliases: uncrafting, Uncrafting, uncraftingtable, uncrafting_table, UncraftingTable //
// Note: needed experience can be zero
// Adds Iron Block + 5 exp to 4 Iron Ingots shaped recipe
mods.twilightforest.uncrafting.addShaped(item('minecraft:iron_block'), 5, [[item('minecraft:iron_ingot'), null, item('minecraft:iron_ingot')], [null, null, null], [item('minecraft:iron_ingot'), null, item('minecraft:iron_ingot')]])

// Adds Dirt + 2 exp to Stone and Gravel shapeless recipe
mods.twilightforest.uncrafting.addShapeless(item('minecraft:dirt'), 2, [item('minecraft:stone'), item('minecraft:gravel')])

// Adds Iron Ingot + 3 exp to Gold Ingot and Coal shaped recipe
mods.twilightforest.uncrafting.shapedBuilder().input(item('minecraft:iron_ingot')).cost(3).outputs([[item('minecraft:gold_ingot'), item('minecraft:coal')]]).register()

// Adds Redstone + 2 exp to Stone shapeless recipe
mods.twilightforest.uncrafting.shapelessBuilder().input(item('minecraft:redstone')).cost(2).outputs([item('minecraft:stone')]).register()

// Adds Oak Planks to uncrafting blacklist.
// You can turn list to act like whitelist instead in config.
// mods.twilightforest.uncrafting.addItemToList(item('minecraft:planks') USE ORE DICTS INSTEAD
ore("uncraftingList").add(item('minecraft:planks'))

// Adds Clay to blocks Ore Magnet will pull.
// Note: By default it has Iron Ore, Diamond Ore, Emerald Ore, Lapis Ore, Redstone Ore, Quartz Ore and Liveroots.
// You can remove them from this ore dictionary to make them unpullable.
ore("blockMagnetable").add(item('minecraft:clay'))

// Removes the entire Oak Planks recipe from Uncrafting Table
mods.twilightforest.uncrafting.addRecipeToList('minecraft:oak_planks')

// Boss Events //
import twilightforest.enums.BossVariant
import net.minecraftforge.fml.common.eventhandler.Event.Result
/*
    The boss events include this variables:
    World                   |  world   - The world spawner is in.
    BlockPos                |  pos     - Position of the spawner.
    BossVariant             |  variant - The boss type spawner will spawn by default. Variants are listed below.
    EntityLiving            |  boss    - The boss spawner will spawn by default.

    BossVariant is an enum that can be these values:
      NAGA
      LICH
      HYDRA
      UR_GHAST
      KNIGHT_PHANTOM
      SNOW_QUEEN
      MINOSHROOM
      ALPHA_YETI
      FINAL_BOSS
*/


/*
    event_manager.listen { BossEvent.Construction event -> ... }
    import twilightforest.events.BossEvent(.Construction) if needed.

    This event inherits twilightforest.events.BossEvent.
    This means variables of BossEvent are included in this event.

    This event also includes this method:
    getState() | Returns the block state of boss spawner.
    getSpawner() | Returns the tile entity of boss spawner.
    setBoss(Entity living) | Sets boss to spawn. living needs to be EntityLiving. Even after setting using this method, boss getter will still going to return the default boss.
*/
// Example: Spawn sheep instead of naga
event_manager.listen { twilightforest.events.BossEvent.Construction event ->
    if (event.variant == BossVariant.NAGA) {
        event.setBoss(entity("minecraft:magma_cube").newInstance(event.world))
    }
}

/*
    event_manager.listen { BossEvent.Spawning event -> ... }
    import twilightforest.events.BossEvent(.Spawning) if needed.

    This event inherits twilightforest.events.BossEvent.
    This means variables of BossEvent are also included in this event.

    This event has result.
    If result is:
        ALLOW: It will spawn the boss even if default check doesn't pass.
        DEFAULT: It will spawn the boss if default check passes
        DENY: It will not spawn the boss even if default check passes.

    This event also includes this method:
    getState() | Returns the block state of boss spawner.
    getSpawner() | Returns the tile entity of boss spawner.
*/
// Example: Entirely disables boss spawn.
event_manager.listen { twilightforest.events.BossEvent.Spawning event ->
    event.result = Result.DENY
}

/*
    event_manager.listen { BossEvent.Death event -> ... }
    import twilightforest.events.BossEvent(.Death) if needed.

    This event inherits twilightforest.events.BossEvent.
    This means variables of BossEvent are also included in this event.

    This event is cancellable. Cancel it with event.canceled = true

    This event also includes this method:
    getSource() | Returns the damage source boss has been killed with. Can be used to understand damage type and such.
*/
// Example: Does absolutely nothing. You can add gamestage stages or grant more advancements for example.
event_manager.listen { twilightforest.events.BossEvent.Death event ->
}