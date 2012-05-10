package siosio.sqlgenerator;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * SQLの自動生成アクション。
 *
 * @author siosio
 * @version 1.0
 */
public class SqlGeneratorAction extends ActionGroup {


    public SqlGeneratorAction() {
        super("Generate SQL", true);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        super.actionPerformed(e);
    }

    @NotNull
    public AnAction[] getChildren(@Nullable AnActionEvent event) {
        return new AnAction[]{new InsertAction(), new UpdateAction()};
    }
}

