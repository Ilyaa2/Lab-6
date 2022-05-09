package incapsulatedCommands;

import сollectionData.MyCollection;
import utils.CollectionManager;
import utils.Command;

import java.io.Serializable;

public class Help implements Command, Serializable {
    private CollectionManager collectionManager;
    private static final long serialVersionUID =111111111111L;

    public Help(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute(MyCollection myCollection) {
        return collectionManager.help();
    }
    @Override
    public String getDescription() {
        return "Help";
    }
}
