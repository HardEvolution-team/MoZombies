package com.electrifiedded.mozombies.client;

import com.electrifiedded.archaiclib.legacy.client.LegacyRenderLiving;
import com.electrifiedded.mozombies.CommonProxy;
import com.electrifiedded.mozombies.entity.Zombies;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ClientProxy extends CommonProxy {
    private static final String T = "mozombies:textures/entity/";

    private static <E extends EntityLiving> void biped(Class<E> cls, String tex, boolean zombieModel) {
        ResourceLocation rl = LegacyRenderLiving.tex(T + tex);
        RenderingRegistry.registerEntityRenderingHandler(cls,
                m -> new RenderMoZombie<E>(m, zombieModel ? new ModelZombie() : new ModelBiped(), rl));
    }

    @Override
    public void preInit() {
        biped(Zombies.Notch.class, "ZombieNotch.png", true);
        biped(Zombies.Miner.class, "ZombieMiner.png", true);
        biped(Zombies.Chef.class, "ZombieChef.png", true);
        LegacyRenderLiving.register(Zombies.Creeper.class, ModelCreeper::new, 0.5F, T + "ZombieCreeper.png");
        biped(Zombies.Pirate.class, "ZombiePirate.png", true);
        biped(Zombies.Pa.class, "ZombiePa.png", true);
        biped(Zombies.Nether.class, "NetherZombie.png", true);
        biped(Zombies.Cyborg.class, "ZombieCyborg.png", true);
        biped(Zombies.Dwarf.class, "ZombieDwarf.png", true);
        biped(Zombies.Herobrine.class, "ZombieHerobrine.png", true);
        biped(Zombies.King.class, "ZombieKing.png", true);
        biped(Zombies.Knight.class, "ZombieKnight.png", true);
        biped(Zombies.Disco.class, "DiscoZombie.png", true);
        biped(Zombies.Survivor.class, "Survivor.png", false);
    }
}
