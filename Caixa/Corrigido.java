import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Classe responsável pela comunicação com o banco de dados e validação de credenciais.
 */

public class Corrigido {

    // URL de conexão com o banco MySQL

    private static final String DB_URL =
        "jdbc:mysql://127.0.0.1/test?user=lopes&password=123";

    // Armazena o nome encontrado no banco após o login

    public String nome = "";

    // Indica se o login foi aceito ou não

    public boolean usuarioValido = false;

    /**
     * Estabelece a conexão com o banco de dados MySQL.
     * 
     * @return Objeto Connection ativo ou null caso ocorra algum erro.
     */

    public Connection conectarBD() {
        Connection conexao = null;

        try {
            // Carrega o driver JDBC do MySQL para permitir a comunicação

            Class.forName("com.mysql.cj.jdbc.Driver");

            // Tenta abrir a conexão usando a URL definida

            conexao = DriverManager.getConnection(DB_URL);

        } catch (Exception e) {
            // Exibe o motivo da falha caso a conexão não seja criada

            System.out.println("Falha ao conectar no banco: " + e.getMessage());
        }

        // Retorna a conexão criada (ou null caso tenha dado erro)

        return conexao;
    }

    /**
     * Consulta o banco para verificar se o login e senha existem.
     * Retorna true quando o usuário é encontrado.
     */

    public boolean verificarUsuario(String login, String senha) {

        // Comando SQL com placeholders, evitando SQL Injection

        String sql = "SELECT nome FROM usuarios WHERE login = ? AND senha = ?";

        // Estrutura try-with-resources garante que tudo será fechado automaticamente

        try (Connection conn = conectarBD();
             PreparedStatement ps = (conn != null ? conn.prepareStatement(sql) : null)) {

            // Se a conexão não foi aberta, não é possível continuar

            if (conn == null) {
                System.out.println("Não foi possível conectar ao banco.");
                return false;
            }

            // Atribui os valores recebidos aos parâmetros da consulta

            ps.setString(1, login);
            ps.setString(2, senha);

            // Executa o SELECT retornando o ResultSet

            try (ResultSet rs = ps.executeQuery()) {

                // Se encontrou um registro, considera o login válido

                if (rs.next()) {
                    nome = rs.getString("nome");
                    usuarioValido = true;
                }
            }

        } catch (Exception e) {

            // Mostra o erro caso aconteça algo durante a verificação

            System.out.println("Erro ao consultar usuário: " + e.getMessage());
        }

        // Retorna o resultado da validação
        
        return usuarioValido;
    }

    public static void main(String[] args) {
        Corrigido app = new Corrigido();
        boolean valido = app.verificarUsuario("login_teste", "senha_teste");
        System.out.println("Usuário válido? " + valido);
    }
}
