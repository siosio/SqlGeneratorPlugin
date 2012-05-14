package siosio.sqlgenerator;

import java.util.List;

import com.intellij.persistence.database.psi.DbColumnElement;

public class SelectSqlGeneratorAction extends SqlGeneratorSupport {

    public SelectSqlGeneratorAction() {
        super("SELECT SQL");
    }

    private static final SqlGenerator GENERATOR = new SelectSqlGenerator();

    @Override
    SqlGenerator createSqlGenerator() {
        return GENERATOR;
    }

    @Override
    String getStatementType() {
        return "SELECT";
    }

    public static class SelectSqlGenerator implements SqlGenerator {

        private static final String SQL_TEMPLATE =
                "SELECT" + Util.LF
                + "$COLUMN_LIST$" + Util.LF
                + "FROM" + Util.LF
                + "    $TABLE_NAME$" + Util.LF
                + "$WHERE_LIST$" + Util.LF;

        @Override
        public String generate(TableInfo tableInfo) {
            List<DbColumnElement> columns = tableInfo.getNonPrimaryColumns();
            StringBuilder columnList = new StringBuilder();
            for (int i = 0; i < columns.size(); i++) {
                DbColumnElement column = columns.get(i);
                if (i != 0) {
                    columnList.append(",");
                }
                columnList.append("    ").append(column.getName()).append(Util.LF);
            }
            return SQL_TEMPLATE.replace("$TABLE_NAME$", tableInfo.getTableName())
                    .replace("$COLUMN_LIST$", columnList)
                    .replace("$WHERE_LIST$", Util.makeWhereClause(tableInfo.getPrimaryKeys()));
        }
    }
}
