
import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    public ProdutosDAO() {
        conn = new conectaDAO().connectDB(); // Inicializa a conexão aqui
    }

    public void cadastrarProduto(ProdutosDTO produto) throws SQLException {
        conn = new conectaDAO().connectDB();

        String sql = "INSERT INTO produto (nome, valor, status) VALUES (?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, produto.getNome());
        stmt.setInt(2, produto.getValor());
        stmt.setString(3, produto.getStatus());
        stmt.execute();
        stmt.close();

    }

    public ArrayList<ProdutosDTO> listarProdutos() {

        String sql = "SELECT * FROM produto";

        try {
            conn = new conectaDAO().connectDB();
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();

            listagem.clear();  // Limpar a lista anterior
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));

                listagem.add(produto);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar produtos: " + e.getMessage());
        }

        return listagem;
    }
    
    public void venderProduto(ProdutosDTO objDTO) throws SQLException{
        String sql= "update produto set status = ? where id =?";
        conn = new conectaDAO().connectDB();

        try {
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1,objDTO.getStatus());
        stmt.setInt(2,objDTO.getId());
       
        stmt.execute();
        stmt.close();
        
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao vender produtos: " + e.getMessage());
        }
    }

}
