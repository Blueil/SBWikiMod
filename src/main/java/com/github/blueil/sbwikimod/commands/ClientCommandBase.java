package com.github.blueil.sbwikimod.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

import java.util.Arrays;
import java.util.List;

public abstract class ClientCommandBase extends CommandBase {
    private final String name;
    private final List<String> commandAliases;

    protected ClientCommandBase(String name, String... commandAliases) {
        this.name = name;
        this.commandAliases = Arrays.asList(commandAliases);
    }

    @Override
    public List<String> getCommandAliases() {
        return this.commandAliases;
    }

    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/" + name;
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    public void sendMessage(String msg) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg));
    }

    public void sendMessage(String msg, ClickEvent clickEvent) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg)
                .setChatStyle(new ChatStyle().setChatClickEvent(clickEvent)));
    }

    public void sendMessage(String msg, HoverEvent hoverEvent) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg)
                .setChatStyle(new ChatStyle().setChatHoverEvent(hoverEvent)));
    }

    public void sendMessage(String msg, ClickEvent clickEvent, HoverEvent hoverEvent) {
        Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(msg)
                .setChatStyle(new ChatStyle()
                        .setChatClickEvent(clickEvent)
                        .setChatHoverEvent(hoverEvent)
                ));
    }

}
