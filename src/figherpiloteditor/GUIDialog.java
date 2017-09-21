package figherpiloteditor;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

public class GUIDialog extends JDialog implements ActionListener{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();

    public enum State {Cancel, OK, Unknown};
    private State state = State.Unknown;

    public static void main(String[] args) {
        boolean result = makeDialog("This is a test");
        if (result) {
            System.out.println("User Selected OK");
        } else {
            System.out.println("User Selected Cancel");
        }
    }

    /**
     * Launch the application.
     * 
     * @param text: Text to display on Dialog
     * @return : true of OK is clicked, false otherwise
     */
    public static boolean makeDialog(String text) {
        GUIDialog dialog = null;
        try {
            dialog = new GUIDialog(text);
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (dialog == null) {
            System.out.println("NULL");
            return false;
        }
        State temp = dialog.getState();
        while (temp == State.Unknown) {
            temp = dialog.getState();
            System.out.println(temp);
        }

        if (dialog.getState() == State.OK) {
            dialog.dispose();
            return true;
        }
        dialog.dispose();
        return false;
    }

    /**
     * Create the dialog.
     */
    public GUIDialog(String text) {
        setBounds(100, 100, 450, 300);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setLayout(new FlowLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        {
            JLabel textLabel = new JLabel(text);
            contentPanel.add(textLabel);
        }
        {
            JPanel buttonPane = new JPanel();
            buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
            getContentPane().add(buttonPane, BorderLayout.SOUTH);
            {
                JButton okButton = new JButton("OK");
                okButton.setActionCommand("OK");
                okButton.addActionListener(this);
                buttonPane.add(okButton);
                getRootPane().setDefaultButton(okButton);
            }
            {
                JButton cancelButton = new JButton("Cancel");
                cancelButton.setActionCommand("Cancel");
                cancelButton.addActionListener(this);
                buttonPane.add(cancelButton);
            }
        }
    }

    /**Used to detect Menu Selections
     * 
     * @param e Action Performed
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        System.out.println(command);
        if (command.equals("OK")) {
            state = State.OK;
        } else {
            state = State.Cancel;
        }
    }

    public State getState() {
        return state;
    }
}
