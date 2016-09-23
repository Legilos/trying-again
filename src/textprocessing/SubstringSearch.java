package textprocessing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;
import views.SubstringSearchFrame;

/**
 * @author Robert Kline
 */
public class SubstringSearch {
  
  private final SubstringSearchFrame frame = new SubstringSearchFrame();
  
  private int search_start = 0;

  public SubstringSearch() throws IOException {
    frame.setTitle(getClass().getSimpleName());
    frame.setSize(750, 500);

    String target = "tell-tale-heart.txt";
    String content = new String(Files.readAllBytes(Paths.get(target)));
    frame.setTargetText(content);

    final String search_string = content.toLowerCase();

    frame.addFindActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent ae) {
        frame.clearHighlights();
        String search = frame.getSearchText().toLowerCase();
        int ind = search_string.indexOf(search, search_start);
        if (ind == -1) {
          JOptionPane.showMessageDialog(frame, "string not found");
        } else {
          try {
            frame.setHighlights(ind, ind + search.length());
            search_start = ind + 1;
          } catch (BadLocationException ex) {
            JOptionPane.showMessageDialog(frame, "something wrong in painting");
            ex.printStackTrace(System.err);
          }
        }
      }
    });
    
    frame.addResetActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.clearHighlights();
        frame.setTargetCaretPosition(0);
        search_start = 0;
      }
    });
  }
  
  /**
   * 
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
    SubstringSearch app = new SubstringSearch();
    app.frame.setVisible(true);
  }
}
