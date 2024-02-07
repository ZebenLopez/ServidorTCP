package models;

/**
 * The type User.
 *
 * @author Zebenzuí López Conde
 * @version 1.0  2ºA DAM
 */
// Clase User que representa a un usuario registrado en la aplicación
public class User {
    // Campos para el nombre de usuario y la contraseña
    private String username;
    private String password;

    /**
     * Instantiates a new User.
     *
     * @param username the username
     * @param password the password
     */
// Constructor de la clase User
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
// Método para obtener el nombre de usuario
    public String getUsername() {
        return username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
// Método para obtener la contraseña
    public String getPassword() {
        return password;
    }
}