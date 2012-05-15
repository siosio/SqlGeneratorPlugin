package siosio.sqlgenerator;

public class InsertSqlGeneratorAction extends SqlGeneratorSupport {

    public InsertSqlGeneratorAction() {
        super("INSERT SQL");
    }

    @Override
    String getStatementType() {
        return "INSERT";
    }

    @Override
    String getSqlTemplate() {
        return "INSERT INTO" + Util.LF
                + "    $TABLE_NAME$" + Util.LF
                + "    (" + Util.LF
                + "$COLUMN_LIST$" + Util.LF
                + "    )" + Util.LF
                + "VALUES" + Util.LF
                + "    (" + Util.LF
                + "$INSERT_VALUES$" + Util.LF
                + "    )" + Util.LF;
    }

}

