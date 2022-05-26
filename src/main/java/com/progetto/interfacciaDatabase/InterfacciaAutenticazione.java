package com.progetto.interfacciaDatabase;
import com.progetto.entity.AddettoAzienda;
import com.progetto.entity.Corriere;
import com.progetto.entity.Farmacia;
import javax.security.auth.login.CredentialException;
import java.sql.*;


/**
 * contiene i metodi necessari ad effettuare l'autenticazione con il database
 */
public class InterfacciaAutenticazione {

    /**
     * Ritorna la tupla della tabella {@code Farmacia} corrispondente alle credenziali inserite
     * @param idFarmacia id della farmacia
     * @param password password della farmacia
     * @return un oggetto {@code Farmacia} contenente l'id corrispondente alle credenziali inserite (se non sono corrette ritorna {@code null})
     */
    public Farmacia getCredenzialiFarmacia(int idFarmacia, String password) throws CredentialException{
        Farmacia farmacia = new Farmacia();
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbazienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("select * from farmacia where id_farmacia = ? and password = ?");
            statement.setInt(1,idFarmacia);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                farmacia.setIdFarmacia(resultSet.getInt(1));
                farmacia.setNome(resultSet.getString(2));
                farmacia.setIndirizzo(resultSet.getString(3));
                farmacia.setRecapitoTelefonico(resultSet.getString(4));
            }
            else{
                PreparedStatement statementId = connection.prepareStatement("select id_farmacia from farmacia where id_farmacia = ?");
                statementId.setInt(1,idFarmacia);
                ResultSet resultSetId = statementId.executeQuery();
                if(resultSetId.next()){
                    farmacia.setNome("passwordNonValida");
                }
                else{
                    farmacia = null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return farmacia;
    }

    /**
     * Ritorna la tupla della tabella {@code AddettoAzienda} corrispondente alle credenziali inserite
     * @param idAddetto id dell'addetto
     * @param password password dell'addetto
     * @return un oggetto {@code AddettoAzienda} contenente l'id corrispondente alle credenziali inserite (se non sono corrette ritorna {@code null})
     */
    public AddettoAzienda getCredenzialiAddettoAzienda(int idAddetto, String password){
        AddettoAzienda addetto = new AddettoAzienda();
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbazienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("select * from addetto where id_addetto = ? and password = ?");
            statement.setInt(1,idAddetto);
            statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                addetto.setIdAddetto(resultSet.getInt(1));
                addetto.setNominativo(resultSet.getString(2));
                addetto.setDataNascita(resultSet.getDate(3).toLocalDate());
                addetto.setEmail(resultSet.getString(4));
                addetto.setRecapitoTelefonico(resultSet.getString(5));
            }
            else{
                PreparedStatement statementId = connection.prepareStatement("select id_addetto from addetto where id_addetto = ?");
                statementId.setInt(1,idAddetto);
                ResultSet resultSetId = statementId.executeQuery();
                if(resultSetId.next()){
                    addetto.setNominativo("passwordNonValida");
                }
                else{
                    addetto = null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addetto;
    }

    /**
     * Ritorna la tupla della tabella {@code Corriere} corrispondente alle credenziali inserite
     * @param idCorriere id del corriere
     * @param password password del corriere
     * @return un oggetto {@code Corriere}
     */
    public Corriere getCredenzialiCorriere(int idCorriere, String password) throws CredentialException{
        Corriere corriere = null;
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbazienda", "root","password")){
            PreparedStatement statement = connection.prepareStatement("select * from corriere where id_corriere = ? and password = ?");
            statement.setInt(1,idCorriere);
            statement.setString(2,password);
            ResultSet risultatoCorriere = statement.executeQuery();
            if(risultatoCorriere.next()){
                corriere = new Corriere(risultatoCorriere);
            }
            else{
                PreparedStatement statementId = connection.prepareStatement("select id_corriere " +
                        "from corriere " +
                        "where id_corriere = ?");
                statementId.setInt(1,idCorriere);
                ResultSet resultSetId = statementId.executeQuery();
                if(resultSetId.next()){
                    corriere = new Corriere("passwordNonValida");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return corriere;
    }

}