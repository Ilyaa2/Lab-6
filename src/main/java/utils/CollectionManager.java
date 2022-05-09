package utils;

import сollectionData.Person;
import сollectionData.StudyGroup;
import exceptions.*;
import сollectionData.MyCollection;

import java.io.*;
import java.nio.file.Files;
import java.time.DateTimeException;
import java.util.*;
import java.util.stream.Collectors;

//ПРОВЕРИТЬ СКОЛЬКО СТРОК В ФАЙЛЕ И ЕСЛИ ЧТО ОТКАЗЫВАТЬСЯ ВЫПОЛНЯТЬ, ПРОВЕРИТЬ ПРЕДЕЛ - НАДО ДЛЯ 7 ЛАБЫ
public class CollectionManager implements Serializable {
    private static final long serialVersionUID = 100101010101010L;

    public CollectionManager() {
    }

    public String add(MyCollection myCollection, StudyGroup studyGroup) {
        if (myCollection.length()>150){
            return "Sorry, there's a lot of elements here, can't add more";
        }
        myCollection.setNewElement(studyGroup);
        return "The element has been added:\n" + studyGroup.toString();
    }

    public String clear(MyCollection myCollection) {
        myCollection.clear();
        return "The collection has been removed";
    }


    public String countLessThanGroupAdmin(MyCollection myCollection, Person groupAdmin) {
        /*
        int count = 0;
        for (StudyGroup studyGroup : myCollection.getVector()) {
            if (studyGroup.getGroupAdmin() == null || studyGroup.getGroupAdmin().compareTo(groupAdmin) == -1) {
                count += 1;
            }
        }
        return Integer.valueOf(count).toString();
         */
        return Long.toString(myCollection.getVector().stream().filter(x -> x.getGroupAdmin() == null || x.getGroupAdmin().compareTo(groupAdmin) < 0).count());
    }


    public String filterContainsName(MyCollection myCollection, String name) {
        /*
        boolean flag = false;
        String Return = "";
        for (StudyGroup studyGroup : myCollection.getVector()) {
            if (studyGroup.getName().equals(name)) {
                Return = studyGroup.toString();
                flag = true;
            }
            if ((studyGroup.getGroupAdmin() == null && name == null) ||
                    (studyGroup.getGroupAdmin() != null && name.equals(studyGroup.getGroupAdmin().getName()))) {
                Return = studyGroup.getGroupAdmin().toString();
                flag = true;
            }
        }
        if (!flag) {
            Return = "There is not such element";
        }
         */
        String result = myCollection.getVector()
                .stream()
                .filter(x -> x.getName().equals(name) || (x.getGroupAdmin() != null && x.getGroupAdmin().getName().equals(name)) )
                .map(StudyGroup::toString)
                .collect(Collectors.joining());
        return result.isEmpty() ? "There is not such element" : result;
    }

    public String info(MyCollection myCollection) {
        return myCollection.getType() + " " + myCollection.getTimeOfCreation() + " " + myCollection.length();
    }

    public String removeAnyByGroupAdmin(MyCollection myCollection, Person groupAdmin) {

        boolean flag = false;
        String s = "";
        for (int i = 0; i < myCollection.length(); i++) {
            StudyGroup studyGroup = myCollection.getVector().get(i);
            if ((studyGroup.getGroupAdmin() == null && groupAdmin == null) ||
                    (studyGroup.getGroupAdmin() != null && studyGroup.getGroupAdmin().equals(groupAdmin))) {
                myCollection.removeElementAt(i);
                flag = true;
                s = "This element has been removed:\n" + groupAdmin;
            }
        }
        if (!flag) {
            s = "There is not such element";
        }
        return s;

//        if (myCollection.length() == 0) return "There is not such element";
        /*
        myCollection.getVector()
                .stream()
                .filter(x -> ((x.getGroupAdmin() != null && x.getGroupAdmin().equals(groupAdmin)) || (x.getGroupAdmin() == null && groupAdmin== null) ))
                .forEach(myCollection::removeElement);

        return null;

         */
    }

    public String removeByID(MyCollection myCollection, int index) {
        if (myCollection.length() == 0) return "There is not such element";
        Optional<StudyGroup> optional = myCollection.getVector().stream().filter(x -> x.getId() == index).findFirst();
        String response;
        if (optional.isPresent()) {
            myCollection.removeElement(optional.get());
            response = "This element has been removed:\n" + optional.get();
        } else {
            response = "There is not such element";
        }
        return response;
        /*
        boolean flag = false;
        String s = "";
        for (int i = 0; i < myCollection.length(); i++) {
            if (myCollection.getVector().get(i).getId().equals(index)) {
                myCollection.removeElementAt(i);
                flag = true;
                s = "This element has been removed:\n" + myCollection.getVector().get(i).toString();
            }
        }
        if (!flag) s = "There is not such element";
        return s;

         */
    }

