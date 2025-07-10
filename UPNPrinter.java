public class UPNPrinter {
    public static String toUpn(Expr e) {
        StringBuilder sb = new StringBuilder();
        walk(e, sb);
        return sb.toString().trim();
    }
    private static void walk(Expr e, StringBuilder sb) {
        if (e instanceof BinaryExpr b) {
            walk(b.left(), sb); sb.append(" ");
            walk(b.right(), sb); sb.append(" ");
            sb.append(b.op().name()).append(" ");
        } else if (e instanceof UnaryExpr u) {
            walk(u.expr(), sb); sb.append(u.op().name()).append(" ");
        } else if (e instanceof FunctionCallExpr f) {
            for (Expr a: f.args()) { walk(a, sb); sb.append(" "); }
            sb.append(f.name()).append(" ");
        } else if (e instanceof NumberExpr n) {
            sb.append(n.value()).append(" ");
        } else if (e instanceof VariableExpr v) {
            sb.append(v.name()).append(" ");
        }
    }
}
