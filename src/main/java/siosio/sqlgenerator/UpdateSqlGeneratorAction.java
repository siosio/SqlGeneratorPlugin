package siosio.sqlgenerator;

public class UpdateSqlGeneratorAction extends SqlGeneratorSupport {

    public UpdateSqlGeneratorAction() {
        super("UPDATE SQL");
    }

    @Override
    String getStatementType() {
        return "UPDATE";
    }

    @Override
    String getSqlTemplate() {
        return "UPDATE" + Util.LF
                + "    $TABLE_NAME$" + Util.LF
                + "SET" + Util.LF
                + "$SET_CLAUSE$" + Util.LF
                + "$WHERE_CLAUSE$" + Util.LF;
    }
}

