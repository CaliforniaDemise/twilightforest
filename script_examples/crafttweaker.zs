import mods.twilightforest.Transformation;
import mods.twilightforest.UncraftingTable;

##### Transformation Powder #####
// Adds Mooshroom to Cow transformation. It is not two way.
Transformation.addEntityTransformation(<entity:minecraft:mooshroom>, <entity:minecraft:cow>);

// Adds Polar Bear to Rabbit transformation. It is not two way.
Transformation.addEntityTransformation("minecraft:polar_bear", "minecraft:rabbit");

// Removes Cow to Deer transformation
Transformation.removeEntityTransformation(<entity:minecraft:cow>);

// Removes Sheep to Bighorn Sheep transformation
Transformation.removeEntityTransformation("minecraft:sheep");

/*
    The parameters can be:
    Transformation.addBlockTransformation(IIngredient from, IItemStack to);
    Transformation.addBlockTransformation(IBlockState from, IBlockState to, @Optional int[] metadatas);
    Transformation.addBlockTransformation(IBlockDefinition from, IBlockStateMatcher matcher, IItemStack to); // See https://docs.blamejared.com/1.12/en/Vanilla/Blocks/IBlockStateMatcher for more information
*/
// Adds Grass to Dirt transformation. It is not two way.
Transformation.addBlockTransformation(<minecraft:grass>, <minecraft:dirt>);

// Adds Stone to Iron Block transformation. It is not two way.
Transformation.addBlockTransformation(<minecraft:stone:*>, <minecraft:iron_block>);

// Adds Iron Block to Gold Block transformation.
Transformation.addBlockTransformation(<ore:blockIron>, <minecraft:gold_block>);

// Removes all transformation recipes.
Transformation.removeAll();

##### Uncrafting Table #####
// Returns 4 Iron Ingots with specific shape when Stone is given as input. Increments outputs repair cost by 4.
UncraftingTable.addShaped(<minecraft:stone>, 4, [[<ore:ingotIron>, null, <ore:ingotIron>], [<ore:ingotIron>, null, <ore:ingotIron>]]);

// Returns a Stone and Iron Block from dirt. Increments outputs repair cost by 3.
UncraftingTable.addShapeless(<minecraft:dirt>, 3, [<minecraft:stone>, <minecraft:iron_block>]);

// Adds Diamond to output blacklist.
UncraftingTable.addStackToList(<ore:gemDiamond>);

// Adds recipe to graylist. The list will be blacklist or whitelist based on 'whitelistUncrafting' value in config.
UncraftingTable.addRecipeToList("minecraft:furnace");

##### Boss Events #####
/*
    All boss events inherit mods.twilightforest.event.IBossEvent.

    The boss events include this variables:
    IWorld             |  world   - The world spawner is in. See https://docs.blamejared.com/1.12/en/Vanilla/World/IWorld for more information.
    IBlockPos          |  pos     - Position of the spawner. See https://docs.blamejared.com/1.12/en/Vanilla/World/IBlockPos for more information.
    string             |  variant - The boss type spawner will spawn by default. Variants are listed below.
    IEntityLivingBase  |  boss    - The boss spawner will spawn by default. See https://docs.blamejared.com/1.12/en/Vanilla/Entities/IEntityLivingBase for more information.

    None of them are mutable.

    The values variant can be:
      naga
      lich
      hydra
      ur_ghast
      knight_phantom
      snow_queen
      minoshroom
      alpha_yeti
      final_boss
*/

/*
    IEventManager.onBossConstruction(event as mods.twilightforest.event.BossConstructionEvent)
    import mods.twilightforest.event.BossConstructionEvent if needed.

    This event inherits mods.twilightforest.event.IBossEvent.
    This means variables of IBossEvent are included in this event.

    This event also includes this method:
    setBoss(IEntityLivingBase living) | Sets boss to spawn. Even after setting using this method, boss getter will still going to return the default boss.
*/
// Example: Spawn sheep instead of naga
events.onBossConstruction(function (event as mods.twilightforest.event.BossConstructionEvent)) {
    if (event.variant == "naga") {
        event.setBoss(<entity:minecraft:sheep>.createEntity(event.world));
    }
});

/*
    IEventManager.onBossSpawn(event as mods.twilightforest.event.BossSpawnEvent)
    import mods.twilightforest.event.BossSpawnEvent if needed.

    This event inherits mods.twilightforest.event.IBossEvent.
    This means variables of IBossEvent are also included in this event.

    This event is IEventHasResult. See https://docs.blamejared.com/1.12/en/Vanilla/Events/Events/IEventHasResult for more information.
    If result is:
        allow: It will spawn the boss even if default check doesn't pass.
        default: It will spawn the boss if default check passes
        deny: It will not spawn the boss even if default check passes.
*/
// Entirely disables boss spawn.
events.onBossSpawn(function (event as mods.twilightforest.event.BossConstructionEvent)) {
    event.setResult("deny");
}

/*
    IEventManager.onBossDeath(event as mods.twilightforest.event.BossDeathEvent)
    import mods.twilightforest.event.BossDeathEvent if needed.

     This event inherits mods.twilightforest.event.IBossEvent.
     This means variables of IBossEvent are also included in this event.

     This event is IEventCancellable. See https://docs.blamejared.com/1.12/en/Vanilla/Events/Events/IEventCancelable for more information.
     If cancelled, it will not grant achievements and if it's Ur Ghast or Phantom Knight it will not generate loot chests.
*/
// Does absolutely nothing. You can add gamestage stages or grant more advancements for example.
events.onBossDeath(function (event as mods.twilightforest.event.BossDeathEvent)) {
}