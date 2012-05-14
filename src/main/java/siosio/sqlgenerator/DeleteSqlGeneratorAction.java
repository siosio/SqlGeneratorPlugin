package siosio.sqlgenerator;

import java.util.List;

import com.intellij.persistence.database.psi.DbColumnElement;

public class DeleteSqlGeneratorAction extends SqlGeneratorSupport {

    private static final SqlGenerator GENERATOR = new DeleteSqlGenerator();

    public DeleteSqlGeneratorAction() {
        super("DELETE SQL");
    }

    @Override
    SqlGenerator createSqlGenerator() {
        return GENERATOR;
    }

    @Override
    String getStatementType() {
        return "DELETE";
    }

    private static class DeleteSqlGenerator implements SqlGenerator {

        private static final String SQL_TEMPLATE = "DELETE FROM\n" + "    $TABLE_NAME$\n" + "$WHERE_LIST$\n";

        @Override
        public String generate(TableInfo tableInfo) {
            List<DbColumnElement> keys = tableInfo.getPrimaryKeys();
            return SQL_TEMPLATE.replace("$TABLE_NAME$", tableInfo.getTableName()).replace("$WHERE_LIST$",
                    Util.makeWhereClause(keys));
        }
    }
}

