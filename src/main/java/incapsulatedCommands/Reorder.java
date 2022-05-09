package incapsulatedCommands;

import сollectionData.MyCollection;
import utils.CollectionManager;
import utils.Command;

import java.io.Serializable;

public class Reorder implements Command, Serializable {
    private CollectionManager collectionManager;
    private static final long serialVersionUID =914888888883L;

    public Reorder(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute(MyCollection myCollection) {
        return collectionManager.reorder(myCollection);
    }
    @Override
    public String getDescription() {
        return "Reorder";
    }

}
