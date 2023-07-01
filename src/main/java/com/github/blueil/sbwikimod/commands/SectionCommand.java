package com.github.blueil.sbwikimod.commands;

import com.github.blueil.sbwikimod.ApiUtil;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;
import org.jsoup.Jsoup;
import org.jsoup.internal.StringUtil;

public class SectionCommand extends ClientCommandBase {

    protected SectionCommand() {
        super("wikisection");
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length == 0) {
            sendMessage(EnumChatFormatting.RED + "No pageid argument found!");
            return;
        }
        if (args.length == 1) {
            sendMessage(EnumChatFormatting.RED + "No sectionid argument found!");
            return;
        }
        if (args.length > 2) {
            sendMessage(EnumChatFormatting.RED + "Too much arguments found!");
            return;
        }
        if (!StringUtil.isNumeric(args[0]) || !StringUtil.isNumeric(args[1])) {
            sendMessage(EnumChatFormatting.RED + "The arguments must be a number!");
            return;
        }
        sendMessage(EnumChatFormatting.YELLOW + "Parsing sections...");
        ApiUtil.makeGetRequest("https://hypixel-skyblock.fandom.com/api.php?action=parse&format=json&prop=text&disabletoc=1&pageid=" + args[0] + "&section=" + args[1])
                .thenAccept(json -> {
                    if (json == null) {
                        sendMessage(EnumChatFormatting.RED + "Something went wrong!");
                        return;
                    }
                    String html = json.getAsJsonObject().getAsJsonObject("parse").getAsJsonObject("text").get("*").getAsString();
                    String text = Jsoup.parse(html).text().replace(". ", ".\n\n" + EnumChatFormatting.GRAY + " - " + EnumChatFormatting.GREEN).replaceFirst("^.+\\[ ] ", "");
                    sendMessage(EnumChatFormatting.GRAY + " - " + EnumChatFormatting.GREEN + text);
                });
    }
}
