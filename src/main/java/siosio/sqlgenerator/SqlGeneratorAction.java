package siosio.sqlgenerator;

import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.persistence.database.TableType;
import com.intellij.persistence.database.psi.DbTableElement;
import com.intellij.persistence.database.view.DatabaseView;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SqlGeneratorAction extends ActionGroup {

    public SqlGeneratorAction() {
        super("Generate SQL", true);
    }

    @Override
    public void actionPerformed(AnActionEvent e) {
        super.actionPerformed(e);
    }

    @Override
    public void update(AnActionEvent e) {
        DatabaseView view = DatabaseView.DATABASE_VIEW_KEY.getData(e.getDataContext());
        if (view == null) {
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }
        Object[] tables = view.getTreeBuilder().getSelectedElements().toArray();
        boolean hasTable = false;
        for (Object table : tables) {
            if (table instanceof DbTableElement) {
                DbTableElement tableElement = (DbTableElement) table;
                if (tableElement.getTableType() == TableType.TABLE || tableElement.getTableType() == TableType.VIEW) {
                    hasTable = true;
                    break;
                }
            }
        }
        e.getPresentation().setEnabledAndVisible(hasTable);
        super.update(e);
    }

    @Override
    public boolean hideIfNoVisibleChildren() {
        return false;
    }

    @NotNull
    public AnAction[] getChildren(@Nullable AnActionEvent event) {
        return new AnAction[]{new InsertSqlGeneratorAction(), new UpdateSqlGeneratorAction()};
    }
}

