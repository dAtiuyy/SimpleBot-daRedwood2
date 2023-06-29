package daTaskScript.Tasks;

import simple.hooks.scripts.task.Task;
import simple.hooks.wrappers.SimpleObject;
import simple.robot.api.ClientContext;

public class daBankTask extends Task {
    public daBankTask(ClientContext ctx) {
        super(ctx);
    }

    @Override
    public boolean condition() {
        return ctx.inventory.inventoryFull();
    }

    @Override
    public void run() {
        SimpleObject bank = ctx.objects.populate().filter(25937).nearest().next();
        if (bank == null) {
            return;
        }
        if (!ctx.bank.depositBoxOpen()) {
            bank.click(0);
            ctx.sleepCondition(() -> ctx.bank.depositBoxOpen(), 2500);
        } else if (ctx.inventory.inventoryFull()) {
            ctx.bank.depositInventory();
            ctx.sleepCondition(() -> !ctx.inventory.inventoryFull(), 2500);
        }
        if (ctx.bank.depositBoxOpen() && !ctx.inventory.inventoryFull()) {
            ctx.bank.closeBank();
            ctx.sleepCondition(() -> !ctx.bank.depositBoxOpen(), 2500);
        }
    }

    @Override
    public String status() {
        return "Banking logs";
    }

}