    public String removeGreater(MyCollection myCollection, StudyGroup element) {
        if (myCollection.length() == 0) return "There is not such element";
        String s = "This element or elements have been removed:\n";
        for (int i = 0; i < myCollection.length(); i++) {
            if (myCollection.getVector().get(i) ==null) continue;
            if (1 == myCollection.getVector().get(i).compareTo(element)) {
                myCollection.removeElementAt(i);
                s += myCollection.getVector().get(i).toString() + "\n";
            }
        }
        return s;

    }

    public String removeLast(MyCollection myCollection) {
        String s;
        if (myCollection.length() != 0) {
            s = "This element has been removed:\n" + myCollection.getVector().get(myCollection.length()-1).toString();
            myCollection.removeElementAt(myCollection.length()-1);
            return s;
        }

        return "There is nothing to delete";
    }

    public String reorder(MyCollection myCollection) {
        if (myCollection.length() == 0) return "There is nothing to reorder";
        Vector<StudyGroup> tmp = new Vector<>(myCollection.getVector().size());
        for (int i = myCollection.getVector().size() - 1; i >= 0; i--) {
            tmp.add(myCollection.getVector().get(i));
        }
        for (int i = 0; i < myCollection.getVector().size(); i++) {
            myCollection.getVector().set(i, tmp.get(i));
        }
        return this.show(myCollection);
    }


    public String show(MyCollection myCollection) {
        Collections.sort(myCollection.getVector());
        return myCollection.print();
    }

    public String updateID(MyCollection myCollection, Integer id, StudyGroup studyGroup) {
        boolean flag = false;
        String s = "";
        studyGroup.setId(id);
        for (int i = 0; i < myCollection.length(); i++) {
            if (myCollection.getVector().get(i).getId().equals(id)) {
                myCollection.getVector().set(i, studyGroup);
                flag = true;
                s = "This element has been removed:\n" + myCollection.getVector().get(i).toString();
            }
        }
        if (!flag) s = "There is not this id in collection, so nothing has changed";
        return s;
    }

    public String executeScript(String path, ArrayList<Command> commands) {
        Command command = null;
        String s="";
        String EnteredCommand = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            File file = new File(path);
            if (!(Files.exists(file.toPath()) && file.canRead() && file.isFile())) throw new IOException();
            Switch sw = new Switch(new FileCollector(reader), this);

            while ((EnteredCommand = reader.readLine()) != null) {
                command = sw.interpret(EnteredCommand);
                //s += command.execute(myCollection);
                commands.add(command);
                //if (myCollection.getExitFlag()) return "Too many elements or commands, cannot execute";
            }
            s = "Script was validated successfully";
        } catch (FileNotFoundException e) {
            s = "Can not find the file";
        } catch (IOException e) {
            s = "Can not read the file";
        } catch (NumberFormatException | InputMismatchException e) {
            s = "Parameter is incorrect";
        } catch (IncorrectValueException e) {
            s = e.getMessage();
        } catch (NullArgumentException e) {
            s = "Argument cannot be null";
        } catch (UnderZeroValueException e) {
            s = "Parameter cannot be under zero";
        } catch (IllegalArgumentException e) {
            s = "Parameter must match with allowed values in enum";
        } catch (DateTimeException e) {
            s = "Something happened wrong, you probably entered incorrect parameter in date or time";
        } catch (MaxValueException e) {
            s = "Too large number";
        } catch (AttemptOfRecursionException e) {
            s = "Recursive execution of the script is not allowed";
        } catch (UnknownCommandException e) {
            s = "\""+EnteredCommand+"\" is unknown command, so that's why script wasn't completed fully";
        } catch (Exception e) {
            s = "Too many elements or commands, cannot execute";
        }

        return s;
    }

    public String help() {
        return "help : вывести справку по доступным командам\n" +
                "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "add {element} : добавить новый элемент в коллекцию\n" +
                "update id {element} : обновить значение элемента коллекции, id которого равен заданному\n" +
                "remove_by_id id : удалить элемент из коллекции по его id\n" +
                "clear : очистить коллекцию\n" +
                "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit : завершить программу (с сохранением в файл)\n" +
                "remove_last : удалить последний элемент из коллекции\n" +
                "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\n" +
                "reorder : отсортировать коллекцию в порядке, обратном нынешнему\n" +
                "remove_any_by_group_admin groupAdmin : удалить из коллекции один элемент, значение поля groupAdmin которого эквивалентно заданному\n" +
                "count_less_than_group_admin groupAdmin : вывести количество элементов, значение поля groupAdmin которых меньше заданного\n" +
                "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку";
    }

    public void save(MyCollection myCollection) {
        Saver save = new Saver(myCollection);
        //save.execute(System.getenv().get("LAB5"));
        save.execute("src/MyData.tld");
    }

    public String exit(MyCollection myCollection) {
        save(myCollection);
        return "The collection has been saved and you were disconnected from server";
    }



}
