package tunabot;

import tunabot.exceptions.InputException;
import tunabot.task.Deadline;
import tunabot.task.Event;
import tunabot.task.Task;

public class Parser {
    public static boolean parse(String input, TaskList tasks) throws InputException {
        String[] command = input.split(" ", 2);
        switch (command[0]) {
            case "list":
                System.out.println("BLUB! There are " + tasks.size() + " task(s)!");
                for (int i = 1; i <= tasks.size(); i++) {
                    System.out.println("    " + i + ". " + tasks.get(i - 1));
                }
                break;
            case "bye":
                System.out.println("    Bye! Blub blub!");
                return true;
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
                    throw new InputException("BLUB! duke.task.Task chosen isn't on the list!");
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
                    throw new InputException("BLUB! duke.task.Task chosen isn't on the list!");
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
                    throw new InputException("BLUB! duke.task.Task chosen isn't on the list!");
                }
                break;
            case "todo":
                if (command.length < 2) {
                    throw new InputException("BLUB! duke.task.Task needs a name!");
                } else {
                    Task newTask = new Task(command[1]);
                    tasks.add(newTask);
                    System.out.println("    Blub! added: \n" + newTask);
                    System.out.println("    Blub! You have " + tasks.size() + " tasks now!");
                }
                break;
            case "event":
                if(command.length < 2) {
                    throw new InputException("BLUB! duke.task.Event needs a name, " +
                            "a start time and end time!");
                } else {
                    String[] details = command[1].split("/from |/to ", 3);
                    if (details.length < 3) {
                        throw new InputException("BLUB! duke.task.Event is missing info!" +
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
                    throw new InputException("BLUB! duke.task.Deadline needs a name and due date!");
                } else {
                    String details[] = command[1].split("/by ", 2);
                    if(details.length < 2) {
                        throw new InputException("BLUB! duke.task.Deadline is missing info!" +
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
        return false;
    }
}