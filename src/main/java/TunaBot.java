import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class TunaBot {
    static String LINE = "------------------------------";
    static Scanner s = new Scanner(System.in);
    static ArrayList<Task> tasks = new ArrayList<>();
    static boolean toExit = false;
    public static void main(String[] args) {
        Path filePath = Paths.get("data");
        File saveLocation = filePath.toFile();
        if (!saveLocation.exists()) {
            try {
                saveLocation.mkdir();
            } catch (SecurityException e) {
                System.out.println("BLUB! Problem with save location!!");
            }
        }
        Path savePath = Paths.get("data", "save.txt");
        File saveFile = savePath.toFile();
        if (!saveFile.exists()) {
            try {
                saveFile.createNewFile();
            } catch (IOException e) {
                System.out.println("BLUB! Problem creating save file!");
            }
        }
        loadSave(saveFile);
        System.out.println(LINE);
        System.out.println("    Hello! I'm TunaBot\n" +
                "    What can I do for you?");
        System.out.println(LINE);
        while (!toExit) {
            String input = s.nextLine();
            System.out.println(LINE);
            try {
                parse(input);
            } catch (InputException e) {
                System.out.println(e.getMessage());
            }
            System.out.println(LINE);
        }
        save(saveFile);
    }

    private static void save(File saveFile) {
        try {
            FileWriter writer = new FileWriter(saveFile);
            for (Task task : tasks) {
                writer.write(task.saveFormat()+ "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("BLUB! Couldn't save tasks!");
        }
    }

    private static void loadSave(File saveFile) {
        try {
            Scanner saveReader = new Scanner(saveFile);
            while (saveReader.hasNextLine()) {
                String [] taskInfo = saveReader.nextLine().split(";");
                if (taskInfo[0].equals("T")) {
                    tasks.add(new Task(taskInfo[1], taskInfo[2]));
                } else if (taskInfo[0].equals("D")) {
                    tasks.add(new Deadline(taskInfo[1], taskInfo[2], taskInfo[3]));
                } else if (taskInfo[0].equals("E")) {
                    tasks.add(new Event(taskInfo[1], taskInfo[2], taskInfo[3], taskInfo[4]));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("BLUB! Couldn't find save file!");
        }
    }

    public static void parse(String input) throws InputException {
        String[] command = input.split(" ", 2);
        switch (command[0]) {
            case "list":
                System.out.println("BLUB! There are " + tasks.size() + " task(s)!");
                for (int i = 1; i <= tasks.size(); i++) {
                    System.out.println("    " + i + ". " + tasks.get(i - 1));
                }
                break;
            case "bye":
                toExit = true;
                System.out.println("    Bye! Blub blub!");
                break;
            case "mark":
                if (command.length < 2) {
                    throw new InputException("BLUB! No task chosen!");
                }
                try {
                    int index = Integer.parseInt(command[1]);
                    tasks.get(index - 1).markDone();
                    System.out.println("    Blub! i have marked this as done!");
                    System.out.println(tasks.get(index - 1));
                } catch (IllegalArgumentException e) {
                    throw new InputException("BLUB! Index chosen isn't a number!");
                } catch (IndexOutOfBoundsException e) {
                    throw new InputException("BLUB! Task chosen isn't on the list!");
                }
                break;
            case "unmark":
                if (command.length < 2) {
                    throw new InputException("BLUB! No task chosen!");
                }
                try {
                    int index = Integer.parseInt(command[1]);
                    tasks.get(index - 1).unmarkDone();
                    System.out.println("    Blub! i have marked this as not done!");
                    System.out.println(tasks.get(index - 1));
                } catch (IllegalArgumentException e) {
                    throw new InputException("BLUB! Index chosen isn't a number!");
                } catch (IndexOutOfBoundsException e) {
                    throw new InputException("BLUB! Task chosen isn't on the list!");
                }
                break;
            case "delete":
                if (command.length < 2) {
                    throw new InputException("BLUB! No task chosen!");
                }
                try {
                    int index = Integer.parseInt(command[1]);
                    System.out.println("    Blub! i have deleted this task!");
                    System.out.println(tasks.get(index - 1));
                    tasks.remove(index - 1);
                    System.out.println("    Blub! You have " + tasks.size() + " tasks now!");
                } catch (IllegalArgumentException e) {
                    throw new InputException("BLUB! Index chosen isn't a number!");
                } catch (IndexOutOfBoundsException e) {
                    throw new InputException("BLUB! Task chosen isn't on the list!");
                }
                break;
            case "todo":
                if (command.length < 2) {
                    throw new InputException("BLUB! Task needs a name!");
                } else {
                    Task newTask = new Task(command[1]);
                    tasks.add(newTask);
                    System.out.println("    Blub! added: \n" + newTask);
                    System.out.println("    Blub! You have " + tasks.size() + " tasks now!");
                }
                break;
            case "event":
                if(command.length < 2) {
                    throw new InputException("BLUB! Event needs a name, " +
                            "a start time and end time!");
                } else {
                    String[] details = command[1].split("/from |/to ", 3);
                    if (details.length < 3) {
                        throw new InputException("BLUB! Event is missing info!" +
                                " Please check input. BLUB!");
                    }
                    Event newEvent = new Event(details[0], details[1], details[2]);
                    tasks.add(newEvent);
                    System.out.println("    Blub! added: \n" + newEvent);
                    System.out.println("    Blub! You have " + tasks.size() + " tasks now!");
                }
                break;
            case "deadline":
                if(command.length < 2) {
                    throw new InputException("BLUB! Deadline needs a name and due date!");
                } else {
                    String details[] = command[1].split("/by ", 2);
                    if(details.length < 2) {
                        throw new InputException("BLUB! Deadline is missing info!" +
                                " PLease check input. BLUB!");
                    }
                    Deadline newDeadline = new Deadline(details[0], details[1]);
                    tasks.add(newDeadline);
                    System.out.println("    Blub! added: \n" + newDeadline);
                    System.out.println("    Blub! You have " + tasks.size() + " tasks now!");
                }
                break;
            default:
                System.out.println("BLUB OH! INVALID COMMAND!");
        }
    }
}
