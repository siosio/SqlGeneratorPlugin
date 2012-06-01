package siosio.sqlgenerator;

import java.util.List;

import com.intellij.persistence.database.psi.DbColumnElement;
import org.jetbrains.annotations.Nullable;

import static siosio.sqlgenerator.Util.LF;
import static siosio.sqlgenerator.Util.convertCamelCase;

public class InsertSqlGeneratorAction extends SqlGeneratorSupport {

    public InsertSqlGeneratorAction() {
        super("INSERT SQL");
    }

    public InsertSqlGeneratorAction(@Nullable String text) {
        super(text);
    }

    @Override
    String getStatementType() {
        return "INSERT";
    }

    @Override
    String getSqlTemplate() {
        return "INSERT INTO" + LF
                + "    $TABLE_NAME$" + LF
                + "    (" + LF
                + "$COLUMN_LIST$" + LF
                + "    )" + LF
                + "VALUES" + LF
                + "    (" + LF
                + "$INSERT_VALUES$" + LF
                + "    )" + LF;
    }

    public static class NamedParameterSqlGeneratorAction extends InsertSqlGeneratorAction {

        public NamedParameterSqlGeneratorAction() {
            super("INSERT SQL(named parameter)");
        }

        @Override
        protected SqlGenerator createSqlGenerator(final TableInfo tableInfo) {
            return new SqlGenerator(tableInfo) {
                @Override
                protected String getInsertValues() {
                    StringBuilder values = new StringBuilder();
                    List<? extends DbColumnElement> columns = tableInfo.getColumns();
                    for (int i = 0; i < columns.size(); i++) {
                        DbColumnElement columnElement = columns.get(i);
                        if (i != 0) {
                            values.append(",").append(LF);
                        }
                        values.append("    :").append(convertCamelCase(columnElement.getName()));
                    }
                    return values.toString();
                }
            };
        }
    }
}

