package siosio.sqlgenerator;

import java.awt.datatransfer.StringSelection;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.persistence.database.TableType;
import com.intellij.persistence.database.psi.DbTableElement;
import com.intellij.persistence.database.view.DatabaseView;
import org.jetbrains.annotations.Nullable;

public abstract class SqlGeneratorSupport extends AnAction {

    protected static final String LF = System.getProperty("line.separator");

    public SqlGeneratorSupport(@Nullable String text) {
        super(text);
    }

    @Override
    public void actionPerformed(AnActionEvent event) {
        DatabaseView view = DatabaseView.DATABASE_VIEW_KEY.getData(event.getDataContext());
        if (view == null) {
            return;
        }
        Object[] tables = view.getTreeBuilder().getSelectedElements().toArray();

        StringBuilder sql = new StringBuilder();
        for (Object table : tables) {
            if (!(table instanceof DbTableElement) || (((DbTableElement) table).getTableType() != TableType.TABLE
                                                               && ((DbTableElement) table).getTableType()
                    != TableType.VIEW)) {
                continue;
            }
            TableInfo tableInfo = new TableInfo((DbTableElement) table);
            sql.append(createHeader(getStatementType(), tableInfo));
            sql.append(createSqlGenerator().generate(tableInfo));
            sql.append(LF);
        }
        CopyPasteManager.getInstance().setContents(new StringSelection(sql.toString()));
    }

    /**
     * SQL文のヘッダーコメントを生成する。
     * <p/>
     * 生成するヘッダーコメントは、以下の形となる。
     * <pre>
     * -------------------------------------
     * -- ステートメントの種類:テーブル名
     * -------------------------------------
     * </pre>
     *
     * @param statementType ステートメントの種類
     * @param tableInfo テーブル名
     * @return ヘッダコメント
     */
    protected String createHeader(String statementType, TableInfo tableInfo) {
        StringBuilder builder = new StringBuilder();
        builder.append("--------------------------------------------------------------------------------").append(LF);
        builder.append("-- ").append(statementType).append(":").append(tableInfo.getTableName()).append(LF);
        builder.append("--------------------------------------------------------------------------------").append(LF);
        return builder.toString();
    }

    /**
     * SQLを生成する{@link SqlGenerator}の実装クラスを生せうする。
     *
     * @return {@link SqlGenerator}
     */
    abstract SqlGenerator createSqlGenerator();

    abstract String getStatementType();
}

