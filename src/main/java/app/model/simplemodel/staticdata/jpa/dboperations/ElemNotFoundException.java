package app.model.simplemodel.staticdata.jpa.dboperations;

import org.jetbrains.annotations.NotNull;

public class ElemNotFoundException extends Exception {

    private Object where;
    private Object what;

    public ElemNotFoundException(@NotNull String errorMessage) {
        super(errorMessage);
    }

    public ElemNotFoundException(Class where, Object what) {
        this(where.toString(), what.toString());
    }

    public ElemNotFoundException(@NotNull String where, @NotNull String what) {
        this.what = what;
        this.where = where;
    }

    public ElemNotFoundException(NumberFormatException e) {
        super(e);
        what = e.getMessage();
    }

    public String getErrorMessage() {
        return toString();
    }

    @Override
    public String toString() {
        return "ElemNotFoundException{" +
                "where=" + where +
                ", what=" + what +
                '}';
    }
}