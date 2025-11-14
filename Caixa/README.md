
# Teste de Caixa Branca


## PONTOS DO CÓDIGO

package login;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class User {

    //1️
    public Connection conectarBD() {
        Connection conn = null;

        //2️
        try {
            Class.forName("com.mysql.Driver.Manager").newInstance();
            String url = "jdbc:mysql://127.0.0.1/test?user=lopes&password=123";
            conn = DriverManager.getConnection(url);
        }
        //3️
        catch (Exception e) { }

        //4️
        return conn;
    }

    //5️
    public String nome = "";
    public boolean result = false;

    //6️
    public boolean verificarUsuario(String login, String senha) {
        String sql = "";
        Connection conn = conectarBD();

        //7️
        sql = "select nome from usuarios ";
        sql += "where login = '" + login + "'";
        sql += " and senha = '" + senha + "'";

        //8️
        try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            //9️
            if (rs.next()) {
                nome = rs.getString("nome");
                result = true;
            }   //10
        }

        //11
        catch (Exception e) { }

        //12
        return result;
    }
}
//13


## NOTAÇÃO DE GRAFO DE FLUXO
**Identificação dos nós (blocos de decisão e processo)**

|  **Nó** | **Descrição**                                     |
| :-----: | :------------------------------------------------ |
|  **N1** | Início do método `conectarBD()`                   |
|  **N2** | Declaração `Connection conn = null`               |
|  **N3** | `try` — tentativa de abrir conexão                |
|  **N4** | `catch (Exception e)` — erro ao conectar          |
|  **N5** | `return conn` — retorna a conexão (pode ser nula) |
|  **N6** | Início do método `verificarUsuario()`             |
|  **N7** | Montagem da query SQL com login e senha           |
|  **N8** | `try` — executa a query                           |
|  **N9** | `if (rs.next())` — verifica se encontrou usuário  |
| **N10** | `nome = rs.getString("nome"); result = true;`     |
| **N11** | `catch (Exception e)` — erro de execução SQL      |
| **N12** | `return result` — retorna verdadeiro/falso        |
| **N13** | Fim da classe `User`                              |

**Fluxo (arestas entre os nós)**

N1 → N2

N2 → N3

N3 → N4 (erro de conexão)

N3 → N5 (conexão bem-sucedida)

N4 → N5

N5 → N6

N6 → N7

N7 → N8

N8 → N9 (execução SQL)

N8 → N11 (exceção SQL)

N9 → N10 (usuário encontrado)

N9 → N12 (usuário não encontrado)

N10 → N12

N11 → N12

N12 → N13 (fim da classe)
## COMPLEXIDADE CICLOMÁTICA COM CALCULOS

**Fórmula:**


M=
𝐸
−
𝑁
+
2
𝑃
M=E−N+2P


E = número de arestas = 15

N = número de nós = 13

P = número de componentes conectados = 1


M =
15
−
13
+
2
(
1
)

4
M=15−13+2(1)=4

**Complexidade ciclomática = 4**
## CAMINHOS INDEPENDENTES

|                                 **Caminho**                             |            **Descrição**                            |
|:--------------------------------------------------------------------: | :-------------------------------------------- |
|    **C1:** N1 → N2 → N3 → N5 → N6 → N7 → N8 → N9 (false) → N12 → N13   | Conexão OK, usuário não encontrado            |
| **C2:** N1 → N2 → N3 → N5 → N6 → N7 → N8 → N9 (true) → N10 → N12 → N13 | Conexão OK, usuário válido                    |
|     **C3:** N1 → N2 → N3 → N4 → N5 → N6 → N7 → N8 → N11 → N12 → N13    | Erro ao conectar, depois erro na execução SQL |
|       **C4:** N1 → N2 → N3 → N5 → N6 → N7 → N8 → N11 → N12 → N13       | Conexão OK, erro SQL na execução da query     |
