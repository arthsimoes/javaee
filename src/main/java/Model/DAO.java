package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DAO.
 */
public class DAO {
    
    /**  Módulo de conexão *. */
    // Parâmetros de conexão
    private String driver = "com.mysql.cj.jdbc.Driver";
    
    /** The url. */
    private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
    
    /** The user. */
    private String user = "root";
    
    /** The password. */
    private String password = "root";

    /**
     * Conectar.
     *
     * @return the connection
     */
    // Método de conexão
    private Connection conectar() {
        Connection con = null;
        try {
            // Carregar o driver JDBC
            Class.forName(driver);
            // Estabelecer a conexão
            con = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return con;
    }

    /**
     *  CRUD CREATE *.
     *
     * @param contato the contato
     */
    public void inserirContato(JavaBeans contato) {
        String create = "INSERT INTO contatos (nome, fone, email) VALUES (?, ?, ?)";
        try (Connection con = conectar();
             PreparedStatement pst = con.prepareStatement(create)) {

            // substituir os parâmetros (?) pelo conteúdo das variáveis JavaBeans
            pst.setString(1, contato.getNome());
            pst.setString(2, contato.getFone());
            pst.setString(3, contato.getEmail());
            pst.executeUpdate();

        } catch (Exception e) {
            System.err.println("Erro ao inserir contato: " + e.getMessage());
        }
    }

    /**
     *  CRUD READ *.
     *
     * @return the array list
     */
    public ArrayList<JavaBeans> listarContatos() {
        ArrayList<JavaBeans> contatos = new ArrayList<>();
        String read = "SELECT * FROM contatos ORDER BY nome";
        try (Connection con = conectar();
             PreparedStatement pst = con.prepareStatement(read);
             ResultSet rs = pst.executeQuery()) {

            while (rs.next()) {
                String idcon = rs.getString("idcon");
                String nome = rs.getString("nome");
                String fone = rs.getString("fone");
                String email = rs.getString("email");
                contatos.add(new JavaBeans(idcon, nome, fone, email));
            }

        } catch (Exception e) {
            System.err.println("Erro ao listar contatos: " + e.getMessage());
        }
        return contatos;
    }

    /**
     *  CRUD UPDATE *.
     *
     * @param contato the contato
     */
    public void selecionarContato(JavaBeans contato) {
        String read2 = "SELECT * FROM contatos WHERE idcon = ?";
        try (Connection con = conectar();
             PreparedStatement pst = con.prepareStatement(read2)) {

            pst.setString(1, contato.getIdcon());
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    contato.setIdcon(rs.getString("idcon"));
                    contato.setNome(rs.getString("nome"));
                    contato.setFone(rs.getString("fone"));
                    contato.setEmail(rs.getString("email"));
                }
            }

        } catch (Exception e) {
            System.err.println("Erro ao selecionar contato: " + e.getMessage());
        }
    }

    /**
     * Alterar contato.
     *
     * @param contato the contato
     */
    public void alterarContato(JavaBeans contato) {
        String update = "UPDATE contatos SET nome = ?, fone = ?, email = ? WHERE idcon = ?";
        try (Connection con = conectar();
             PreparedStatement pst = con.prepareStatement(update)) {

            pst.setString(1, contato.getNome());
            pst.setString(2, contato.getFone());
            pst.setString(3, contato.getEmail());
            pst.setString(4, contato.getIdcon());
            pst.executeUpdate();

        } catch (Exception e) {
            System.err.println("Erro ao alterar contato: " + e.getMessage());
        }
    }
    
    /**
     *  CRUD DELETE *.
     *
     * @param contato the contato
     */
    public void deletarContato(JavaBeans contato) {
    	String delete = "delete from contatos where idcon =?";
    	try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(delete);
			pst.setString(1, contato.getIdcon());
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
    	
    	
    	
    }
    
    
}
