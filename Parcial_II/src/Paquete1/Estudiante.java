package Paquete1;

import java.awt.HeadlessException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

public class Estudiante extends Persona {

    private int id;
    private String carnet;
    Conexion cn;

    public Estudiante() {
    }

    public Estudiante(int id, String carnet, String nombres, String apellidos, String direccion, String telefono, String fecha_nacimiento, String genero, String email) {
        super(nombres, apellidos, direccion, telefono, fecha_nacimiento, genero, email);
        this.carnet = carnet;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public DefaultTableModel leer() throws java.sql.SQLException {
        DefaultTableModel tabla = new DefaultTableModel();
        try {
            cn = new Conexion();
            cn.abrir_conexion();
            String query;
            query = "Select id_estudiante as id,nit,nombres,apellidos,direccion,telefono,fecha_nacimiento, genero, email from estudiante;";
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);

            String encabezado[] = {"id", "Carnet", "Nombres", "Apellidos", "Direccion", "Telefono", "Nacimiento", "Genero", "Email"};
            tabla.setColumnIdentifiers(encabezado);

            String datos[] = new String[7];

            while (consulta.next()) {
                datos[0] = consulta.getString("id");
                datos[1] = consulta.getString("nit");
                datos[2] = consulta.getString("nombres");
                datos[3] = consulta.getString("apellidos");
                datos[4] = consulta.getString("direccion");
                datos[5] = consulta.getString("telefono");
                datos[6] = consulta.getString("fecha_nacimiento");
                datos[7] = consulta.getString("genero");
                datos[8] = consulta.getString("email");
                tabla.addRow(datos);
            }
            cn.cerrar_conexion();

        } catch (Exception ex) {
            cn.cerrar_conexion();
            System.out.println("Error: " + ex.getMessage());

        }
        return tabla;
    }

    @Override
    public void agregar() {
        try {
            PreparedStatement parametro;
            Conexion cn = new Conexion();
            cn.abrir_conexion();
            String query;
            query = "INSERT INTO estudiantes(nit,nombres,apellidos,direccion,telefono,fecha_nacimiento, genero, email) VALUES(?,?,?,?,?,?,?,?);";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getCarnet());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setString(7, getGenero());
            parametro.setString(8, getEmail());
            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(executar) + "Registros Ingresados", "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch (java.sql.SQLException ex) {
            Logger.getLogger(Estudiante.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void actualizar() {
        try {
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrir_conexion();
            String query;
            query = "update estudiantes set carnet = ?,nombres= ?,apellidos= ?,direccion= ?,telefono= ?,fecha_nacimiento= ?, genero= ?, email= ? "
                    + "where id_estudiante = ?";
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setString(1, getCarnet());
            parametro.setString(2, getNombres());
            parametro.setString(3, getApellidos());
            parametro.setString(4, getDireccion());
            parametro.setString(5, getTelefono());
            parametro.setString(6, getFecha_nacimiento());
            parametro.setString(7, getGenero());
            parametro.setString(8, getEmail());

            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(executar) + " Registro Actualizado",
                    "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch (java.sql.SQLException ex) {
            Logger.getLogger(Estudiante.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void eliminar() {

        try {
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrir_conexion();
            String query;
            query = "delete from estudiantes where id_estudiantes = ?";

            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, getId());

            int executar = parametro.executeUpdate();
            cn.cerrar_conexion();
            JOptionPane.showMessageDialog(null, Integer.toString(executar) + " Registro Eliminado",
                    "Mensaje", JOptionPane.INFORMATION_MESSAGE);

        } catch (java.sql.SQLException ex) {
            Logger.getLogger(Estudiante.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
