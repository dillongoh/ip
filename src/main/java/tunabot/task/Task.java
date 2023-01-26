package tunabot.task;

/**
 * Class to handle Tasks
 */
public class Task {
    final String name;
    boolean isDone;

    /**
     * Initializes new Task with given name
     * @param name
     */
    public Task(String name) {
        this.name = name;
        this.isDone = false;
    }

    /**
     * Initializes new Task with given name and status
     * @param name
     * @param isDone
     */
    public  Task(String name, String isDone) {
        this.name = name;
        this.isDone = isDone.equals("true");
    }

    public String getName() {
        return name;
    }
    public boolean getDone() {
        return isDone;
    }

    /**
     * Marks task as done
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks task as undone
     */
    public void unmarkDone() {
        this.isDone = false;
    }
    public String saveFormat() {
        return "T;" + this.name + ";" +this.isDone;
    }
    
    @Override
    public String toString() {
        String box;
        if (isDone) {
            box = "[X] ";
        } else {
            box = "[ ] ";
        }
        return"[T]" + box + this.getName();
    }
}
