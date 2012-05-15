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
            if (!(table instanceof DbTableElement)
                    || (((DbTableElement) table).getTableType() != TableType.TABLE
                    && ((DbTableElement) table).getTableType() != TableType.VIEW)) {
                continue;
            }
            TableInfo tableInfo = new TableInfo((DbTableElement) table);
            String sqlTemplate = getSqlTemplate();
            // table name
            sqlTemplate = sqlTemplate.replaceAll("\\$TABLE_NAME\\$", tableInfo.getTableName());

            // column list
            SqlGenerator generator = createSqlGenerator(tableInfo);
            sqlTemplate = sqlTemplate.replaceAll("\\$COLUMN_LIST\\$", generator.getColumnList());

            // where clause
            sqlTemplate = sqlTemplate.replaceAll("\\$WHERE_CLAUSE\\$", generator.getWhereClause());

            // insert values
            sqlTemplate = sqlTemplate.replaceAll("\\$INSERT_VALUES\\$", generator.getInsertValues());

            // set clause
            sqlTemplate = sqlTemplate.replaceAll("\\$SET_CLAUSE\\$", generator.getSetClause());

            sql.append(createHeader(getStatementType(), tableInfo));
            sql.append(sqlTemplate);
            sql.append(Util.LF);
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
        builder.append("--------------------------------------------------------------------------------").append(
                Util.LF);
        builder.append("-- ").append(statementType).append(":").append(tableInfo.getTableName()).append(Util.LF);
        builder.append("--------------------------------------------------------------------------------").append(
                Util.LF);
        return builder.toString();
    }

    /**
     * SQLを生成する{@link SqlGenerator}の実装クラスを生せうする。
     *
     * @param tableInfo テーブル情報
     * @return {@link SqlGenerator}
     */
    protected SqlGenerator createSqlGenerator(TableInfo tableInfo) {
        return new SqlGenerator(tableInfo);
    }

    /**
     * SQL文のタイプを表す文字列を返す。
     * <p/>
     * ここで返されたSQL文のタイプは、各SQL文のヘッダコメントに使用する。
     *
     * @return SQLタイプ
     */
    abstract String getStatementType();

    abstract String getSqlTemplate();
}

