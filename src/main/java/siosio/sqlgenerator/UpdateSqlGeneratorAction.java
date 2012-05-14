package siosio.sqlgenerator;

import java.util.List;

import com.intellij.persistence.database.psi.DbColumnElement;

public class UpdateSqlGeneratorAction extends SqlGeneratorSupport {

    private static final SqlGenerator GENERATOR = new UpdateSqlGenerator();

    public UpdateSqlGeneratorAction() {
        super("UPDATE SQL");
    }

    @Override
    String getStatementType() {
        return "UPDATE";
    }

    @Override
    SqlGenerator createSqlGenerator() {
        return GENERATOR;
    }

    private static class UpdateSqlGenerator implements SqlGenerator {

        private static final String SQL_TEMPLATE =
                "UPDATE\n" + "    $TABLE_NAME$\n" + "SET\n" + "$SET_LIST$\n" + "$WHERE_LIST$\n";


        @Override
        public String generate(TableInfo tableInfo) {

            List<? extends DbColumnElement> columns = tableInfo.getNonPrimaryColumns();
            StringBuilder setList = new StringBuilder();
            for (int i = 0, size = columns.size(); i < size; i++) {
                DbColumnElement column = columns.get(i);
                if (i != 0) {
                    setList.append(",").append(Util.LF);
                }
                setList.append("    ").append(column.getName()).append(" = ?");
            }

            List<DbColumnElement> primaryKeys = tableInfo.getPrimaryKeys();
            return SQL_TEMPLATE.replace("$TABLE_NAME$", tableInfo.getTableName()).replace("$SET_LIST$", setList)
                    .replace("$WHERE_LIST$", Util.makeWhereClause(primaryKeys));
        }
    }
}

