package models;

/**
 * The type User.
 * <p>
 *     Clase User que representa a un usuario registrado en la aplicación
 *
 * @author Zebenzuí López Conde
 * @version 1.0  2ºA DAM
 */
public class User {
    // Campos para el nombre de usuario y la contraseña
    private String username;
    private String password;

    /**
     * Instantiates a new User.
     * <p>
     *     Constructor de la clase User
     *
     * @param username the username
     * @param password the password
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets username.
     * <p>
     *     Método para obtener el nombre de usuario
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets password.
     * <p>
     *     Método para obtener la contraseña
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }
}