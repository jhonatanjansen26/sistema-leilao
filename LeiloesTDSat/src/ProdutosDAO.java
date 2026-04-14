import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException;

public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public boolean cadastrarProduto (ProdutosDTO produto){
        conn = new conectaDAO().connectDB();
        try {
            prep = conn.prepareStatement("INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)");
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            prep.executeUpdate();
            return true;
        } catch (SQLException erro) {
            return false;
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        listagem.clear();
        conn = new conectaDAO().connectDB();
        try {
            prep = conn.prepareStatement("SELECT * FROM produtos");
            resultset = prep.executeQuery();
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                listagem.add(produto);
            }
        } catch (SQLException erro) {}
        return listagem;
    }

    // NOVA FUNÇÃO: Vender Produto
    public void venderProduto(int id) {
        conn = new conectaDAO().connectDB();
        try {
            prep = conn.prepareStatement("UPDATE produtos SET status = 'Vendido' WHERE id = ?");
            prep.setInt(1, id);
            prep.executeUpdate();
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro ao vender: " + erro.getMessage());
        }
    }

    // NOVA FUNÇÃO: Listar Vendidos
    public ArrayList<ProdutosDTO> listarProdutosVendidos() {
        listagem.clear();
        conn = new conectaDAO().connectDB();
        try {
            prep = conn.prepareStatement("SELECT * FROM produtos WHERE status = 'Vendido'");
            resultset = prep.executeQuery();
            while (resultset.next()) {
                ProdutosDTO p = new ProdutosDTO();
                p.setId(resultset.getInt("id"));
                p.setNome(resultset.getString("nome"));
                p.setValor(resultset.getInt("valor"));
                p.setStatus(resultset.getString("status"));
                listagem.add(p);
            }
        } catch (SQLException erro) {}
        return listagem;
    }
}