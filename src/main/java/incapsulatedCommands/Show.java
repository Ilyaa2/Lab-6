package incapsulatedCommands;

import сollectionData.MyCollection;
import utils.CollectionManager;
import utils.Command;

import java.io.Serializable;

public class Show implements Command, Serializable {
    private CollectionManager collectionManager;
    private static final long serialVersionUID =2888888L;

    public Show(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute(MyCollection myCollection) {
        return collectionManager.show(myCollection);
    }
    @Override
    public String getDescription() {
        return "Show";
    }
}
