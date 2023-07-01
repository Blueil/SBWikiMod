package com.github.blueil.sbwikimod;

import com.github.blueil.sbwikimod.commands.Commands;
import com.google.gson.Gson;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = "sbwikimod", version = "1.0.0")
public class SBWikiMod {
    public static final Logger LOGGER = LogManager.getLogger("SBWikiMod");
    public static final Gson GSON = new Gson();

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
    }

    @Mod.EventHandler
    public void preinit(FMLPreInitializationEvent event) {
        new Commands();
    }
}
