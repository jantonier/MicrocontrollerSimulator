import java.util.ArrayList;
import java.util.List;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;

public class KeywordStyledDocument extends DefaultStyledDocument  {
    private static final long serialVersionUID = 1L;
    private Style _defaultStyle;
    private Style _cwStyle;

    public KeywordStyledDocument(Style defaultStyle, Style cwStyle) {
        _defaultStyle =  defaultStyle;
        _cwStyle = cwStyle;
    }

    public void insertString (int offset, String str, AttributeSet a) throws BadLocationException {
        super.insertString(offset, str, a);
        refreshDocument();
    }

    public void remove (int offs, int len) throws BadLocationException {
        super.remove(offs, len);
        refreshDocument();
    }

    private synchronized void refreshDocument() throws BadLocationException {
        String text = getText(0, getLength());
        final List<HiliteWord> list = processWords(text);

        setCharacterAttributes(0, text.length(), _defaultStyle, true);
        for(HiliteWord word : list) {
            int p0 = word._position;
            setCharacterAttributes(p0, word._word.length(), _cwStyle, true);
        }
    }

    private static  List<HiliteWord> processWords(String content) {
        content += " ";
        List<HiliteWord> hiliteWords = new ArrayList<HiliteWord>();
        int lastWhitespacePosition = 0;
        String word = "";
        char[] data = content.toCharArray();

        for(int index=0; index < data.length; index++) {
            char ch = data[index];
            if(!(Character.isLetter(ch) || Character.isDigit(ch) || ch == '_')) {
                lastWhitespacePosition = index;
                if(word.length() > 0) {
                    if(isReservedWord(word)) {
                        hiliteWords.add(new HiliteWord(word,(lastWhitespacePosition - word.length())));
                    }
                    word="";
                }
            }
            else {
                word += ch;
            }
        }
        return hiliteWords;
    }

    private static final boolean isReservedWord(String word) {
        return(word.trim().equals("LOAD") ||
                word.trim().equals("LOADIM") ||
                word.trim().equals("POP") ||
                word.trim().equals("STORE") ||
                word.trim().equals("PUSH") ||
                word.trim().equals("LOADRIND") ||
                word.trim().equals("STORERIND") ||
                word.trim().equals("ADD") ||
                word.trim().equals("SUB") ||
                word.trim().equals("ADDIM") ||
                word.trim().equals("SUBIM") ||
                word.trim().equals("AND") ||
                word.trim().equals("OR") ||
                word.trim().equals("XOR") ||
                word.trim().equals("NOT") ||
                word.trim().equals("NEG") ||
                word.trim().equals("SHIFTR") ||
                word.trim().equals("SHIFTL") ||
                word.trim().equals("ROTAR") ||
                word.trim().equals("ROTAL") ||
                word.trim().equals("JMPRIND") ||
                word.trim().equals("JMPADDR") ||
                word.trim().equals("JCONDRIN") ||
                word.trim().equals("JCONDADDR") ||
                word.trim().equals("LOOP") ||
                word.trim().equals("GRT") ||
                word.trim().equals("GRTEQ") ||
                word.trim().equals("EQ") ||
                word.trim().equals("NEQ") ||
                word.trim().equals("NOP") ||
                word.trim().equals("CALL") ||
                word.trim().equals("RETURN"))
                ;
    }
}