package com.electrifiedded.mozombies;

import com.electrifiedded.archaiclib.legacy.registry.LegacyGameRegistry;
import com.electrifiedded.archaiclib.legacy.world.LegacySpawns;
import com.electrifiedded.archaiclib.original.OriginalModAssets;
import com.electrifiedded.mozombies.entity.Zombies;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.util.List;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:archaiclib@[1.25.0,)")
public class MoZombiesMod {
    public static final String ORIGINAL_NAME = "mo_zombies_2.1.0.jar - Mo Zombies 2.1.0 for 1.7.10 (Coocoo617)";
    public static final String ORIGINAL_URL = "https://www.curseforge.com/minecraft/mc-mods/mo-zombies";

    @Mod.Instance(Reference.MOD_ID)
    public static MoZombiesMod instance;

    @SidedProxy(clientSide = "com.electrifiedded.mozombies.client.ClientProxy", serverSide = "com.electrifiedded.mozombies.CommonProxy")
    public static CommonProxy proxy;

    private int nextId = 0;

    public MoZombiesMod() {
        // textures come from the player's own copy of the original jar (archaic_originals/); our lang is shipped
        OriginalModAssets.hide(Reference.MOD_ID, "lang/en_us.lang");
        OriginalModAssets.hide(Reference.MOD_ID, "lang/ru_ru.lang");
    }

    private void entity(Class<? extends EntityLiving> cls, String name, int egg1, int egg2) {
        LegacyGameRegistry.registerModEntity(cls, name, nextId++, this, 64, 1, true, egg1, egg2);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent e) {
        OriginalModAssets.require(Reference.MOD_ID, Reference.MOD_ID, "Mo Zombies 2.1.0", ORIGINAL_URL, Reference.MOD_ID);
        entity(Zombies.Notch.class, "zombie_notch", 44975, 7969893);
        entity(Zombies.Miner.class, "zombie_miner", 44975, 7969893);
        entity(Zombies.Chef.class, "zombie_chef", 44975, 7969893);
        entity(Zombies.Creeper.class, "zombie_creeper", 44975, 7969893);
        entity(Zombies.Pirate.class, "zombie_pirate", 44975, 7969893);
        entity(Zombies.Pa.class, "zombie_pa", 44975, 7969893);
        entity(Zombies.Nether.class, "nether_zombie", 44975, 7969893);
        entity(Zombies.Cyborg.class, "zombie_cyborg", 44975, 7969893);
        entity(Zombies.Dwarf.class, "zombie_dwarf", 44975, 7969893);
        entity(Zombies.Herobrine.class, "zombie_herobrine", 44975, 7969893);
        entity(Zombies.King.class, "zombie_king", 44975, 7969893);
        entity(Zombies.Knight.class, "zombie_knight", 44975, 7969893);
        entity(Zombies.Disco.class, "disco_zombie", 44975, 7969893);
        entity(Zombies.Survivor.class, "survivor", 10506797, 9127187);
        proxy.preInit();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        // original: every overworld land biome (weight 100, groups of 4); Nether Zombie only in the Nether
        List<Biome> overworld = LegacySpawns.biomes(LegacySpawns::isOverworld);
        for (Class<? extends EntityLiving> c : new Class[]{Zombies.Notch.class, Zombies.Miner.class, Zombies.Chef.class,
                Zombies.Creeper.class, Zombies.Pirate.class, Zombies.Pa.class, Zombies.Cyborg.class, Zombies.Dwarf.class,
                Zombies.Herobrine.class, Zombies.King.class, Zombies.Knight.class, Zombies.Disco.class}) {
            LegacySpawns.add(c, 100, 4, 4, EnumCreatureType.MONSTER, overworld);
        }
        LegacySpawns.addSpawn(Zombies.Nether.class, 100, 4, 4, EnumCreatureType.MONSTER, Biomes.HELL);
        LegacySpawns.add(Zombies.Survivor.class, 2, 0, 1, EnumCreatureType.CREATURE, overworld);
    }
}
