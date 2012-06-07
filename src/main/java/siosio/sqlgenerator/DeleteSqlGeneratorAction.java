package siosio.sqlgenerator;

import org.jetbrains.annotations.Nullable;

import static siosio.sqlgenerator.Util.LF;
import static siosio.sqlgenerator.Util.makeNamedWhereClause;

public class DeleteSqlGeneratorAction extends SqlGeneratorSupport {

    public DeleteSqlGeneratorAction() {
        super("DELETE SQL");
    }

    public DeleteSqlGeneratorAction(@Nullable String text) {
        super(text);
    }

    @Override
    String getStatementType() {
        return "DELETE";
    }

    @Override
    String getSqlTemplate() {
        return "DELETE FROM" + LF
                + "    $TABLE_NAME$" + LF
                + "$WHERE_CLAUSE$" + LF;
    }

    public static class NamedParameterSqlGeneratorAction extends DeleteSqlGeneratorAction {

        public NamedParameterSqlGeneratorAction() {
            super("DELETE SQL(named parameter)");
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


