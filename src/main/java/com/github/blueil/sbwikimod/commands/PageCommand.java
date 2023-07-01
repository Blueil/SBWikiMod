package com.github.blueil.sbwikimod.commands;

import com.github.blueil.sbwikimod.ApiUtil;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import org.jsoup.internal.StringUtil;

public class PageCommand extends ClientCommandBase {
    protected PageCommand() {
        super("wikipage");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sendMessage(EnumChatFormatting.RED + "No pageid argument found!");
            return;
        }
        if (args.length > 1) {
            sendMessage(EnumChatFormatting.RED + "Too much arguments");
            return;
        }
        if (!StringUtil.isNumeric(args[0])) {
            sendMessage(EnumChatFormatting.RED + "The argument must be a number!");
            return;
        }
        sendMessage(EnumChatFormatting.YELLOW + "Parsing sections...");
        ApiUtil.makeGetRequest("https://hypixel-skyblock.fandom.com/api.php?action=parse&format=json&prop=sections&disabletoc=1&pageid=" + args[0])
                .thenAccept(json -> {
                    if (json == null) {
                        sendMessage(EnumChatFormatting.RED + "Something went wrong!");
                        return;
                    }
                    JsonArray sections = json.getAsJsonObject().getAsJsonObject("parse").getAsJsonArray("sections");
                    sendMessage(EnumChatFormatting.GREEN + "Choose a section: ");
                    for (JsonElement section : sections) {
                        String title = section.getAsJsonObject().get("line").getAsString();
                        int index = section.getAsJsonObject().get("index").getAsInt();
                        sendMessage(
                                EnumChatFormatting.GRAY + " - " + EnumChatFormatting.YELLOW + title,
                                new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wikisection " + args[0] + " " + index),
                                new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(EnumChatFormatting.YELLOW + "Show content of section " + title + "."))
                        );
                    }
                });
    }
}
