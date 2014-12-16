/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.io.File;
import Controler.*;


public class FileTransferDialog extends javax.swing.JFrame {

    /**
     * Creates new form FileTransfer
     */
    
    private String file;
    private String from;
    private Gui g;
    public FileTransferDialog(String file, String from, Gui g) {
        this.g=g;
        this.file=file;
        this.from=from;
        initComponents();
        this.getRemoteLabel().setText(from);
        this.getFileLabel().setText(file);
        this.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    acceptButton = new javax.swing.JButton();
    refuseButton = new javax.swing.JButton();
    remoteLabel = new javax.swing.JLabel();
    jLabel1 = new javax.swing.JLabel();
    fileLabel = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    acceptButton.setText("Accept");
    acceptButton.addActionListener(g);

    refuseButton.setText("Refuse");
    refuseButton.addActionListener(g);

    remoteLabel.setText(" ");

    jLabel1.setText("wants to send you a file:");

    fileLabel.setText("jLabel2");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(49, 49, 49)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(remoteLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jLabel1)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
          .addGroup(layout.createSequentialGroup()
            .addComponent(acceptButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 156, Short.MAX_VALUE)
            .addComponent(refuseButton)
            .addGap(65, 65, 65))))
      .addGroup(layout.createSequentialGroup()
        .addGap(99, 99, 99)
        .addComponent(fileLabel)
        .addGap(0, 0, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGap(20, 20, 20)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(remoteLabel)
          .addComponent(jLabel1))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(fileLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 64, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(acceptButton)
          .addComponent(refuseButton))
        .addGap(21, 21, 21))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(FileTransferDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FileTransferDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FileTransferDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FileTransferDialog.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new FileTransferDialog().setVisible(true);
            }
        });
    }

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JButton acceptButton;
  private javax.swing.JLabel fileLabel;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JButton refuseButton;
  private javax.swing.JLabel remoteLabel;
  // End of variables declaration//GEN-END:variables

    /**
     * @return the acceptButton
     */
    public javax.swing.JButton getAcceptButton() {
        return acceptButton;
    }

    /**
     * @param acceptButton the acceptButton to set
     */
    public void setAcceptButton(javax.swing.JButton acceptButton) {
        this.acceptButton = acceptButton;
    }

    /**
     * @return the fileLabel
     */
    public javax.swing.JLabel getFileLabel() {
        return fileLabel;
    }

    /**
     * @param fileLabel the fileLabel to set
     */
    public void setFileLabel(javax.swing.JLabel fileLabel) {
        this.fileLabel = fileLabel;
    }

    /**
     * @return the refuseButton
     */
    public javax.swing.JButton getRefuseButton() {
        return refuseButton;
    }

    /**
     * @param refuseButton the refuseButton to set
     */
    public void setRefuseButton(javax.swing.JButton refuseButton) {
        this.refuseButton = refuseButton;
    }

    /**
     * @return the remoteLabel
     */
    public javax.swing.JLabel getRemoteLabel() {
        return remoteLabel;
    }

    /**
     * @param remoteLabel the remoteLabel to set
     */
    public void setRemoteLabel(javax.swing.JLabel remoteLabel) {
        this.remoteLabel = remoteLabel;
    }
}
