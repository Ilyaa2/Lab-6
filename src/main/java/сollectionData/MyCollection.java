package сollectionData;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Vector;


/**
 * нужно убрать поле type в xml и тут, пусть в конструкторе автоматически генерится
 * time of creation можно в метод запихнуть в локальное поле.
 * крч нужно избавится от timeOfCreation
 * МОЖЕТ В КАЖДОМ ЦИКЛЕ ДОЛЖЕН БЫТЬ SIZE()-1
 */


public class MyCollection implements Serializable {
    private boolean exitFlag=false;
    private Vector<StudyGroup> vector;
    private LocalDateTime timeOfCreation;
    private String type;
    private static final long serialVersionUID =95250943863L;

    public MyCollection(Vector<StudyGroup> v){
        vector = v;
        timeOfCreation = LocalDateTime.now();
        type = v.getClass().getName() + "<" + v.get(0).getClass().getName() + ">";
    }
    public MyCollection(Vector<StudyGroup> v,LocalDateTime timeOfCreation){
        vector = v;
        this.timeOfCreation = timeOfCreation;
        type = v.getClass().getName() + "<" + v.get(0).getClass().getName() + ">";
    }

    public MyCollection(){
        timeOfCreation = LocalDateTime.now();
        type = "java.util.Vector<ClassesOfCollection.StudyGroup>";
    }

    public LocalDateTime getTimeOfCreation(){
        return timeOfCreation;
    }

    public String getType(){
        return type;
    }

    public void setNewElement(StudyGroup studyGroup){
        vector.add(studyGroup);
    }

    public Vector<StudyGroup> getVector(){
        return vector;
    }


    public void clear(){
        vector.clear();
    }

    public void removeElementAt(int index){
        vector.removeElementAt(index);
    }
    public void setExitFlag(boolean f){
        exitFlag = f;
    }


    public void removeElement(StudyGroup studyGroup){
        vector.remove(studyGroup);
    }

    public int length(){
        return vector.size();
    }

    @Override
    public String toString() {
        return vector.toString()+"\n" ;
    }

    public String print(){
        String s="";
        for (StudyGroup studyGroup: this.getVector()){
            s += studyGroup.toString();
        }
        return s;
    }
}

