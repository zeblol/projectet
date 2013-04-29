package Test;

import Domain.Controller;
import java.awt.event.KeyEvent;
import javax.swing.JOptionPane;

/**
 *
 * @author Sebastian
 */
public class LoginFrame extends javax.swing.JFrame {

    private Controller control;

    /**
     * Creates new form LoginFrame
     */
    public LoginFrame() {
        initComponents();
        control = new Controller();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        jLabelLoginTitle = new javax.swing.JLabel();
        jTextFieldLoginPassword = new javax.swing.JTextField();
        jLabelLoginUsername = new javax.swing.JLabel();
        jTextFieldLoginUsername = new javax.swing.JTextField();
        jLabelLoginPassword = new javax.swing.JLabel();
        jButtonLogin = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabelLoginTitle.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabelLoginTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelLoginTitle.setText("LOGIN");

        jTextFieldLoginPassword.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jTextFieldLoginPasswordActionPerformed(evt);
            }
        });
        jTextFieldLoginPassword.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                jTextFieldLoginPasswordKeyReleased(evt);
            }
        });

        jLabelLoginUsername.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelLoginUsername.setText("Brugernavn");

        jTextFieldLoginUsername.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jTextFieldLoginUsernameActionPerformed(evt);
            }
        });

        jLabelLoginPassword.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelLoginPassword.setText("Password");

        jButtonLogin.setText("Login");
        jButtonLogin.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jButtonLoginActionPerformed(evt);
            }
        });
        jButtonLogin.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                jButtonLoginKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabelLoginTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabelLoginUsername, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(151, Short.MAX_VALUE)
                .addComponent(jTextFieldLoginPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(132, 132, 132))
            .addComponent(jLabelLoginPassword, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(239, 239, 239)
                .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(151, Short.MAX_VALUE)
                    .addComponent(jTextFieldLoginUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(132, 132, 132)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabelLoginTitle)
                .addGap(67, 67, 67)
                .addComponent(jLabelLoginUsername)
                .addGap(90, 90, 90)
                .addComponent(jLabelLoginPassword)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldLoginPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
                .addComponent(jButtonLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(144, 144, 144)
                    .addComponent(jTextFieldLoginUsername, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(257, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldLoginUsernameActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jTextFieldLoginUsernameActionPerformed
    {//GEN-HEADEREND:event_jTextFieldLoginUsernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLoginUsernameActionPerformed

    private void jTextFieldLoginPasswordActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jTextFieldLoginPasswordActionPerformed
    {//GEN-HEADEREND:event_jTextFieldLoginPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldLoginPasswordActionPerformed
    //sebastian
    private void jButtonLoginActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jButtonLoginActionPerformed
    {//GEN-HEADEREND:event_jButtonLoginActionPerformed
        // TJEK OM USERNAME ER INT
        boolean b = control.tryLogin(jTextFieldLoginUsername.getText(), jTextFieldLoginPassword.getText());
        if (b) {
            new TestFrame(control).setVisible(true);
            setVisible(false); //Setter LoginFrame til ikke at vises
        } else {
            JOptionPane.showMessageDialog(null, "Brugernavn eller password passer ikke!");
        }

    }//GEN-LAST:event_jButtonLoginActionPerformed

    private void jButtonLoginKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jButtonLoginKeyReleased
    {//GEN-HEADEREND:event_jButtonLoginKeyReleased

    }//GEN-LAST:event_jButtonLoginKeyReleased

    private void jTextFieldLoginPasswordKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_jTextFieldLoginPasswordKeyReleased
    {//GEN-HEADEREND:event_jTextFieldLoginPasswordKeyReleased
         if(evt.getKeyCode() == KeyEvent.VK_ENTER){
            jButtonLogin.doClick();
        }
    }//GEN-LAST:event_jTextFieldLoginPasswordKeyReleased

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonLogin;
    private javax.swing.JLabel jLabelLoginPassword;
    private javax.swing.JLabel jLabelLoginTitle;
    private javax.swing.JLabel jLabelLoginUsername;
    private javax.swing.JTextField jTextFieldLoginPassword;
    private javax.swing.JTextField jTextFieldLoginUsername;
    // End of variables declaration//GEN-END:variables
}
