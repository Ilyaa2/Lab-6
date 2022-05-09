package incapsulatedCommands;

import сollectionData.MyCollection;
import utils.CollectionManager;
import utils.Command;

import java.io.Serializable;

public class RemoveByID implements Command, Serializable {
    private Integer index;
    private CollectionManager collectionManager;
    private static final long serialVersionUID =90000000000L;

    public RemoveByID(CollectionManager collectionManager, Integer index) {
        this.collectionManager = collectionManager;
        this.index = index;
    }

    public String execute(MyCollection myCollection) {
        return collectionManager.removeByID(myCollection,index);
    }
    @Override
    public String getDescription() {
        return "RemoveByID";
    }
}
