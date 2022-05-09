package incapsulatedCommands;

import сollectionData.MyCollection;
import utils.CollectionManager;
import utils.Command;

import java.io.Serializable;

public class Info implements Command, Serializable {
    private CollectionManager collectionManager;
    private static final long serialVersionUID =7777777777L;

    public Info(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public String execute(MyCollection myCollection) {
       return  collectionManager.info(myCollection);
    }
    @Override
    public String getDescription() {
        return "Info";
    }

}
