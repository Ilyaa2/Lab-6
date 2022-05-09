package incapsulatedCommands;

import сollectionData.StudyGroup;
import сollectionData.MyCollection;
import utils.CollectionManager;
import utils.Command;

import java.io.Serializable;


public class Add implements Command, Serializable {
    private CollectionManager collectionManager;
    private StudyGroup studyGroup;
    private static final long serialVersionUID =952543863L;

    public Add(CollectionManager collectionManager, StudyGroup studyGroup) {
        this.collectionManager = collectionManager;
        this.studyGroup = studyGroup;
    }

    public String execute(MyCollection myCollection) {
       return collectionManager.add(myCollection,studyGroup);
    }

    @Override
    public String getDescription() {
        return "Add";
    }
}
