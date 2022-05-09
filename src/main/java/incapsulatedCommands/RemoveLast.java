package incapsulatedCommands;

import сollectionData.MyCollection;
import utils.CollectionManager;
import utils.Command;

import java.io.Serializable;

public class RemoveLast implements Command, Serializable {
    private CollectionManager collectionManager;
    private static final long serialVersionUID =93L;

    public RemoveLast(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute(MyCollection myCollection) {
        return collectionManager.removeLast(myCollection);
    }
    @Override
    public String getDescription() {
        return "RemoveLast";
    }
}
