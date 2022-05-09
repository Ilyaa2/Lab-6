package utils;

import сollectionData.MyCollection;

public interface Command {
    String execute(MyCollection myCollection);
    String getDescription();
}


