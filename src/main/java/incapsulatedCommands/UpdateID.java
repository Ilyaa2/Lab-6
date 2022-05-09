package incapsulatedCommands;

import сollectionData.StudyGroup;
import сollectionData.MyCollection;
import utils.CollectionManager;
import utils.Command;

import java.io.Serializable;

public class UpdateID implements Command, Serializable {
    private CollectionManager collectionManager;
    private Integer id;
    private StudyGroup studyGroup;
    private static final long serialVersionUID =6000000066666L;

    public UpdateID(CollectionManager collectionManager, Integer id, StudyGroup studyGroup) {
        this.collectionManager = collectionManager;
        this.id = id;
        this.studyGroup = studyGroup;
    }

    public String execute(MyCollection myCollection) {
        return collectionManager.updateID(myCollection,id,studyGroup);
    }
    @Override
    public String getDescription() {
        return "UpdateID";
    }
}
