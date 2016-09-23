package textprocessing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import views.SubstringSearchFrame;

public class RegexSearch {

  private final SubstringSearchFrame frame = new SubstringSearchFrame();

  public RegexSearch() throws IOException {
    frame.setTitle(getClass().getSimpleName());
    frame.setSize(600, 500);

    String target = "testing.txt";
    String content = new String(Files.readAllBytes(Paths.get(target)));
    frame.setTargetText(content);

    final String search_string = content;

    frame.addFindActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        frame.clearHighlights();
        String search = frame.getSearchText(); // here, a regex

        if (search.isEmpty()) {
          JOptionPane.showMessageDialog(frame, "search string cannot be empty");
          return;
        }

        try {
          Pattern pattern = Pattern.compile(search);
          Matcher matcher = pattern.matcher(search_string);

          while (matcher.find()) {
            int begin = matcher.start();
            int end = matcher.end();

            frame.setHighlights(begin, end);
          }
        } catch (BadLocationException ex) {
          JOptionPane.showMessageDialog(frame, "something wrong in painting");
          ex.printStackTrace(System.err);
        } catch (PatternSyntaxException ex) {
          JOptionPane.showMessageDialog(frame, "invalid regular expression");
          ex.printStackTrace(System.err);
        }
      }
    });

    frame.addResetActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.clearHighlights();
        frame.setTargetCaretPosition(0);
      }
    });

  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws IOException {
    RegexSearch app = new RegexSearch();
    app.frame.setVisible(true);
  }
}
