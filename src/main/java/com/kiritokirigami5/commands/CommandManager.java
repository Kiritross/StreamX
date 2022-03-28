package com.kiritokirigami5.commands;

import com.kiritokirigami5.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    public CommandManager() {
        subCommands.add(new ReloadCommand());
        subCommands.add(new StreamCommand());
        subCommands.add(new LinksCommand());

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player){
            Player p = (Player)sender;

            if (args.length > 0){
                for (int i = 0; i<getSubCommands().size(); i++){
                    if (args[0].equalsIgnoreCase(getSubCommands().get(i).getName())){
                        getSubCommands().get(i).perform(p, args);
                        break;
                    }
                }
            }else if (args.length == 0){
                p.sendMessage("-------------------------------------");
                for (int i = 0; i < getSubCommands().size(); i++){
                    p.sendMessage(getSubCommands().get(i).getSyntax()+" - "+getSubCommands().get(i).getDescription());
                }
                p.sendMessage("-------------------------------------");
            }
        }


        return true;
    }

    public ArrayList<SubCommand> getSubCommands() {
        return subCommands;
    }
}
