package me.heyimblake.proxyparty.commands;

import me.heyimblake.proxyparty.commands.subcommands.*;
import me.heyimblake.proxyparty.partyutils.PartyManager;
import me.heyimblake.proxyparty.partyutils.PartyRole;
import me.heyimblake.proxyparty.utils.Constants;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Created by heyimblake on 10/21/2016.
 *
 * @author heyimblake
 *         https://heyimblake.me
 */
public class PartyCommand extends Command {

    private static Map<String, Class<? extends AnnotatedPartySubCommand>> subCommandClasses = new HashMap<>();
    private static Set<Class<? extends AnnotatedPartySubCommand>> defaultSubCommandClasses = new HashSet<>();

    public PartyCommand() {
        super("party", null);
        defaultSubCommandClasses.add(InviteSubCommand.class);
        defaultSubCommandClasses.add(AcceptSubCommand.class);
        defaultSubCommandClasses.add(DenySubCommand.class);
        defaultSubCommandClasses.add(FindSubCommand.class);
        defaultSubCommandClasses.add(ListSubCommand.class);
        defaultSubCommandClasses.add(InvitedSubCommand.class);
        defaultSubCommandClasses.add(RetractSubCommand.class);
        defaultSubCommandClasses.add(KickSubCommand.class);
        defaultSubCommandClasses.add(ChatSubCommand.class);
        defaultSubCommandClasses.add(WarpSubCommand.class);
        defaultSubCommandClasses.add(LeaveSubCommand.class);
        defaultSubCommandClasses.add(PromoteSubCommand.class);
        defaultSubCommandClasses.add(DisbandSubCommand.class);
        registerDefaultSubCommands();
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof ProxiedPlayer)) {
            sender.sendMessage(Constants.TAG, new TextComponent("You must be a player in order to execute party commands."));
            return;
        }
        ProxiedPlayer player = ((ProxiedPlayer) sender);
        if (args.length > 0) {
            PartySubCommandHandler handler = new PartySubCommandHandler(sender, Arrays.copyOfRange(args, 1, args.length));
            String subCMDInput = args[0];
            for (String key : subCommandClasses.keySet()) {
                Class<? extends AnnotatedPartySubCommand> clazz = subCommandClasses.get(key);
                if (key.equalsIgnoreCase(subCMDInput)) {
                    if (PartyRole.getRoleOf(player) == PartyRole.PARTICIPANT && getSubCommandClassAnnotation(clazz).leaderExclusive()) {
                        TextComponent errmsg = new TextComponent("You must be the party leader in order to do this.");
                        errmsg.setColor(ChatColor.RED);
                        player.sendMessage(Constants.TAG, errmsg);
                        return;
                    }
                    if (handler.getArguments().length == 0 && getSubCommandClassAnnotation(clazz).requiresArgumentCompletion()) {
                        TextComponent usage = new TextComponent(getSubCommandClassAnnotation(clazz).syntax());
                        usage.setColor(ChatColor.AQUA);
                        usage.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party " + getSubCommandClassAnnotation(clazz).subCommand() + " "));
                        usage.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{new TextComponent(ChatColor.YELLOW + "Click to prepare command.")}));
                        player.sendMessage(Constants.TAG, new TextComponent("Usage: "), usage);
                        return;
                    }
                    if (PartyManager.getInstance().getPartyOf(player) == null && getSubCommandClassAnnotation(clazz).mustBeInParty()) {
                        TextComponent errmsg = new TextComponent("You must be in a party in order to do this!");
                        errmsg.setColor(ChatColor.RED);
                        player.sendMessage(Constants.TAG, errmsg);
                        return;
                    }
                    performSubCommand(clazz, handler);
                    return;
                }
            }
            showHelpMessage(player);
            return;
        }
        showHelpMessage(player);
    }

    private void performSubCommand(Class<? extends AnnotatedPartySubCommand> clazz, PartySubCommandHandler handler) {
        try {
            Constructor constructor = clazz.getConstructor(handler.getClass());
            AnnotatedPartySubCommand subCommandInstance = (AnnotatedPartySubCommand) constructor.newInstance(handler);
            if (handler.isSenderProxiedPlayer()) {
                subCommandInstance.runProxiedPlayer();
            } else {
                subCommandInstance.runConsole();
            }
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    private void showHelpMessage(ProxiedPlayer player) {
        TextComponent topMSG = new TextComponent("Party Commands:");
        topMSG.setColor(ChatColor.LIGHT_PURPLE);
        topMSG.setBold(true);
        player.sendMessage(topMSG);
        TextComponent prepareMSG = new TextComponent("Click to prepare this command.");
        prepareMSG.setColor(ChatColor.YELLOW);
        prepareMSG.setItalic(true);
        TextComponent pt1 = new TextComponent("" + '\u25CF' + " ");
        for (Class<? extends AnnotatedPartySubCommand> clazz : subCommandClasses.values()) {

            //Bullet Colors tell if the player can run the command or not. Just for a quick glance.
            ChatColor bulletColor;
            if (getSubCommandClassAnnotation(clazz).subCommand().equalsIgnoreCase("invite")) {
                bulletColor = !PartyManager.getInstance().hasParty(player) || PartyRole.getRoleOf(player) == PartyRole.LEADER ? ChatColor.DARK_GREEN : ChatColor.DARK_RED;
            } else if (getSubCommandClassAnnotation(clazz).mustBeInParty()) {
                bulletColor = PartyManager.getInstance().hasParty(player) ? ChatColor.DARK_GREEN : ChatColor.DARK_RED;
            } else {
                bulletColor = (getSubCommandClassAnnotation(clazz).leaderExclusive() && PartyRole.getRoleOf(player) == PartyRole.LEADER) ? ChatColor.DARK_GREEN : ChatColor.DARK_RED;
            }
            pt1.setColor(bulletColor);

            TextComponent pt2 = new TextComponent(getSubCommandClassAnnotation(clazz).syntax());
            pt2.setColor(ChatColor.AQUA);
            pt2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new BaseComponent[]{prepareMSG}));
            pt2.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/party " + getSubCommandClassAnnotation(clazz).subCommand() + " "));
            TextComponent pt3 = new TextComponent(" - ");
            pt3.setColor(ChatColor.DARK_GRAY);
            TextComponent pt4 = new TextComponent(getSubCommandClassAnnotation(clazz).description());
            pt4.setColor(ChatColor.GRAY);
            player.sendMessage(pt1, pt2, pt3, pt4);
        }
        player.sendMessage(new TextComponent(" "));

    }

    private void registerDefaultSubCommands() {
        defaultSubCommandClasses.forEach(clazz -> registerSubCommand(getSubCommandClassAnnotation(clazz).subCommand(), clazz));
    }

    private void registerSubCommand(String name, Class<? extends AnnotatedPartySubCommand> annotatedPartySubCommand) {
        subCommandClasses.put(name, annotatedPartySubCommand);
    }

    private Map<String, Class<? extends AnnotatedPartySubCommand>> getSubCommandClasses() {
        return subCommandClasses;
    }

    /**
     * Gets the Annotation of a AnnotatedPartySubCommand class.
     *
     * @param clazz the annotatedpartysubcommand class
     * @return Annotation if it exists, null if invalid
     */
    private PartySubCommandExecutor getSubCommandClassAnnotation(Class<? extends AnnotatedPartySubCommand> clazz) {
        if (clazz.isAnnotationPresent(PartySubCommandExecutor.class)) {
            return clazz.getAnnotation(PartySubCommandExecutor.class);
        }
        return null;
    }
}
