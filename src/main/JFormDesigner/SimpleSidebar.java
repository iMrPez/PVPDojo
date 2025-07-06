import java.awt.*;
import javax.swing.*;
/*
 * Created by JFormDesigner on Fri Jul 04 23:13:32 EDT 2025
 */



/**
 * @author Owner
 */
public class SimpleSidebar extends JPanel {
    public SimpleSidebar() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents  @formatter:off
        // Generated using JFormDesigner non-commercial license
        scrollView = new JScrollPane();
        panel1 = new JPanel();

        //======== this ========
        setLayout(new BorderLayout());

        //======== scrollView ========
        {
            scrollView.setPreferredSize(null);

            //======== panel1 ========
            {
                panel1.setBackground(Color.red);
                panel1.setPreferredSize(new Dimension(5, 500));
                panel1.setLayout(new BoxLayout(panel1, BoxLayout.X_AXIS));
            }
            scrollView.setViewportView(panel1);
        }
        add(scrollView, BorderLayout.CENTER);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents  @formatter:on
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables  @formatter:off
    // Generated using JFormDesigner non-commercial license
    private JScrollPane scrollView;
    private JPanel panel1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables  @formatter:on
}
