import java.util.Hashtable;
import java.util.Stack;

public class Parser {

    private Hashtable<String, Hashtable<String, String>> parsingTable;

    public Parser(){
        parsingTable = new Hashtable<>();
        setTable();
    }

    protected void setTable(){
        //E in language
        Hashtable<String, String> Evalues = new Hashtable<>();
        Evalues.put("i", "TX");
        Evalues.put("(", "TX");
        parsingTable.put("E", Evalues);
        //X in language
        Hashtable<String, String> Xvalues = new Hashtable<>();
        Xvalues.put("+", "+E");
        Xvalues.put(")", "empty");
        Xvalues.put("$", "empty");
        parsingTable.put("X", Xvalues);
        //T in language
        Hashtable<String, String> Tvalues = new Hashtable<>();
        Tvalues.put("i", "iY");
        Tvalues.put("(", "(E)");
        parsingTable.put("T", Tvalues);
        //Y in laguage
        Hashtable<String, String> Yvalues = new Hashtable<>();
        Yvalues.put("*", "*T");
        Yvalues.put("+", "empty");
        Yvalues.put(")", "empty");
        Yvalues.put("$", "empty");
        parsingTable.put("Y", Yvalues);
    }

    protected String Parse(String input){
        try{
            Stack<String> parseStack = new Stack<>();
            parseStack.push("$");
            parseStack.push("E");
            for (int i = 0; i < input.length(); i++){
                String current = "" + input.charAt(i);
                if(current.equals("$")){
                    return "accepted";
                }else {
                    while (true) {
                        String stackElement = parseStack.pop();
                        if (stackElement.equals(current)) {
                            break;
                        } else {
                            Hashtable<String, String> options = parsingTable.get(stackElement);
                            String selected = options.get(current);
                            if(!selected.equals("empty")) {
                                for (int j = selected.length() - 1; j > -1; j--) {
                                    parseStack.push("" + selected.charAt(j));
                                }
                            }
                        }
                    }
                }
            }
            return "accepted";
        }catch (NullPointerException ex){ //if does not exist in current table entry reject input
            return "rejected";
        }

    }
}
