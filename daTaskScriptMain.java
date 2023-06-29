package daTaskScript;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import daTaskScript.Tasks.daBankTask;
import daTaskScript.Tasks.daChopTask;
import simple.hooks.scripts.Category;
import simple.hooks.scripts.ScriptManifest;
import simple.hooks.scripts.task.TaskScript;
import simple.hooks.simplebot.ChatMessage;

@ScriptManifest(
        author = "alex",
        category = Category.WOODCUTTING,
        description = "<html>"
                + "<p>Basically daRedwood but task based</p>"
                + "<p><strong>Cuts redwood @ home in Battlescape</strong></p>"
                + "<ul>"
                + "<li>Start <strong>@ da big redwood at home with the deposit box on ur screen</strong>.</li>"
                + "<li><strong>Have an axe equipped and dont worry about bird nests or logs, it picks up bird nests and bank em along with logs</strong></li>"
                + "</ul>"
                + "</html>",
        discord = "",
        name = "daRedwood2",
        servers = {"Battlescape"},
        version = "3.0"
)

public class daTaskScriptMain extends TaskScript {
    private static final int[] BIRD_NEST = {5070, 5072, 5071};

    private List tasks = new ArrayList();

    @Override
    public void onExecute() {
        tasks.addAll(Arrays.asList(new daBankTask(ctx), new daChopTask(ctx)));// Adds our tasks to our {task} list for execution
        System.out.println("Started RWoodcutter!");
    }

    @Override
    public List tasks() {
        return tasks;// Tells our TaskScript these are the tasks we want executed
    }

    @Override
    public boolean prioritizeTasks() {
        return true;// Will prioritize tasks in order added in our {tasks} List
    }

    // This method is not needed as the TaskScript class will call it, itself
    @Override
    public void onProcess() {
        // Can add anything here before tasks have been ran
        super.onProcess();// Needed for the TaskScript to process the tasks
        //Can add anything here after tasks have been ran
        if (!ctx.groundItems.populate().filter(BIRD_NEST).isEmpty()) {
            ctx.groundItems.nearest().next().click("Take");
            ctx.sleep(2000);
            return;
        }
    }

    @Override
    public void onTerminate() {
    }

    @Override public void onChatMessage(ChatMessage m) {}

    @Override
    public void paint(Graphics g) {
    }

}