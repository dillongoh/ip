package tunabot;

import tunabot.exceptions.InputException;
import tunabot.task.Deadline;
import tunabot.task.Event;
import tunabot.task.Task;

/**
 * Class handling the parsing of commands
 */
public class Parser {
    /**
     * Returns boolean to determine whether to continue receiving input
     * If input "bye" is received return true else return false
     * @param input command
     * @param tasks TaskList
     * @return true If input is "bye" else false
     * @throws InputException If the command does not fit required format
     */
    public static String parse(String input, TaskList tasks) throws InputException {
        String[] command = input.split(" ", 2);
        switch (command[0]) {
        case "list":
            return Ui.list(tasks);
        case "bye":
            return "    Bye! Blub blub!";
        case "mark":
            if (command.length < 2) {
                throw new InputException("BLUB! No task chosen!");
            }
            try {
                int index = Integer.parseInt(command[1]);
                tasks.get(index - 1).markDone();
                return Ui.mark(tasks.get(index - 1));
            } catch (IllegalArgumentException e) {
                throw new InputException("BLUB! Index chosen isn't a number!");
            } catch (IndexOutOfBoundsException e) {
                throw new InputException("BLUB! duke.task.Task chosen isn't on the list!");
            }
        case "unmark":
            if (command.length < 2) {
                throw new InputException("BLUB! No task chosen!");
            }
            try {
                int index = Integer.parseInt(command[1]);
                tasks.get(index - 1).unmarkDone();
                return Ui.unmark(tasks.get(index - 1));
            } catch (IllegalArgumentException e) {
                throw new InputException("BLUB! Index chosen isn't a number!");
            } catch (IndexOutOfBoundsException e) {
                throw new InputException("BLUB! duke.task.Task chosen isn't on the list!");
            }
        case "delete":
            if (command.length < 2) {
                throw new InputException("BLUB! No task chosen!");
            }
            try {
                int index = Integer.parseInt(command[1]);

                String delete = Ui.delete(tasks.get((index - 1)), tasks.size() - 1);
                tasks.remove(index - 1);
                return delete;
            } catch (IllegalArgumentException e) {
                throw new InputException("BLUB! Index chosen isn't a number!");
            } catch (IndexOutOfBoundsException e) {
                throw new InputException("BLUB! duke.task.Task chosen isn't on the list!");
            }
        case "todo":
            if (command.length < 2) {
                throw new InputException("BLUB! duke.task.Task needs a name!");
            } else {
                Task newTask = new Task(command[1]);
                tasks.add(newTask);
                return Ui.add(newTask, tasks.size());
            }
        case "event":
            if (command.length < 2) {
                throw new InputException("BLUB! duke.task.Event needs a name, "
                    + "a start time and end time!");
            } else {
                String[] details = command[1].split("/from |/to ", 3);
                if (details.length < 3) {
                    throw new InputException("BLUB! duke.task.Event is missing info!"
                        + " Please check input. BLUB!");
                }
                Event newEvent = new Event(details[0], details[1], details[2]);
                tasks.add(newEvent);
                return Ui.add(newEvent, tasks.size());
            }
        case "deadline":
            if (command.length < 2) {
                throw new InputException("BLUB! duke.task.Deadline needs a name and due date!");
            } else {
                String[] details = command[1].split("/by ", 2);
                if (details.length < 2) {
                    throw new InputException("BLUB! duke.task.Deadline is missing info!"
                        + " PLease check input. BLUB!");
                }
                Deadline newDeadline = new Deadline(details[0], details[1]);
                tasks.add(newDeadline);
                return Ui.add(newDeadline, tasks.size());
            }
        case "find":
            if (command.length < 2) {
                throw new InputException("BLUB! find needs a search target!");
            } else {
                String target = command[1];
                TaskList targetList = tasks.find(target);
                if (targetList.size() == 0) {
                    return "BLUB! No matching tasks!";
                } else {
                    String found = "BLUB! Here are your matching task(s)!\n";
                    found += Ui.list(targetList);
                    return found;
                }
            }
        default:
            return "BLUB OH! INVALID COMMAND!";
        }
    }
}
