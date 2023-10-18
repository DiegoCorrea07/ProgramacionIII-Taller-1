import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;

public class FormularioCine {
    private JPanel principal;
    private JComboBox cboPelicula;
    private JComboBox cboCantidad;
    private JButton cboComprar;
    private JTextArea txtEntradas;
    private JButton btnPelicula1;
    private JButton btnPelicula2;
    private JLabel txtAutor;
    private JButton nuevaCompraButton;
    private Cine cine = new Cine();
    public FormularioCine() {

        try {
        String a="",b="";
        do {
            b= JOptionPane.showInputDialog("Ingrese su número de cédula real");
            a = JOptionPane.showInputDialog("Ingrese su nombre real");
        }while(b.length()<10);
            //txtAutor.setText("" + a + " - " + "" + b);
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("Taller1ProgramacionIII.dat"));
            o.writeObject(a+b);
            o.close();
        }catch (Exception ex) {

        }
        cboComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pelicula = cboPelicula.getSelectedItem().toString();
                int cantidad = Integer.parseInt(cboCantidad.getSelectedItem().toString());

                //Verificar si el cliente ya ha comprado entradas para esta película
                int entradasPorCliente = 0;
                for (Asistente asistente : cine.getAsistentes()) {
                    if (asistente.getPelicula().equals(pelicula)) {
                        entradasPorCliente += asistente.getCantidad();
                    }
                }

                int entradasRestantes = 4 - entradasPorCliente;

                if (cantidad > 0 && cantidad <= entradasRestantes) {
                    //->Se agrega la compra a la cola
                    cine.encolar(pelicula, cantidad);

                    //Actualizar el TextArea con la información de las compras
                    txtEntradas.setText(cine.listarElementos());

                    //Limpia los JComboBox
                    cboPelicula.setSelectedIndex(0);
                    cboCantidad.setSelectedIndex(0);
                } else {
                    // Mostrar un cuadro de diálogo que indique cuántas entradas adicionales puede comprar
                    if (entradasRestantes > 0) {
                        JOptionPane.showMessageDialog(null, "No se pueden comprar " + cantidad + " entradas para " + pelicula +
                                ". Puede comprar " + entradasRestantes + " entradas más.");
                    } else {
                        JOptionPane.showMessageDialog(null, "No se pueden comprar más entradas para " + pelicula + ". Límite de 4 entradas por cliente.");
                    }
                }
            }
        });

        btnPelicula1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String pelicula = "XMEN";
                int espaciosDisponibles = cine.espaciosDisponibles(pelicula);
                JOptionPane.showMessageDialog(null, "Espacios disponibles para " + pelicula + ": " + espaciosDisponibles);

            }
        });
        btnPelicula2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String pelicula = "MARIO";
                int espaciosDispo = cine.espaciosDisponibles(pelicula);
                JOptionPane.showMessageDialog(null, "Espacios disponibles para " + pelicula + ": " + espaciosDispo);

            }
        });

    }

    public static void main(String[] args){
        JFrame frame = new JFrame("CINEMAX");
        frame.setContentPane(new FormularioCine().principal);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(400, 400);
        frame.setVisible(true);
    }
}
