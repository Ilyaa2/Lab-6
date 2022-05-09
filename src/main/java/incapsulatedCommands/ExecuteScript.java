package incapsulatedCommands;

import сollectionData.MyCollection;
import utils.CollectionManager;
import utils.Command;

import java.io.Serializable;
import java.util.ArrayList;

public class ExecuteScript implements Command, Serializable {
    private CollectionManager collectionManager;
    private String filePath;
    private ArrayList<Command> commands;
    private static final long serialVersionUID =1488L;

    public ExecuteScript(CollectionManager collectionManager, String filePath, ArrayList<Command> commands) {
        this.collectionManager = collectionManager;
        this.filePath = filePath;
        this.commands = commands;
    }

    public String execute(MyCollection myCollection) {
        return collectionManager.executeScript( filePath, commands);
    }
    @Override
    public String getDescription() {
        return "ExecuteScript";
    }
}
