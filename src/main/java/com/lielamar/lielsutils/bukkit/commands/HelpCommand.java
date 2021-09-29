package com.lielamar.lielsutils.bukkit.commands;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class HelpCommand extends Command {

    private static final int COMMANDS_IN_PAGE = 10;

    private final SuperCommand parent;
    private final HelpRenderer helpRenderer;
    private final HelpHeaderRenderer helpHeaderRenderer;
    private final int commandsInPage;

    public HelpCommand(@NotNull SuperCommand parent, @NotNull HelpRenderer helpRenderer, @NotNull HelpHeaderRenderer helpHeaderRenderer) {
        this(parent, helpRenderer, helpHeaderRenderer, COMMANDS_IN_PAGE);
    }

    public HelpCommand(@NotNull SuperCommand parent, @NotNull HelpRenderer helpRenderer, @NotNull HelpHeaderRenderer helpHeaderRenderer, int commandsInPage) {
        super("help",parent.getPermission() + ".help");

        this.parent = parent;
        this.helpRenderer = helpRenderer;
        this.helpHeaderRenderer = helpHeaderRenderer;
        this.commandsInPage = commandsInPage;
    }


    @Override
    public boolean runCommand(@NotNull CommandSender cs, @NotNull String[] args) {
        int page = 1;

        try {
            page = Integer.parseInt(args[0]);
        } catch (Exception ignored) {}

        if(page < 0 || page > numberOfPages())
            page = 1;

        cs.sendMessage(renderHelpHeader(page));

        int pageStart = (page - 1) * commandsInPage;

        for(int i = 0; i < getLeftCommands(page); i++) {
            Command sub = parent.getSubCommands()[pageStart + i];
            cs.sendMessage(renderHelp(sub));
        }

        cs.sendMessage(renderHelpHeader(page));
        return true;
    }

    @Override
    public List<String> tabOptions(@NotNull CommandSender cs, @NotNull String[] args) {
        List<String> options = new ArrayList<>();

        for(int i = 1; i < numberOfPages() + 1; i++)
            options.add(i + "");

        return options;
    }


    private int numberOfPages() {
        return parent.getSubCommands().length / commandsInPage;
    }

    private int getLeftCommands(int page) {
        int left = parent.getSubCommands().length - ((page - 1) * commandsInPage);
        return Math.min(left, commandsInPage);
    }

    private String renderHelp(Command command) { return helpRenderer.render(command); }
    private String renderHelpHeader(int pageNumber) { return helpHeaderRenderer.render(pageNumber); }


    public interface HelpRenderer { String render(Command command); }
    public interface HelpHeaderRenderer { String render(int pageNumber); }
}