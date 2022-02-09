import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import javax.imageio.ImageIO;

public class FEmpregado {
    private JTextField textFieldID;
    private JTextField textFieldNome;
    private JTextField textFieldSalario;
    private JLabel labelImagem;
    private JButton guardarButton;
    private JButton procurarButton;
    private JPanel panelEmpregado;

    private String path=null;
    private byte[] userImage;
    private PreparedStatement ps;
    private Connection conn;
    private ResultSet rs;

    public void setVisible(boolean b){
        JFrame frame = new JFrame("Consulta de funcionários");
        frame.setContentPane(new FEmpregado().panelEmpregado);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(b);
    }

    public FEmpregado() {
        guardarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome=textFieldNome.getText();
                double salario=Double.valueOf(textFieldSalario.getText());
                try{
                    File file = new File(path);
                    FileInputStream fs = new FileInputStream(file);
                    Class.forName("com.mysql.jdbc.Driver");
                    conn= DriverManager.getConnection("jdbc:mysql://localhost/bdempregados", "root", "1234");
                    ps=conn.prepareStatement("insert into empregados(nome,salario,foto) values (?,?,?)");
                    ps.setString(1,nome);
                    ps.setDouble(2,salario);
                    ps.setBytes(3,userImage);
                    ps.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Empregado alterado!");
                    textFieldID.setText("");
                    textFieldSalario.setText("");
                    textFieldNome.setText("");
                    labelImagem.setIcon(null);
                } catch (FileNotFoundException | SQLException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                } catch (ClassNotFoundException classNotFoundException) {
                    classNotFoundException.printStackTrace();
                }
            }
        });
        procurarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imgChoose = new JFileChooser();
                imgChoose.showOpenDialog(null);
                File img = imgChoose.getSelectedFile();

                path=((File) img).getAbsolutePath();
                BufferedImage image;
                try{
                    image=ImageIO.read(imgChoose.getSelectedFile());
                    ImageIcon imgIcon = new ImageIcon(new ImageIcon(image).getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT));
                    labelImagem.setIcon(imgIcon);
                    File imgg = new File(path);
                    FileInputStream fs = new FileInputStream(imgg);
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buff = new byte[1024];
                    int n_Bytes_read=0;

                    while((n_Bytes_read=fs.read(buff)) !=-1){
                        bos.write(buff, 0, n_Bytes_read);
                    }
                    userImage=bos.toByteArray();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }
}
