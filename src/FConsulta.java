import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class FConsulta {
    private JPanel panelConsulta;
    private JTextField textFieldID;
    private JTextField textFieldNome;
    private JTextField textFieldSalario;
    private JButton buttonSeguinte;
    private JButton buttonAnterior;
    private JButton buttonPrimeiro;
    private JLabel labelImagem;
    private JButton buttonUltimo;

    private String path=null;
    private byte[] userImage;
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public FConsulta() {
        Connection();
        buttonPrimeiro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    rs.first();
                    textFieldID.setText(rs.getString("id"));
                    textFieldNome.setText(rs.getString("nome"));
                    textFieldSalario.setText(String.valueOf(rs.getDouble("salario")));
                    Blob blob= rs.getBlob("foto");
                    byte[] imageBytes=blob.getBytes(1,(int)blob.length());
                    ImageIcon imgIcon=new ImageIcon(new ImageIcon(imageBytes).getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT));
                    labelImagem.setIcon(imgIcon);

                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        buttonAnterior.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(!rs.isFirst()){
                        rs.previous();
                        textFieldID.setText(rs.getString("id"));
                        textFieldNome.setText(rs.getString("nome"));
                        textFieldSalario.setText(String.valueOf(rs.getDouble("salario")));
                        Blob blob=rs.getBlob("foto");
                        byte[]imageBytes=blob.getBytes(1,(int) blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT));
                        labelImagem.setIcon(imgIcon);
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        buttonSeguinte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if(!rs.isLast()){
                        rs.next();
                        textFieldID.setText(rs.getString("id"));
                        textFieldNome.setText(rs.getString("nome"));
                        textFieldSalario.setText(String.valueOf(rs.getDouble("salario")));
                        Blob blob =rs.getBlob("foto");
                        byte[] imageBytes = blob.getBytes(1,(int)blob.length());
                        ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().getScaledInstance(250,250, Image.SCALE_DEFAULT));
                        labelImagem.setIcon(imgIcon);
                    }
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
        buttonUltimo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    rs.last();
                    textFieldID.setText(rs.getString("id"));
                    textFieldNome.setText(rs.getString("nome"));
                    textFieldSalario.setText(String.valueOf(rs.getDouble("salario")));
                    Blob blob = rs.getBlob("foto");
                    byte[] imageBytes = blob.getBytes(1,(int)blob.length());
                    ImageIcon imgIcon = new ImageIcon(new ImageIcon(imageBytes).getImage().getScaledInstance(250,250,Image.SCALE_DEFAULT));
                    labelImagem.setIcon(imgIcon);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }

    public void Connection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            conn= DriverManager.getConnection("jdbc:mysql://localhost/bdempregados", "root", "1234");
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=st.executeQuery("select id, nome, foto, salario from empregados");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public void setVisible(boolean b){
        JFrame frame = new JFrame("Consulta de funcionários");
        frame.setContentPane(new FConsulta().panelConsulta);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(600,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(b);
    }
}
