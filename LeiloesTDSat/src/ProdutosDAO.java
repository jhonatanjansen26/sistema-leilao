/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Adm
 */

import java.sql.PreparedStatement;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.sql.SQLException; // Importação necessária para tratar erros do banco


public class ProdutosDAO {
    
    Connection conn;
    PreparedStatement prep;
    ResultSet resultset;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    
    // Método alterado para boolean para sabermos se o cadastro teve sucesso
    public boolean cadastrarProduto (ProdutosDTO produto){
        conn = new conectaDAO().connectDB();
        try {
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            prep = conn.prepareStatement(sql);
            prep.setString(1, produto.getNome());
            prep.setInt(2, produto.getValor());
            prep.setString(3, produto.getStatus());
            
            prep.executeUpdate();
            return true; // Retorna true se o comando funcionou
        } catch (SQLException erro) {
            System.out.println("Erro ao cadastrar: " + erro.getMessage());
            return false; // Retorna false se deu algum erro
        }
    }
    
    public ArrayList<ProdutosDTO> listarProdutos(){
        listagem.clear(); // Limpa a lista para não duplicar os itens ao abrir a tela novamente
        conn = new conectaDAO().connectDB();
        try {
            String sql = "SELECT * FROM produtos";
            prep = conn.prepareStatement(sql);
            resultset = prep.executeQuery();
            
            while (resultset.next()) {
                ProdutosDTO produto = new ProdutosDTO();
                produto.setId(resultset.getInt("id"));
                produto.setNome(resultset.getString("nome"));
                produto.setValor(resultset.getInt("valor"));
                produto.setStatus(resultset.getString("status"));
                
                listagem.add(produto);
            }
        } catch (SQLException erro) {
            System.out.println("Erro ao listar: " + erro.getMessage());
        }
        return listagem;
    }
        
}