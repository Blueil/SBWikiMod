package com.github.blueil.sbwikimod.commands;

import net.minecraftforge.client.ClientCommandHandler;

public class Commands {
    public Commands() {
        registerCommand(new SearchCommand());
        registerCommand(new PageCommand());
        registerCommand(new SectionCommand());
    }

    private void registerCommand(ClientCommandBase command) {
        ClientCommandHandler.instance.registerCommand(command);
    }
}
