package siosio.sqlgenerator;

import org.jetbrains.annotations.Nullable;

import static siosio.sqlgenerator.Util.makeNamedWhereClause;

public class SelectSqlGeneratorAction extends SqlGeneratorSupport {

    public SelectSqlGeneratorAction() {
        super("SELECT SQL");
    }

    public SelectSqlGeneratorAction(@Nullable String text) {
        super(text);
    }

    @Override
    String getStatementType() {
        return "SELECT";
    }

    @Override
    String getSqlTemplate() {
        return  "SELECT" + Util.LF
                + "$COLUMN_LIST$" + Util.LF
                + "FROM" + Util.LF
                + "    $TABLE_NAME$" + Util.LF
                + "$WHERE_CLAUSE$" + Util.LF;
    }

    public static class NamedParameterSqlGeneratorAction extends SelectSqlGeneratorAction {

        public NamedParameterSqlGeneratorAction() {
            super("SELECT SQL(named parameter)");
        }

        @Override
        protected SqlGenerator createSqlGenerator(final TableInfo tableInfo) {
            return new SqlGenerator(tableInfo) {
                @Override
                protected String getWhereClause() {
                    return makeNamedWhereClause(tableInfo.getPrimaryKeys());
                }
            };
        }
    }

}

