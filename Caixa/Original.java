package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class Original {

    //1 Início do método conectarBD
    public Connection conectarBD() {
        Connection conn = null;

        //2 Tentativa de carregar driver e conectar
        try {
            Class.forName("com.mysql.Driver.Manager").newInstance();
            String url = "jdbc:mysql://127.0.0.1/test?user=lopes&password=123";
            conn = DriverManager.getConnection(url);
        }
        //3 Caso ocorra erro na conexão
        catch (Exception e) { }

        //4 Retorno da conexão (pode ser null)
        return conn;
    }

    //5 Declaração das variáveis globais
    public String nome = "";
    public boolean result = false;

    //6 Início do método verificarUsuario
    public boolean verificarUsuario(String login, String senha) {
        String sql = "";
        Connection conn = conectarBD();

        //7 Montagem da query SQL
        sql = "select nome from usuarios ";
        sql += "where login = '" + login + "'";
        sql += " and senha = '" + senha + "'";

        //8 Execução da query SQL
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            //9 Verificação do resultado da consulta
            if (rs.next()) {
                nome = rs.getString("nome");
                result = true;
            } 
            //10 Fim do bloco IF
        }

        //11 Tratamento de exceção da query
        catch (Exception e) { }

        //12 Retorno do resultado (true/false)
        return result;
    }
}
//13 Fim da classe
