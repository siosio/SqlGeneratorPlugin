package siosio.sqlgenerator;

import java.util.List;

import com.intellij.persistence.database.psi.DbColumnElement;
import com.intellij.persistence.database.psi.DbElement;

public class InsertSqlGeneratorAction extends SqlGeneratorSupport {

    private static final SqlGenerator GENERATOR = new InsertSqlGenerator();

    public InsertSqlGeneratorAction() {
        super("INSERT SQL");
    }

    @Override
    SqlGenerator createSqlGenerator() {
        return GENERATOR;
    }

    @Override
    String getStatementType() {
        return "INSERT";
    }


    public static class InsertSqlGenerator implements SqlGenerator {

        private static final String SQL_TEMPLATE =
                "INSERT INTO\n" + "    $TABLE_NAME$\n" + "    (\n" + "$COLUMN_LIST$\n" + "    )\n" + "VALUES\n"
                        + "    (\n" + "$VALUE_LIST$\n" + "    )\n";

        @Override
        public String generate(TableInfo tableInfo) {
            List<? extends DbColumnElement> columns = tableInfo.getColumns();
            StringBuilder columnList = new StringBuilder();
            StringBuilder valueList = new StringBuilder();
            for (int i = 0, size = columns.size(); i < size; i++) {
                DbElement column = columns.get(i);
                if (i != 0) {
                    columnList.append(",").append(Util.LF);
                    valueList.append(",").append(Util.LF);
                }
                valueList.append("    ?");
                columnList.append("    ").append(column.getName());
            }
            return SQL_TEMPLATE.replace("$TABLE_NAME$", tableInfo.getTableName()).replace("$COLUMN_LIST$",
                    columnList.toString()).replace("$VALUE_LIST$", valueList.toString());
        }
    }
}

