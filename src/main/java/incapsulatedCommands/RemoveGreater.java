package incapsulatedCommands;

import сollectionData.StudyGroup;
import сollectionData.MyCollection;
import utils.CollectionManager;
import utils.Command;

import java.io.Serializable;

public class RemoveGreater implements Command, Serializable {
    private StudyGroup studyGroup;
    private CollectionManager collectionManager;
    private static final long serialVersionUID =91111133333223L;

    public RemoveGreater( CollectionManager collectionManager, StudyGroup studyGroup) {
        this.collectionManager = collectionManager;
        this.studyGroup = studyGroup;
    }


    public String execute(MyCollection myCollection) {
        return collectionManager.removeGreater(myCollection,studyGroup);
    }
    @Override
    public String getDescription() {
        return "RemoveGreater";
    }
}
