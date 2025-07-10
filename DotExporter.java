// src/DotExporter.java
public class DotExporter {
    private int idCounter = 0;
    private StringBuilder sb = new StringBuilder("digraph G {\n");

    public String toDot(Expr root) {
        visit(root, nextId());
        sb.append("}\n");
        return sb.toString();
    }

    private int nextId() { return idCounter++; }

    private void visit(Expr e, int myId) {
        String label;
        if (e instanceof NumberExpr n)       label = Double.toString(n.value());
        else if (e instanceof VariableExpr v)label = v.name();
        else if (e instanceof BinaryExpr b)  label = b.op().name();
        else if (e instanceof UnaryExpr u)   label = u.op().name();
        else if (e instanceof FunctionCallExpr f) label = f.name();
        else label = "?";

        sb.append("  node").append(myId)
                .append(" [label=\"").append(label).append("\"];\n");

        if (e instanceof UnaryExpr u) {
            int cid = nextId(); visit(u.expr(), cid);
            sb.append("  node").append(myId).append(" -> node").append(cid).append(";\n");
        }
        else if (e instanceof BinaryExpr b) {
            int l = nextId(), r = nextId();
            visit(b.left(), l); visit(b.right(), r);
            sb.append("  node").append(myId).append(" -> node").append(l).append(";\n");
            sb.append("  node").append(myId).append(" -> node").append(r).append(";\n");
        }
        else if (e instanceof FunctionCallExpr f) {
            for (Expr arg : f.args()) {
                int cid = nextId();
                visit(arg, cid);
                sb.append("  node").append(myId).append(" -> node").append(cid).append(";\n");
            }
        }
        // NumberExpr und VariableExpr sind Blätter
    }
}
