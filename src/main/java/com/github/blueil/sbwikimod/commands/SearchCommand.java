package com.github.blueil.sbwikimod.commands;

import com.github.blueil.sbwikimod.ApiUtil;
import com.github.blueil.sbwikimod.SBWikiMod;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class SearchCommand extends ClientCommandBase {
    protected SearchCommand() {
        super("searchwiki", "swiki", "sw");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException {
        if (args.length == 0) {
            sendMessage(EnumChatFormatting.RED + "No query parameters found...");
            return;
        }
        try {
            sendMessage(EnumChatFormatting.YELLOW + "Searching...");
            ApiUtil.makeGetRequest("https://hypixel-skyblock.fandom.com/api.php?action=query&format=json&prop=&list=search&continue=-%7C%7C&titles=&srlimit=3&srinfo=&srprop=&srsearch=" + URLEncoder.encode(String.join(" ", args), "UTF-8"))
                    .thenAccept(json -> {
                        if (json == null) {
                            sendMessage(EnumChatFormatting.RED + "Something went wrong!");
                            return;
                        }
                        JsonArray queries = json.getAsJsonObject().getAsJsonObject("query").getAsJsonArray("search");
                        sendMessage(EnumChatFormatting.GREEN + "Choose a title: ");
                        for (JsonElement query : queries) {
                            String title = query.getAsJsonObject().get("title").getAsString();
                            int pageId = query.getAsJsonObject().get("pageid").getAsInt();
                            sendMessage(
                                    EnumChatFormatting.GRAY + " - " + EnumChatFormatting.YELLOW + title,
                                    new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wikipage " + pageId),
                                    new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(EnumChatFormatting.YELLOW + "Show content of page " + title + "."))
                            );
                        }
                    });
        } catch (UnsupportedEncodingException e) {
            SBWikiMod.LOGGER.error(e);
        }
    }
}
